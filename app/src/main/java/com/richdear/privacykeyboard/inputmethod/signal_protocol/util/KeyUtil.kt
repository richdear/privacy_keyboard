package com.richdear.privacykeyboard.inputmethod.signal_protocol.util

import android.util.Log
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataAbstractStore
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.SignalProtocolStoreFullImplementation
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.InvalidKeyException
import org.signal.libsignal.protocol.ecc.Curve
import org.signal.libsignal.protocol.ecc.ECPrivateKey
import org.signal.libsignal.protocol.state.PreKeyRecord
import org.signal.libsignal.protocol.state.SignedPreKeyRecord
import org.signal.libsignal.protocol.util.KeyHelper
import org.signal.libsignal.protocol.util.Medium
import java.util.LinkedList
import java.util.concurrent.TimeUnit

object KeyUtil {
    val TAG: String = KeyUtil::class.java.simpleName

    const val BATCH_SIZE: Int = 2 // 100 in Signal app
    private val SIGNED_PRE_KEY_MAX_DAYS =
        TimeUnit.DAYS.toMillis(30) // debug: TimeUnit.MINUTES.toMillis(3)
    private val SIGNED_PRE_KEY_ARCHIVE_AGE =
        TimeUnit.DAYS.toMillis(2) // debug: TimeUnit.SECONDS.toMillis(20)

    @JvmStatic
    fun generateIdentityKeyPair(): IdentityKeyPair {
        val identityKeyPairKeys = Curve.generateKeyPair()

        return IdentityKeyPair(
            IdentityKey(identityKeyPairKeys.publicKey),
            identityKeyPairKeys.privateKey
        )
    }

    @JvmStatic
    fun generateRegistrationId(): Int {
        return KeyHelper.generateRegistrationId(false)
    }

    @JvmStatic
    @Synchronized
    fun generateAndStoreOneTimePreKeys(
        protocolStore: SignalProtocolStoreFullImplementation,
        metadataStore: PreKeyMetadataAbstractStore
    ): List<PreKeyRecord> {
        Log.d(TAG, "Generating one-time prekeys...")

        val records: MutableList<PreKeyRecord> = LinkedList()
        val preKeyIdOffset = metadataStore.nextOneTimePreKeyId

        for (i in 0 until BATCH_SIZE) {
            val preKeyId = (preKeyIdOffset + i) % Medium.MAX_VALUE
            val record = generateAndStoreOneTimePreKey(protocolStore, preKeyId)
            records.add(record)
        }

        return records
    }

    @JvmStatic
    @Synchronized
    fun generateAndStoreOneTimePreKey(
        protocolStore: SignalProtocolStoreFullImplementation,
        preKeyId: Int
    ): PreKeyRecord {
        Log.d(TAG, "Generating one-time prekey with id: $preKeyId...")
        val keyPair = Curve.generateKeyPair()
        val record = PreKeyRecord(preKeyId, keyPair)

        protocolStore.storePreKey(preKeyId, record)
        return record
    }

    @JvmStatic
    @Synchronized
    fun generateAndStoreSignedPreKey(
        protocolStore: SignalProtocolStoreFullImplementation,
        metadataStore: PreKeyMetadataAbstractStore
    ): SignedPreKeyRecord {
        return generateAndStoreSignedPreKey(
            protocolStore,
            metadataStore,
            protocolStore.identityKeyPair.privateKey
        )
    }

    @Synchronized
    fun generateAndStoreSignedPreKey(
        protocolStore: SignalProtocolStoreFullImplementation,
        metadataStore: PreKeyMetadataAbstractStore,
        privateKey: ECPrivateKey?
    ): SignedPreKeyRecord {
        Log.d(TAG, "Generating signed prekeys...")

        val signedPreKeyId = metadataStore.nextSignedPreKeyId
        val record = generateSignedPreKey(signedPreKeyId, privateKey, metadataStore)

        protocolStore.storeSignedPreKey(signedPreKeyId, record)
        metadataStore.nextSignedPreKeyId = (signedPreKeyId + 1) % Medium.MAX_VALUE
        metadataStore.nextSignedPreKeyRefreshTime =
            System.currentTimeMillis() + SIGNED_PRE_KEY_MAX_DAYS
        metadataStore.oldSignedPreKeyDeletionTime =
            System.currentTimeMillis() + SIGNED_PRE_KEY_ARCHIVE_AGE

        return record
    }

    @Synchronized
    fun generateSignedPreKey(
        signedPreKeyId: Int,
        privateKey: ECPrivateKey?,
        metadataStore: PreKeyMetadataAbstractStore?
    ): SignedPreKeyRecord {
        try {
            val keyPair = Curve.generateKeyPair()
            val signature = Curve.calculateSignature(privateKey, keyPair.publicKey.serialize())

            return SignedPreKeyRecord(
                signedPreKeyId,
                System.currentTimeMillis(),
                keyPair,
                signature
            )
        } catch (e: InvalidKeyException) {
            throw AssertionError(e)
        }
    }

    private fun rotateSignedPreKey(
        protocolStore: SignalProtocolStoreFullImplementation,
        metadataStore: PreKeyMetadataAbstractStore
    ) {
        val signedPreKeyRecord = generateAndStoreSignedPreKey(protocolStore, metadataStore)
        metadataStore.activeSignedPreKeyId = signedPreKeyRecord.id
        metadataStore.isSignedPreKeyRegistered = true
        metadataStore.signedPreKeyFailureCount = 0
    }

    @JvmStatic
    fun getUnusedOneTimePreKeyId(protocolStore: SignalProtocolStoreFullImplementation?): Int? {
        if (protocolStore?.preKeyStore == null) return null

        val preKeyId = 1
        val preKeyIsUsed = protocolStore.preKeyStore.checkPreKeyAvailable(preKeyId)
        if (preKeyIsUsed == null || preKeyIsUsed) {
            Log.d(TAG, "No unused prekey left. Generating new one time prekey with id $preKeyId")
            generateAndStoreOneTimePreKey(protocolStore, preKeyId)
        } else {
            Log.d(TAG, "Prekey with id $preKeyId is unused")
        }
        return preKeyId
    }

    @JvmStatic
    fun renewSignedPreKeyIfNecessary(
        protocolStore: SignalProtocolStoreFullImplementation?,
        metadataStore: PreKeyMetadataAbstractStore?
    ): Boolean {
        if (protocolStore == null || metadataStore == null) return false

        if (System.currentTimeMillis() > metadataStore.nextSignedPreKeyRefreshTime) {
            Log.d(TAG, "Rotating signed prekey...")
            rotateSignedPreKey(protocolStore, metadataStore)
            return true
        } else {
            Log.d(TAG, "Rotation of signed prekey not necessary...")
        }
        deleteOlderSignedPreKeysIfNecessary(protocolStore, metadataStore)
        return false
    }

    private fun deleteOlderSignedPreKeysIfNecessary(
        protocolStore: SignalProtocolStoreFullImplementation?,
        metadataStore: PreKeyMetadataAbstractStore?
    ) {
        if (protocolStore == null || metadataStore == null) return

        if (System.currentTimeMillis() > SIGNED_PRE_KEY_ARCHIVE_AGE) {
            Log.d(TAG, "Deleting old signed prekeys...")
            protocolStore.signedPreKeyStore.removeOldSignedPreKeys(metadataStore.activeSignedPreKeyId)
        } else {
            Log.d(TAG, "Deletion of old signed prekeys not necessary...")
        }
    }
}

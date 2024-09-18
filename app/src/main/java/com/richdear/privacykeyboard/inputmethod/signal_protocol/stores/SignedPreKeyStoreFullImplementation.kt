package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import org.signal.libsignal.protocol.InvalidKeyIdException
import org.signal.libsignal.protocol.InvalidMessageException
import org.signal.libsignal.protocol.state.SignedPreKeyRecord
import org.signal.libsignal.protocol.state.SignedPreKeyStore
import java.util.LinkedList

class SignedPreKeyStoreFullImplementation : SignedPreKeyStore {
    @JsonProperty
    private val store: MutableMap<Int, ByteArray> = HashMap()

    @Throws(InvalidKeyIdException::class)
    override fun loadSignedPreKey(signedPreKeyId: Int): SignedPreKeyRecord {
        Log.d(TAG, "Loading SignedPreKeyRecord with id: $signedPreKeyId")
        try {
            if (!store.containsKey(signedPreKeyId)) {
                throw InvalidKeyIdException("No such signed prekey-record! $signedPreKeyId")
            }

            return SignedPreKeyRecord(store[signedPreKeyId])
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }

    override fun loadSignedPreKeys(): List<SignedPreKeyRecord> {
        Log.d(TAG, "Loading all SignedPreKeyRecords")
        try {
            val results: MutableList<SignedPreKeyRecord> = LinkedList()

            for (serialized in store.values) {
                results.add(SignedPreKeyRecord(serialized))
            }

            return results
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }

    override fun storeSignedPreKey(signedPreKeyId: Int, record: SignedPreKeyRecord) {
        Log.d(TAG, "Storing SignedPreKeyRecord with id: $signedPreKeyId")
        store[signedPreKeyId] = record.serialize()
    }

    override fun containsSignedPreKey(signedPreKeyId: Int): Boolean {
        return store.containsKey(signedPreKeyId)
    }

    override fun removeSignedPreKey(signedPreKeyId: Int) {
        Log.d(TAG, "Removing SignedPreKeyRecord with id: $signedPreKeyId")
        store.remove(signedPreKeyId)
    }

    fun removeOldSignedPreKeys(activeSignedPreKeyId: Int) {
        Log.d(
            TAG,
            "Removing old SignedPreKeyRecord smaller than active signed pre key id: $activeSignedPreKeyId"
        )
        for (i in 0 until activeSignedPreKeyId) {
            if (containsSignedPreKey(i)) {
                removeSignedPreKey(i)
            }
        }
    }

    companion object {
        val TAG: String = SignedPreKeyStoreFullImplementation::class.java.simpleName
    }
}


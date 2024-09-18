package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import com.fasterxml.jackson.annotation.JsonProperty
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.InvalidKeyIdException
import org.signal.libsignal.protocol.NoSessionException
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.groups.state.SenderKeyRecord
import org.signal.libsignal.protocol.state.IdentityKeyStore
import org.signal.libsignal.protocol.state.PreKeyRecord
import org.signal.libsignal.protocol.state.SessionRecord
import org.signal.libsignal.protocol.state.SignalProtocolStore
import org.signal.libsignal.protocol.state.SignedPreKeyRecord
import java.util.UUID

class SignalProtocolStoreFullImplementation : SignalProtocolStore {
    @JvmField
    @JsonProperty
    val preKeyStore: PreKeyStoreFullImplementation = PreKeyStoreFullImplementation()

    @JvmField
    @JsonProperty
    val sessionStore: SessionStoreFullImplementation = SessionStoreFullImplementation()

    @JvmField
    @JsonProperty
    val signedPreKeyStore: SignedPreKeyStoreFullImplementation = SignedPreKeyStoreFullImplementation()

    @JsonProperty
    val senderKeyStore: SenderKeyStoreFullImplementation = SenderKeyStoreFullImplementation()

    @JsonProperty
    var identityKeyStore: IdentityKeyStoreFullImplementation? = null

    constructor(identityKeyPair: IdentityKeyPair?, registrationId: Int) {
        this.identityKeyStore = IdentityKeyStoreFullImplementation(identityKeyPair, registrationId)
    }

    constructor()

    override fun getIdentityKeyPair(): IdentityKeyPair {
        return identityKeyStore!!.identityKeyPair
    }

    override fun getLocalRegistrationId(): Int {
        return identityKeyStore!!.localRegistrationId
    }

    override fun saveIdentity(address: SignalProtocolAddress, identityKey: IdentityKey): Boolean {
        return identityKeyStore!!.saveIdentity(address, identityKey)
    }

    override fun isTrustedIdentity(
        address: SignalProtocolAddress,
        identityKey: IdentityKey,
        direction: IdentityKeyStore.Direction
    ): Boolean {
        return identityKeyStore!!.isTrustedIdentity(address, identityKey, direction)
    }

    override fun getIdentity(address: SignalProtocolAddress): IdentityKey {
        return identityKeyStore!!.getIdentity(address)
    }

    @Throws(InvalidKeyIdException::class)
    override fun loadPreKey(preKeyId: Int): PreKeyRecord {
        return preKeyStore.loadPreKey(preKeyId)
    }

    override fun storePreKey(preKeyId: Int, record: PreKeyRecord) {
        preKeyStore.storePreKey(preKeyId, record)
    }

    override fun containsPreKey(preKeyId: Int): Boolean {
        return preKeyStore.containsPreKey(preKeyId)
    }

    override fun removePreKey(preKeyId: Int) {
        preKeyStore.removePreKey(preKeyId)
    }

    override fun loadSession(address: SignalProtocolAddress): SessionRecord {
        return sessionStore.loadSession(address)
    }

    @Throws(NoSessionException::class)
    override fun loadExistingSessions(addresses: List<SignalProtocolAddress>): List<SessionRecord> {
        return sessionStore.loadExistingSessions(addresses)
    }

    override fun getSubDeviceSessions(name: String): List<Int> {
        return sessionStore.getSubDeviceSessions(name)
    }

    override fun storeSession(address: SignalProtocolAddress, record: SessionRecord) {
        sessionStore.storeSession(address, record)
    }

    override fun containsSession(address: SignalProtocolAddress): Boolean {
        return sessionStore.containsSession(address)
    }

    override fun deleteSession(address: SignalProtocolAddress) {
        sessionStore.deleteSession(address)
    }

    override fun deleteAllSessions(name: String) {
        sessionStore.deleteAllSessions(name)
    }

    @Throws(InvalidKeyIdException::class)
    override fun loadSignedPreKey(signedPreKeyId: Int): SignedPreKeyRecord {
        return signedPreKeyStore.loadSignedPreKey(signedPreKeyId)
    }

    override fun loadSignedPreKeys(): List<SignedPreKeyRecord> {
        return signedPreKeyStore.loadSignedPreKeys()
    }

    override fun storeSignedPreKey(signedPreKeyId: Int, record: SignedPreKeyRecord) {
        signedPreKeyStore.storeSignedPreKey(signedPreKeyId, record)
    }

    override fun containsSignedPreKey(signedPreKeyId: Int): Boolean {
        return signedPreKeyStore.containsSignedPreKey(signedPreKeyId)
    }

    override fun removeSignedPreKey(signedPreKeyId: Int) {
        signedPreKeyStore.removeSignedPreKey(signedPreKeyId)
    }

    override fun storeSenderKey(
        sender: SignalProtocolAddress,
        distributionId: UUID,
        record: SenderKeyRecord
    ) {
        senderKeyStore.storeSenderKey(sender, distributionId, record)
    }

    override fun loadSenderKey(
        sender: SignalProtocolAddress,
        distributionId: UUID
    ): SenderKeyRecord {
        return senderKeyStore.loadSenderKey(sender, distributionId)!!
    }
}
package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.richdear.privacykeyboard.inputmethod.signal_protocol.TrustedKey
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil.IdentityKeyPairDeserializer
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil.IdentityKeyPairSerializer
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.state.IdentityKeyStore
import java.util.Objects

class IdentityKeyStoreFullImplementation : IdentityKeyStore {
    @JsonProperty
    private val trustedKeys: MutableList<TrustedKey> = ArrayList()

    @JsonProperty
    @JsonSerialize(using = IdentityKeyPairSerializer::class)
    @JsonDeserialize(using = IdentityKeyPairDeserializer::class)
    private var identityKeyPair: IdentityKeyPair? = null

    @JsonProperty
    private var localRegistrationId = 0

    constructor(identityKeyPair: IdentityKeyPair?, localRegistrationId: Int) {
        this.identityKeyPair = identityKeyPair
        this.localRegistrationId = localRegistrationId
    }

    constructor()

    override fun getIdentityKeyPair(): IdentityKeyPair {
        return identityKeyPair!!
    }

    override fun getLocalRegistrationId(): Int {
        return localRegistrationId
    }

    override fun saveIdentity(address: SignalProtocolAddress, identityKey: IdentityKey): Boolean {
        val existing = getIdentityKeyFromEntryInList(address)

        if (identityKey != existing) {
            trustedKeys.add(TrustedKey(address, identityKey))
            return true
        } else {
            return false
        }
    }

    override fun isTrustedIdentity(
        address: SignalProtocolAddress,
        identityKey: IdentityKey,
        direction: IdentityKeyStore.Direction
    ): Boolean {
        val trusted = getIdentityKeyFromEntryInList(address)
        return (trusted == null || trusted == identityKey)
    }

    override fun getIdentity(address: SignalProtocolAddress): IdentityKey {
        return getIdentityKeyFromEntryInList(address)!!
    }

    private fun getIdentityKeyFromEntryInList(address: SignalProtocolAddress): IdentityKey? {
        for (trustedKey in trustedKeys) {
            if (trustedKey != null && trustedKey.signalProtocolAddress == address) return trustedKey.identityKey
        }
        return null
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as IdentityKeyStoreFullImplementation
        return localRegistrationId == that.localRegistrationId && trustedKeys == that.trustedKeys && identityKeyPair == that.identityKeyPair
    }

    override fun hashCode(): Int {
        return Objects.hash(trustedKeys, identityKeyPair, localRegistrationId)
    }

    fun getTrustedKeys(): List<TrustedKey> {
        return trustedKeys
    }
}

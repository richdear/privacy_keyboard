package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.util.Objects

class TrustedKey {
    var signalProtocolAddress: SignalProtocolAddress? = null
    var identityKey: IdentityKey? = null

    @JsonCreator
    constructor(
        @JsonProperty("signalProtocolAddress") signalProtocolAddress: SignalProtocolAddress?,
        @JsonProperty("identityKey") identityKey: IdentityKey?
    ) {
        this.signalProtocolAddress = signalProtocolAddress
        this.identityKey = identityKey
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as TrustedKey
        return signalProtocolAddress == that.signalProtocolAddress && identityKey == that.identityKey
    }

    override fun hashCode(): Int {
        return Objects.hash(signalProtocolAddress, identityKey)
    }

    override fun toString(): String {
        return "TrustedKey{" +
                "signalProtocolAddress=" + signalProtocolAddress +
                ", identityKey=" + identityKey +
                '}'
    }
}

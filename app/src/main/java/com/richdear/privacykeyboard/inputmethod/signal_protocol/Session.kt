package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.util.Objects

class Session {
    var signalProtocolAddress: SignalProtocolAddress? = null
    var serializedSessionRecord: ByteArray?=null

    @JsonCreator
    constructor(
        @JsonProperty("signalProtocolAddress") signalProtocolAddress: SignalProtocolAddress?,
        @JsonProperty("serializedSessionRecord") serializedSessionRecord: ByteArray
    ) {
        this.signalProtocolAddress = signalProtocolAddress
        this.serializedSessionRecord = serializedSessionRecord
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val session = o as Session
        return signalProtocolAddress == session.signalProtocolAddress
    }

    override fun hashCode(): Int {
        return Objects.hash(signalProtocolAddress)
    }
}

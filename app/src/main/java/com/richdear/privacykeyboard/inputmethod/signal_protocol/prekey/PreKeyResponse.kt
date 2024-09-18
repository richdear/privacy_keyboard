package com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil.IdentityKeyDeserializer
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil.IdentityKeySerializer
import org.signal.libsignal.protocol.IdentityKey
import java.util.Objects

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
class PreKeyResponse {
    @JvmField
    @JsonProperty
    @JsonSerialize(using = IdentityKeySerializer::class)
    @JsonDeserialize(using = IdentityKeyDeserializer::class)
    var identityKey: IdentityKey? = null

    @JsonProperty
    var devices: List<PreKeyResponseItem>? = null
        private set

    constructor()

    constructor(identityKey: IdentityKey?, devices: List<PreKeyResponseItem>?) {
        this.identityKey = identityKey
        this.devices = devices
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PreKeyResponse
        return identityKey == that.identityKey && devices == that.devices
    }

    override fun hashCode(): Int {
        return Objects.hash(identityKey, devices)
    }
}

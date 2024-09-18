package com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Objects

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
class PreKeyResponseItem {
    @JvmField
    @JsonProperty
    var deviceId: Int = 0

    @JvmField
    @JsonProperty
    var registrationId: Int = 0

    @JvmField
    @JsonProperty
    var signedPreKey: SignedPreKeyItem? = null

    @JvmField
    @JsonProperty
    var preKey: PreKeyItem? = null

    constructor(
        deviceId: Int,
        registrationId: Int,
        signedPreKey: SignedPreKeyItem?,
        preKey: PreKeyItem?
    ) {
        this.deviceId = deviceId
        this.registrationId = registrationId
        this.signedPreKey = signedPreKey
        this.preKey = preKey
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PreKeyResponseItem
        return deviceId == that.deviceId && registrationId == that.registrationId && signedPreKey == that.signedPreKey && preKey == that.preKey
    }

    override fun hashCode(): Int {
        return Objects.hash(deviceId, registrationId, signedPreKey, preKey)
    }
}

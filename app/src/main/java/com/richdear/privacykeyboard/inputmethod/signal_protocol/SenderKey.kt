package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Objects

class SenderKey {
    var signalProtocolAddressName: String? = null
    var deviceId: Int = 0
    var distributionId: String? = null

    @JsonCreator
    constructor(
        @JsonProperty("signalProtocolAddress") signalProtocolAddressName: String?,
        @JsonProperty("deviceId") deviceId: Int,
        @JsonProperty("distributionId") distributionId: String?
    ) {
        this.signalProtocolAddressName = signalProtocolAddressName
        this.deviceId = deviceId
        this.distributionId = distributionId
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val senderKey = o as SenderKey
        return deviceId == senderKey.deviceId && signalProtocolAddressName == senderKey.signalProtocolAddressName && distributionId == senderKey.distributionId
    }

    override fun hashCode(): Int {
        return Objects.hash(signalProtocolAddressName, deviceId, distributionId)
    }

    override fun toString(): String {
        return "$signalProtocolAddressName.$deviceId.$distributionId"
    }
}

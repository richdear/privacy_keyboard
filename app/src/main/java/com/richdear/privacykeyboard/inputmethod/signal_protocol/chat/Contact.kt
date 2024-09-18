package com.richdear.privacykeyboard.inputmethod.signal_protocol.chat

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.util.Objects

class Contact {
    var firstName: String? = null
    var lastName: String? = null
    var deviceId: Int = 0
    var signalProtocolAddressName: String? = null
    var isVerified: Boolean = false

    @JsonProperty
    var signalProtocolAddress: SignalProtocolAddress? = null

    @JsonCreator
    constructor(
        @JsonProperty("firstName") firstName: String?,
        @JsonProperty("lastName") lastName: String?,
        @JsonProperty("signalProtocolAddressName") signalProtocolAddressName: String?,
        @JsonProperty("deviceId") deviceId: Int,
        @JsonProperty("verified") verified: Boolean
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.signalProtocolAddressName = signalProtocolAddressName
        this.deviceId = deviceId
        this.signalProtocolAddress = SignalProtocolAddress(signalProtocolAddressName, deviceId)
        this.isVerified = verified
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val contact = o as Contact
        return deviceId == contact.deviceId && isVerified == contact.isVerified && firstName == contact.firstName && lastName == contact.lastName && signalProtocolAddressName == contact.signalProtocolAddressName && signalProtocolAddress == contact.signalProtocolAddress
    }

    override fun hashCode(): Int {
        return Objects.hash(
            firstName,
            lastName,
            deviceId,
            signalProtocolAddressName,
            isVerified,
            signalProtocolAddress
        )
    }

    override fun toString(): String {
        return firstName + '.' + lastName + '.' + deviceId + '.' + signalProtocolAddressName + '.' + signalProtocolAddress + '.' + isVerified
    }
}

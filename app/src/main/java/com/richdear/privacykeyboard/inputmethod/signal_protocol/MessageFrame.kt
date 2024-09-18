package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey.PreKeyResponse
import java.io.Serializable
import java.util.Objects


@JsonFormat(shape = JsonFormat.Shape.ARRAY)
class MessageFrame : Serializable {
    @JvmField
    @JsonProperty
    var preKeyResponse: PreKeyResponse? = null

    @JvmField
    @JsonProperty
    var ciphertextMessage: ByteArray?=null

    @JvmField
    @JsonProperty
    var ciphertextType: Int = 0

    @JvmField
    @JsonProperty
    var timestamp: Long = 0

    @JvmField
    @JsonProperty
    var signalProtocolAddressName: String? = null

    @JvmField
    @JsonProperty
    var deviceId: Int = 0

    constructor(
        ciphertextMessage: ByteArray,
        ciphertextType: Int,
        signalProtocolAddressName: String?,
        deviceId: Int
    ) {
        this.ciphertextMessage = ciphertextMessage
        this.ciphertextType = ciphertextType
        this.signalProtocolAddressName = signalProtocolAddressName
        this.deviceId = deviceId
        this.timestamp = System.currentTimeMillis()
    }

    constructor(
        preKeyResponse: PreKeyResponse?,
        signalProtocolAddressName: String?,
        deviceId: Int
    ) {
        this.preKeyResponse = preKeyResponse
        this.signalProtocolAddressName = signalProtocolAddressName
        this.deviceId = deviceId
        this.timestamp = System.currentTimeMillis()
    }

    constructor()

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as MessageFrame
        return ciphertextType == that.ciphertextType && timestamp == that.timestamp && deviceId == that.deviceId && preKeyResponse == that.preKeyResponse && ciphertextMessage.contentEquals(
            that.ciphertextMessage
        ) && signalProtocolAddressName == that.signalProtocolAddressName
    }

    override fun hashCode(): Int {
        var result = Objects.hash(
            preKeyResponse,
            ciphertextType,
            timestamp,
            signalProtocolAddressName,
            deviceId
        )
        result = 31 * result + ciphertextMessage.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "MessageFrame{" +
                "preKeyResponse=" + preKeyResponse +
                ", ciphertextMessage=" + ciphertextMessage.contentToString() +
                ", ciphertextType=" + ciphertextType +
                ", timestamp=" + timestamp +
                ", signalProtocolAddressName='" + signalProtocolAddressName + '\'' +
                ", deviceId=" + deviceId +
                '}'
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}

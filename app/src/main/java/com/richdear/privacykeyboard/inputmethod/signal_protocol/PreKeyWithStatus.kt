package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Objects

class PreKeyWithStatus @JsonCreator constructor(
    @param:JsonProperty("serializedPreKeyRecord") val serializedPreKeyRecord: ByteArray,
    @param:JsonProperty("isUsed") var isUsed: Boolean
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PreKeyWithStatus
        return isUsed == that.isUsed && serializedPreKeyRecord.contentEquals(that.serializedPreKeyRecord)
    }

    override fun hashCode(): Int {
        var result = Objects.hash(isUsed)
        result = 31 * result + serializedPreKeyRecord.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "PreKeyWithStatus{" +
                "serializedPreKeyRecord=" + serializedPreKeyRecord.contentToString() +
                ", isUsed=" + isUsed +
                '}'
    }
}

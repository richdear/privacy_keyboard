package com.richdear.privacykeyboard.inputmethod.signal_protocol.chat

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.Objects

class MessageStorage @JsonCreator constructor(
    // contact name/address name-uuid
  @JvmField @param:JsonProperty("contactUUID") var contactUUID: String,
  @JvmField @param:JsonProperty("senderUUID") var senderUUID: String,
  @JvmField @param:JsonProperty("recipientUUID") var recipientUUID: String,
  @JvmField @param:JsonProperty("timestamp") val timestamp: Instant,
  @JvmField @param:JsonProperty("unencryptedMessage") val unencryptedMessage: String
) {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val message = o as MessageStorage
        return contactUUID == message.contactUUID && senderUUID == message.senderUUID && recipientUUID == message.recipientUUID && timestamp == message.timestamp && unencryptedMessage == message.unencryptedMessage
    }

    override fun hashCode(): Int {
        return Objects.hash(contactUUID, senderUUID, recipientUUID, timestamp, unencryptedMessage)
    }

    override fun toString(): String {
        return "MessageStorage{" +
                "contactUUID='" + contactUUID + '\'' +
                ", senderUUID='" + senderUUID + '\'' +
                ", recipientUUID='" + recipientUUID + '\'' +
                ", timestamp=" + timestamp +
                ", unencryptedMessage='" + unencryptedMessage + '\'' +
                '}'
    }

    fun getSenderUUID(): String {
        return senderUUID
    }

    fun setSenderUUID(senderUUID: String?) {
        this.senderUUID = senderUUID!!
    }

    fun getRecipientUUID(): String {
        return recipientUUID
    }

    fun setRecipientUUID(recipientUUID: String?) {
        this.recipientUUID = recipientUUID!!
    }

    fun getTimestamp(): Instant {
        return timestamp
    }

    fun getUnencryptedMessage(): String {
        return unencryptedMessage
    }

    fun getContactUUID(): String {
        return contactUUID
    }

    fun setContactUUID(contactUUID: String?) {
        this.contactUUID = contactUUID!!
    }


}

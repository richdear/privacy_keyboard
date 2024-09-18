package com.richdear.privacykeyboard.inputmethod.signal_protocol.helper

import android.content.Context
import android.util.Log
import com.richdear.privacykeyboard.inputmethod.signal_protocol.Account
import com.richdear.privacykeyboard.inputmethod.signal_protocol.ProtocolIdentifier
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.Contact
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataAbstractStore
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.SignalProtocolStoreFullImplementation
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.io.IOException

class StorageHelper(private val mContext: Context?) {
    private val messageSharedPreferenceName = "protocol"

    val accountFromSharedPreferences: Account
        get() {
            val name = getClassFromSharedPreferences(ProtocolIdentifier.UNIQUE_USER_ID) as String?
            val signalProtocolStore =
                getClassFromSharedPreferences(ProtocolIdentifier.PROTOCOL_STORE) as SignalProtocolStoreFullImplementation?
            val identityKeyPair = signalProtocolStore!!.identityKeyPair

            val metadataStore =
                getClassFromSharedPreferences(ProtocolIdentifier.METADATA_STORE) as PreKeyMetadataAbstractStore?
            val signalProtocolAddress =
                getClassFromSharedPreferences(ProtocolIdentifier.PROTOCOL_ADDRESS) as SignalProtocolAddress?

            val unencryptedMessages = JsonUtil.convertUnencryptedMessagesList(
                getClassFromSharedPreferences(ProtocolIdentifier.UNENCRYPTED_MESSAGES) as ArrayList<MessageStorage>?
                    ?: ArrayList()
            )
            val contactList = JsonUtil.convertContactsList(
                getClassFromSharedPreferences(ProtocolIdentifier.CONTACTS) as ArrayList<Contact>?
                    ?: ArrayList()
            )

            val account = Account(
                name.toString(),
                signalProtocolAddress!!.deviceId,
                identityKeyPair,
                metadataStore,
                signalProtocolStore,
                signalProtocolAddress
            ) // deviceId is static
            account.unencryptedMessages = unencryptedMessages
            account.contactList = contactList

            return account
        }

    fun storeAllInformationInSharedPreferences(account: Account) {
        storeMetaDataStoreInSharedPreferences(account.metadataStore)
        storeUniqueUserIdInSharedPreferences(account.name)
        storeSignalProtocolInSharedPreferences(account.signalProtocolStore) // incl. registrationId + identityKeyPair
        storeSignalProtocolAddressInSharedPreferences(account.signalProtocolAddress)
        storeDeviceIdInSharedPreferences(account.deviceId)
        storeUnencryptedMessagesMapInSharedPreferences(account.unencryptedMessages)
        storeContactListInSharedPreferences(account.contactList)
    }

    private fun storeUnencryptedMessagesMapInSharedPreferences(unencryptedMessages: ArrayList<MessageStorage>?) {
        storeInSharedPreferences(ProtocolIdentifier.UNENCRYPTED_MESSAGES, unencryptedMessages)
    }

    fun storeMetaDataStoreInSharedPreferences(metadataStore: PreKeyMetadataAbstractStore?) {
        storeInSharedPreferences(ProtocolIdentifier.METADATA_STORE, metadataStore)
    }

    fun storeUniqueUserIdInSharedPreferences(uniqueUserId: String?) {
        storeInSharedPreferences(ProtocolIdentifier.UNIQUE_USER_ID, uniqueUserId)
    }

    fun storeSignalProtocolInSharedPreferences(signalProtocolStore: SignalProtocolStoreFullImplementation?) {
        storeInSharedPreferences(ProtocolIdentifier.PROTOCOL_STORE, signalProtocolStore)
    }

    fun storeSignalProtocolAddressInSharedPreferences(signalProtocolAddress: SignalProtocolAddress?) {
        storeInSharedPreferences(ProtocolIdentifier.PROTOCOL_ADDRESS, signalProtocolAddress)
    }

    fun storeDeviceIdInSharedPreferences(deviceId: Int?) {
        storeInSharedPreferences(ProtocolIdentifier.DEVICE_ID, deviceId)
    }

    private fun storeContactListInSharedPreferences(contactList: ArrayList<Contact>?) {
        storeInSharedPreferences(ProtocolIdentifier.CONTACTS, contactList)
    }

    fun storeInSharedPreferences(protocolIdentifier: ProtocolIdentifier, objectToStore: Any?) {
        if (mContext == null) {
            logError("mContext")
            return
        }
        val sharedPreferences =
            mContext.getSharedPreferences(messageSharedPreferenceName, Context.MODE_PRIVATE)
        if (sharedPreferences == null) {
            logError("sharedPreferences")
            return
        }
        sharedPreferences.edit()
            .putString(protocolIdentifier.toString(), JsonUtil.toJson(objectToStore)).apply()
    }

    fun getClassFromSharedPreferences(protocolIdentifier: ProtocolIdentifier): Any? {
        if (mContext == null) {
            logError("mContext")
            return null
        }
        val sharedPreferences =
            mContext.getSharedPreferences(messageSharedPreferenceName, Context.MODE_PRIVATE)
        if (sharedPreferences == null) {
            logError("sharedPreferences")
            return null
        }
        val json = sharedPreferences.getString(protocolIdentifier.toString(), null)
        try {
            if (json == null) throw IOException("Required content not found! Was is stored before?")
            return JsonUtil.fromJson(json, protocolIdentifier.className)
        } catch (e: IOException) {
            Log.e(TAG, "Error: Could not process $protocolIdentifier from sharedPreferences")
            e.printStackTrace()
        }
        return null
    }

    private fun logError(nameObject: String) {
        Log.e(TAG, "Error: Possible null value for $nameObject")
    }

    companion object {
        val TAG: String = StorageHelper::class.java.simpleName
    }
}

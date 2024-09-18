package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.Contact
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactDuplicatedException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactUnknownException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataAbstractStore
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.SignalProtocolStoreFullImplementation
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.util.Objects

class Account {
    val name: String
    val deviceId: Int
    var identityKeyPair: IdentityKeyPair? = null
    var metadataStore: PreKeyMetadataAbstractStore? = null
    var signalProtocolStore: SignalProtocolStoreFullImplementation? = null
    var signalProtocolAddress: SignalProtocolAddress? = null
    var unencryptedMessages: ArrayList<MessageStorage>? = null
    @JvmField
    var contactList: ArrayList<Contact>? = null

    constructor(
        name: String,
        deviceId: Int,
        identityKeyPair: IdentityKeyPair?,
        metadataStore: PreKeyMetadataAbstractStore?,
        signalProtocolStore: SignalProtocolStoreFullImplementation?,
        signalProtocolAddress: SignalProtocolAddress?
    ) {
        this.name = name
        this.deviceId = deviceId
        this.identityKeyPair = identityKeyPair
        this.metadataStore = metadataStore
        this.signalProtocolStore = signalProtocolStore
        this.signalProtocolAddress = signalProtocolAddress
        this.unencryptedMessages = ArrayList()
        this.contactList = ArrayList()
    }

    // testing only
    constructor(name: String, mDeviceId: Int) {
        this.name = name
        this.deviceId = mDeviceId
    }

    @Throws(RuntimeException::class)
    fun addUnencryptedMessage(contact: Contact?, storageMessage: MessageStorage) {
        if (unencryptedMessages == null) throw RuntimeException("Error: UnencryptedMessage could not be saved. mUnencryptedMessages is null")
        unencryptedMessages!!.add(storageMessage)
    }

    fun removeAllUnencryptedMessages(contact: Contact) {
        val operatedList: MutableList<MessageStorage> = ArrayList()
        unencryptedMessages!!.stream()
            .filter { m: MessageStorage -> m.contactUUID == contact.signalProtocolAddressName }
            .forEach { e: MessageStorage -> operatedList.add(e) }
        unencryptedMessages!!.removeAll(operatedList)
    }

    @Throws(ContactDuplicatedException::class)
    fun addContactToContactList(contact: Contact) {
        if (contactList!!.contains(contact)) throw ContactDuplicatedException("Error: Contact " + contact.firstName + " " + contact.lastName + " already exists in contact list and will not be saved!")
        contactList!!.add(contact)
    }

    @Throws(ContactUnknownException::class)
    fun updateContactInContactList(contact: Contact) {
        if (contactList!!.contains(contact)) {
            var indexContact = 0
            for (i in contactList!!.indices) {
                if (contactList!![i].signalProtocolAddressName == contact.signalProtocolAddressName) {
                    indexContact = i
                }
            }
            contactList!![indexContact] = contact
        } else {
            throw ContactUnknownException("Contact does not exist in contact list")
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val account = o as Account
        return deviceId == account.deviceId && name == account.name && identityKeyPair == account.identityKeyPair && metadataStore == account.metadataStore && signalProtocolStore == account.signalProtocolStore && signalProtocolAddress == account.signalProtocolAddress && unencryptedMessages == account.unencryptedMessages && contactList == account.contactList
    }

    override fun hashCode(): Int {
        return Objects.hash(
            name,
            deviceId,
            identityKeyPair,
            metadataStore,
            signalProtocolStore,
            signalProtocolAddress,
            unencryptedMessages,
            contactList
        )
    }

    override fun toString(): String {
        return "Account{" +
                "mName='" + name + '\'' +
                ", mDeviceId=" + deviceId +
                ", mIdentityKeyPair=" + identityKeyPair +
                ", mMetadataStore=" + metadataStore +
                ", mSignalProtocolStore=" + signalProtocolStore +
                ", mSignalProtocolAddress=" + signalProtocolAddress +
                ", mUnencryptedMessages=" + unencryptedMessages +
                ", contactList=" + contactList +
                '}'
    }
}


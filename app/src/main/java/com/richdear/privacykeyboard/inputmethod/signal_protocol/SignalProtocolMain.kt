package com.richdear.privacykeyboard.inputmethod.signal_protocol

import android.content.Context
import android.util.Log
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.Contact
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactDuplicatedException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactIncorrectException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactUnknownException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.MessageUnknownException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.helper.StorageHelper
import com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey.PreKeyItem
import com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey.PreKeyResponse
import com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey.PreKeyResponseItem
import com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey.SignedPreKeyItem
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataAbstractStore
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataStoreFullImplementation
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.SignalProtocolStoreFullImplementation
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.generateAndStoreOneTimePreKey
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.generateAndStoreOneTimePreKeys
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.generateAndStoreSignedPreKey
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.generateIdentityKeyPair
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.generateRegistrationId
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.getUnusedOneTimePreKeyId
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.KeyUtil.renewSignedPreKeyIfNecessary
import org.signal.libsignal.protocol.DuplicateMessageException
import org.signal.libsignal.protocol.InvalidKeyException
import org.signal.libsignal.protocol.InvalidKeyIdException
import org.signal.libsignal.protocol.InvalidMessageException
import org.signal.libsignal.protocol.InvalidVersionException
import org.signal.libsignal.protocol.LegacyMessageException
import org.signal.libsignal.protocol.NoSessionException
import org.signal.libsignal.protocol.SessionBuilder
import org.signal.libsignal.protocol.SessionCipher
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.UntrustedIdentityException
import org.signal.libsignal.protocol.ecc.Curve
import org.signal.libsignal.protocol.ecc.ECPublicKey
import org.signal.libsignal.protocol.fingerprint.Fingerprint
import org.signal.libsignal.protocol.fingerprint.NumericFingerprintGenerator
import org.signal.libsignal.protocol.message.CiphertextMessage
import org.signal.libsignal.protocol.message.PreKeySignalMessage
import org.signal.libsignal.protocol.message.SignalMessage
import org.signal.libsignal.protocol.state.PreKeyBundle
import java.io.IOException
import java.time.Instant
import java.util.LinkedList
import java.util.Optional
import java.util.Random
import java.util.UUID
import java.util.stream.Collectors


// main class that combines all Signal protocol functionalities!
class SignalProtocolMain private constructor() {
    private var messageStorageHelper: StorageHelper? = null
    var account: Account? = null

    @Throws(ContactUnknownException::class)
    private fun verifyContactInContactList(contact: Contact?) {
        if (contact == null) return
        contact.isVerified = true
        account!!.updateContactInContactList(contact)
        storeAllAccountInformationInSharedPreferences()
    }

    private fun createFingerprint(contact: Contact?): Fingerprint? {
        if (contact == null) return null

        val localIdentity = account!!.identityKeyPair!!.publicKey
        // session for contract for public key
        val remoteIdentity = account!!.signalProtocolStore!!.sessionStore.getPublicKeyFromSession(
            contact.signalProtocolAddress!!
        )

        if (localIdentity == null && remoteIdentity == null) return null

        // using UUID!
        val version = 2
        val localId = account!!.signalProtocolAddress!!.name.toByteArray()
        val remoteId = contact.signalProtocolAddress!!.name.toByteArray()

        val numericFingerprintGenerator = NumericFingerprintGenerator(5200)

        return numericFingerprintGenerator.createFor(
            version,
            localId, localIdentity,
            remoteId, remoteIdentity
        )
    }

    private val contactListFromAccount: ArrayList<Contact>?
        get() {
            if (account != null) {
                return account!!.contactList
            }
            return null
        }

    private fun extractContactFromFrame(messageFrame: MessageFrame): Contact? {
        val signalProtocolAddress = SignalProtocolAddress(
            messageFrame.signalProtocolAddressName,
            messageFrame.deviceId
        )
        return getContactFromAddressInContactList(signalProtocolAddress)
    }

    private fun getContactFromAddressInContactList(signalProtocolAddress: SignalProtocolAddress): Contact? {
        val contacts = contactListFromAccount ?: return null
        return contacts.stream()
            .filter { c: Contact? -> c!!.signalProtocolAddress == signalProtocolAddress }
            .findFirst().orElse(null)
    }

    @Throws(ContactDuplicatedException::class, ContactIncorrectException::class)
    private fun createAndAddContactToList(
        firstName: CharSequence?,
        lastName: CharSequence,
        signalProtocolAddressName: String?,
        deviceId: Int
    ): Contact {
        if (firstName == null || firstName.length == 0 || signalProtocolAddressName == null || deviceId == 0) throw ContactIncorrectException(
            "Error: Contact is incorrect. Some information is missing! Please fill all information!"
        )

        val recipient = Contact(
            firstName.toString(),
            lastName.toString(),
            signalProtocolAddressName,
            deviceId,
            false
        )
        account!!.addContactToContactList(recipient)
        storeAllAccountInformationInSharedPreferences()
        return recipient
    }

    private fun removeContact(contactToRemove: Contact) {
        val contacts = contactListFromAccount ?: return

        Log.d(
            TAG,
            "Contact is being deleted: " + contactToRemove.firstName + " " + contactToRemove.lastName
        )
        val newContacts = ArrayList<Contact>()
        for (contact in contacts) {
            if (!contact!!.equals(contactToRemove)) {
                newContacts.add(contact)
            }
        }
        account!!.contactList = newContacts

        Log.d(
            TAG,
            "Wiping session for contact: " + contactToRemove.firstName + " " + contactToRemove.lastName
        )
        if (account!!.signalProtocolStore!!.sessionStore.containsSession(contactToRemove.signalProtocolAddress!!)) {
            account!!.signalProtocolStore!!.sessionStore.deleteSession(contactToRemove.signalProtocolAddress!!)
        }

        Log.d(
            TAG,
            "Removing unencrypted messages for contact: " + contactToRemove.firstName + " " + contactToRemove.lastName
        )
        account!!.removeAllUnencryptedMessages(contactToRemove)

        storeAllAccountInformationInSharedPreferences()
    }

    @Throws(ContactUnknownException::class)
    private fun getUnencryptedMessagesListFromAccount(contact: Contact?): List<MessageStorage>? {
        if (account != null && contact != null) {
            val messagesWithContact = account!!.unencryptedMessages!!.stream()
                .filter { m: MessageStorage -> m.contactUUID == contact.signalProtocolAddressName }
                .collect(Collectors.toList())
            if (messagesWithContact.size == 0) {
                throw ContactUnknownException("There are no messages for contact: " + contact.firstName + " " + contact.lastName)
            }
            return messagesWithContact
        }
        return null
    }

    private val accountName: String
        get() = account!!.name.toString()

    private fun encrypt(
        unencryptedMessage: String?,
        signalProtocolAddress: SignalProtocolAddress?
    ): MessageFrame? {
        if (unencryptedMessage == null || signalProtocolAddress == null) return null
        try {
            var messageFrame: MessageFrame? = null
            // check time for signedPreKey, create new one if necessary, delete old after
            if (renewSignedPreKeyIfNecessary(
                    account!!.signalProtocolStore, account!!.metadataStore
                )
            ) {
                // signed pre key is renewed, send new preKeyBundle appended to new message
                messageFrame = preKeyResponseMessage
            }

            val sessionCipher = SessionCipher(account!!.signalProtocolStore, signalProtocolAddress)
            val ciphertextMessage = sessionCipher.encrypt(unencryptedMessage.toByteArray())
            logMessageType(ciphertextMessage.type)

            if (messageFrame == null) {
                messageFrame = MessageFrame(
                    ciphertextMessage.serialize(),
                    ciphertextMessage.type,
                    account!!.name,
                    account!!.deviceId
                )
            } else {
                // add ciphertextMessage with type to preKeyResponse message
                messageFrame.ciphertextMessage = ciphertextMessage.serialize()
                messageFrame.ciphertextType = ciphertextMessage.type
                Log.d(TAG, "Signed pre key was rotated. Adding ciphertextMessage.....")
            }

            // store unencrypted message somewhere with recipient in map
            Log.d(TAG, "Trying to save unencrypted message.....")
            storeUnencryptedMessageWithInMap(
                account,
                signalProtocolAddress,
                unencryptedMessage,
                Instant.ofEpochMilli(messageFrame.timestamp),
                true
            )

            storeAllAccountInformationInSharedPreferences()

            return messageFrame
        } catch (e: UntrustedIdentityException) {
            e.printStackTrace()
        } catch (e: ContactIncorrectException) {
            e.printStackTrace()
        }
        return null
    }

    private fun logMessageType(type: Int) {
        if (type == 3) {
            Log.d(TAG, "CiphertextMessage = PRE_KEY")
        } else if (type == 2) {
            Log.d(TAG, "CiphertextMessage = WHISPER_TYPE")
        }
    }

    private fun processPreKeyResponse(
        messageFrame: MessageFrame?,
        signalProtocolAddress: SignalProtocolAddress
    ): Boolean {
        if (messageFrame == null) return false
        try {
            // create new session with recipients protocol address when preKeyResponse was send
            if (messageFrame.preKeyResponse != null) {
                val preKeyBundle = createPreKeyBundle(messageFrame.preKeyResponse)

                createSession(preKeyBundle, signalProtocolAddress)
                Log.d(
                    TAG,
                    "New session with PreKeyBundle created: " + sessionExists(signalProtocolAddress)
                )
                Log.d(
                    TAG,
                    "Number of pre key ids: " + account!!.signalProtocolStore!!.preKeyStore.size
                )
                storeAllAccountInformationInSharedPreferences()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    @Throws(
        ContactIncorrectException::class,
        MessageUnknownException::class,
        InvalidMessageException::class,
        InvalidVersionException::class,
        LegacyMessageException::class,
        InvalidKeyException::class,
        UntrustedIdentityException::class,
        DuplicateMessageException::class,
        InvalidKeyIdException::class,
        NoSessionException::class
    )
    private fun decrypt(
        messageFrame: MessageFrame?,
        signalProtocolAddress: SignalProtocolAddress
    ): String? {
        if (messageFrame == null) return null
        val decryptedMessage: String

        val sessionCipher = SessionCipher(account!!.signalProtocolStore, signalProtocolAddress)

        // refresh session with new signed pre key from recipient
        if (messageFrame.ciphertextMessage != null && messageFrame.preKeyResponse != null) {
            Log.d(TAG, "New message with cipherText and refreshed preKeyResponse received...")
            processPreKeyResponseMessage(messageFrame, signalProtocolAddress)
        }

        logMessageType(messageFrame.ciphertextType)

        val plaintext: ByteArray?
        if (messageFrame.ciphertextType == CiphertextMessage.PREKEY_TYPE) {
            // decrypting message and storing session with preKeySignalMessage
            val preKeySignalMessage = PreKeySignalMessage(messageFrame.ciphertextMessage)

            Log.d(
                TAG,
                "PreKeySignalMessage: Used signed prekey id: " + preKeySignalMessage.signedPreKeyId
            )

            plaintext = sessionCipher.decrypt(preKeySignalMessage)
            decryptedMessage = String(plaintext)

            if (preKeySignalMessage.preKeyId.isPresent) generateAndStoreOneTimePreKey(
                account!!.signalProtocolStore!!, preKeySignalMessage.preKeyId.get()
            )

            Log.d(
                TAG,
                "New session with PreKeySignalMessage created (after decryption): " + sessionExists(
                    signalProtocolAddress
                )
            )
            Log.d(TAG, "Number of pre key ids: " + account!!.signalProtocolStore!!.preKeyStore.size)
        } else if (messageFrame.ciphertextType == CiphertextMessage.WHISPER_TYPE) {
            // will only decrypt message (session already exists)
            plaintext = sessionCipher.decrypt(SignalMessage(messageFrame.ciphertextMessage))
            decryptedMessage = String(plaintext)
            Log.d(TAG, "Number of pre key ids: " + account!!.signalProtocolStore!!.preKeyStore.size)
        } else {
            throw MessageUnknownException("Found message is not of type PRE_KEY or WHISPER_TYPE")
        }

        if (plaintext != null) {
            // save unencrypted messages with recipient in map
            Log.d(TAG, "Trying to save unencrypted message...")
            storeUnencryptedMessageWithInMap(
                account,
                signalProtocolAddress,
                decryptedMessage,
                Instant.ofEpochMilli(messageFrame.timestamp),
                false
            )
        }
        storeAllAccountInformationInSharedPreferences()

        return decryptedMessage
    }

    @Throws(IOException::class)
    fun createPreKeyBundle(preKeyResponse: PreKeyResponse?): PreKeyBundle {
        if (preKeyResponse!!.devices == null || preKeyResponse.devices!!.size < 1) throw IOException(
            "Prekey list is empty"
        )

        val device = preKeyResponse.devices!![0]
        var preKey: ECPublicKey? = null
        var signedPreKey: ECPublicKey? = null
        var signedPreKeySignature: ByteArray? = null
        var preKeyId = -1
        var signedPreKeyId = -1

        if (device.preKey != null) {
            preKeyId = device.preKey!!.keyId
            preKey = device.preKey!!.publicKey
        }

        if (device.signedPreKey != null) {
            signedPreKeyId = device.signedPreKey!!.keyId
            signedPreKey = device.signedPreKey!!.publicKey
            signedPreKeySignature = device.signedPreKey!!.signature
        }

        return PreKeyBundle(
            device.registrationId, device.deviceId, preKeyId, preKey,
            signedPreKeyId, signedPreKey, signedPreKeySignature, preKeyResponse.identityKey
        )
    }

    @Throws(ContactIncorrectException::class)
    private fun storeUnencryptedMessageWithInMap(
        account: Account?,
        signalProtocolAddress: SignalProtocolAddress,
        decryptedMessage: String,
        timestamp: Instant,
        isFromOwnAccount: Boolean
    ) {
        val recipient = if (testIsRunning) {
            // for running tests only!
           Optional.of(
                Contact(
                    "test",
                    "test",
                    signalProtocolAddress.name,
                    signalProtocolAddress.deviceId,
                    false
                )
            )
        } else {
           contactList!!.stream()
                .filter { c: Contact? -> c!!.signalProtocolAddress == signalProtocolAddress }
                .findFirst()
        }

        if (!recipient.isPresent) throw ContactIncorrectException("No contact found with signalProtocolAddress: $signalProtocolAddress")
        val storageMessage = if (isFromOwnAccount) {
            MessageStorage(
                signalProtocolAddress.name,
                account!!.signalProtocolAddress!!.name,
                signalProtocolAddress.name,
                timestamp,
                decryptedMessage
            )
        } else {
            MessageStorage(
                signalProtocolAddress.name,
                signalProtocolAddress.name,
                account!!.signalProtocolAddress!!.name,
                timestamp,
                decryptedMessage
            )
        }

        recipient.ifPresent { contact: Contact? ->
            account.addUnencryptedMessage(
                contact,
                storageMessage
            )
        }
    }

    private fun sessionExists(signalProtocolAddress: SignalProtocolAddress): Boolean {
        return account!!.signalProtocolStore!!.containsSession(signalProtocolAddress)
    }

    private fun createPreKeyResponseMessage(): MessageFrame? {
        try {
            val preKeyResponse = createPreKeyResponse()
            return MessageFrame(
                preKeyResponse,
                account!!.signalProtocolAddress!!.name,
                account!!.signalProtocolAddress!!.deviceId
            )
        } catch (e: InvalidKeyIdException) {
            Log.e(TAG, "E: Creating pre key response message failed")
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "E: Creating pre key response message failed")
            e.printStackTrace()
        }
        return null
    }

    @get:Throws(
        InvalidKeyIdException::class,
        InvalidKeyException::class
    )
    private val preKeyBundle: PreKeyBundle

        get() {
            // check time passed since generation of signedPreKey and generate new one if necessary (and delete old ones after archive age)
            renewSignedPreKeyIfNecessary(
                account!!.signalProtocolStore, account!!.metadataStore
            )

            val signedPreKeySignature = Curve.calculateSignature(
                account!!.signalProtocolStore!!.identityKeyPair.privateKey,
                account!!.signalProtocolStore!!.loadSignedPreKey(account!!.metadataStore!!.activeSignedPreKeyId).keyPair.publicKey.serialize()
            )

            val preKeyId = getUnusedOneTimePreKeyId(
                account!!.signalProtocolStore
            )!!

            Log.d(TAG, "Creating PreKeyBundle with pre key id: $preKeyId")
            val preKeyBundle = PreKeyBundle(
                account!!.signalProtocolStore!!.localRegistrationId,
                account!!.deviceId,
                preKeyId,
                account!!.signalProtocolStore!!.loadPreKey(preKeyId).keyPair.publicKey,
                account!!.metadataStore!!.activeSignedPreKeyId,
                account!!.signalProtocolStore!!.loadSignedPreKey(account!!.metadataStore!!.activeSignedPreKeyId).keyPair.publicKey,
                signedPreKeySignature,
                account!!.signalProtocolStore!!.identityKeyPair.publicKey
            )

            return preKeyBundle
        }

    @Throws(InvalidKeyIdException::class, InvalidKeyException::class)
    private fun createPreKeyResponse(): PreKeyResponse {
        val preKeyBundle = preKeyBundle

        val responseItems: MutableList<PreKeyResponseItem> = LinkedList()
        responseItems.add(
            PreKeyResponseItem(
                preKeyBundle.deviceId,
                preKeyBundle.registrationId,
                SignedPreKeyItem(
                    preKeyBundle.signedPreKeyId,
                    preKeyBundle.signedPreKey,
                    preKeyBundle.signedPreKeySignature
                ),
                PreKeyItem(preKeyBundle.preKeyId, preKeyBundle.preKey)
            )
        )

        return PreKeyResponse(preKeyBundle.identityKey, responseItems)
    }


    private fun createSession(
        preKeyBundle: PreKeyBundle,
        recipientSignalProtocolAddress: SignalProtocolAddress
    ) {
        try {
            val sessionBuilder =
                SessionBuilder(account!!.signalProtocolStore, recipientSignalProtocolAddress)
            sessionBuilder.process(preKeyBundle)
            storeAllAccountInformationInSharedPreferences()
        } catch (e: InvalidKeyException) {
            Log.e(
                TAG,
                "E: Creating session with recipient id " + recipientSignalProtocolAddress.name + " failed"
            )
            e.printStackTrace()
        } catch (e: UntrustedIdentityException) {
            Log.e(
                TAG,
                "E: Creating session with recipient id " + recipientSignalProtocolAddress.name + " failed"
            )
            e.printStackTrace()
        }
    }


    private fun initializeProtocol() {
        val uniqueUserId = UUID.randomUUID().toString()
        val deviceId = Random().nextInt(10000)
        val signalProtocolAddress = SignalProtocolAddress(uniqueUserId, deviceId)
        val metadataStore: PreKeyMetadataAbstractStore = PreKeyMetadataStoreFullImplementation()

        // create IdentityKeyPair, registrationId
        val identityKeyPair = generateIdentityKeyPair()
        val registrationId = generateRegistrationId()

        // create new signalProtocolStore
        val signalProtocolStore = SignalProtocolStoreFullImplementation(identityKeyPair, registrationId)

        // create and store preKeys in PreKeyStore
        generateAndStoreOneTimePreKeys(signalProtocolStore, metadataStore)

        // create and store signed prekey in SignedPreKeyStore
        val signedPreKey = generateAndStoreSignedPreKey(signalProtocolStore, metadataStore)

        metadataStore.activeSignedPreKeyId = signedPreKey.id
        metadataStore.isSignedPreKeyRegistered = true

        // create account for device
        account = Account(
            uniqueUserId,
            deviceId,
            identityKeyPair,
            metadataStore,
            signalProtocolStore,
            signalProtocolAddress
        )

        storeAllAccountInformationInSharedPreferences()
    }

    private fun reloadAccountFromSharedPreferences() {
        account = messageStorageHelper!!.accountFromSharedPreferences
    }

    private fun storeAllAccountInformationInSharedPreferences() {
        if (messageStorageHelper != null) {
            messageStorageHelper!!.storeAllInformationInSharedPreferences(account!!)
        } else {
            Log.e(TAG, "E: No protocol resources were stored (mStorageHelper is null)")
        }
    }

    private fun initializeStorageHelper(context: Context?) {
        if (context == null) {
            Log.e(TAG, "E: mStorageHelper cannot get initialized because context is null")
            return
        }
        messageStorageHelper = StorageHelper(context)
    }

    companion object {
        val TAG: String = SignalProtocolMain::class.java.simpleName

        val instance: SignalProtocolMain = SignalProtocolMain()

        // switch for test cases identification
        var testIsRunning: Boolean = false

        @JvmStatic
        fun initialize(context: Context?) {
            Log.d(TAG, "Initializing signal protocol.....")
            instance.initializeStorageHelper(context)
            instance.initializeProtocol()
        }

        @JvmStatic
        fun reloadAccount(context: Context?) {
            Log.d(TAG, "Refreshing local account for signal protocol... not first application run!")
            instance.initializeStorageHelper(context)
            instance.reloadAccountFromSharedPreferences()
            instance.storeAllAccountInformationInSharedPreferences()
        }

        @JvmStatic
        fun encryptMessage(
            unencryptedMessage: String?,
            signalProtocolAddress: SignalProtocolAddress?
        ): MessageFrame? {
            Log.d(TAG, "Encrypting signal message.....")
            return instance.encrypt(unencryptedMessage, signalProtocolAddress)
        }

        @JvmStatic
        @Throws(
            InvalidMessageException::class,
            ContactIncorrectException::class,
            MessageUnknownException::class,
            UntrustedIdentityException::class,
            DuplicateMessageException::class,
            InvalidVersionException::class,
            InvalidKeyIdException::class,
            LegacyMessageException::class,
            InvalidKeyException::class,
            NoSessionException::class
        )
        fun decryptMessage(
            messageFrame: MessageFrame?,
            signalProtocolAddress: SignalProtocolAddress
        ): String? {
            Log.d(TAG, "Decrypting signal message.....")
            return instance.decrypt(messageFrame, signalProtocolAddress)
        }

        @JvmStatic
        fun processPreKeyResponseMessage(
            messageFrame: MessageFrame?,
            signalProtocolAddress: SignalProtocolAddress
        ): Boolean {
            Log.d(TAG, "Processing pre key response signal message.....")
            return instance.processPreKeyResponse(messageFrame, signalProtocolAddress)
        }

        @JvmStatic
        val preKeyResponseMessage: MessageFrame?
            get() {
                Log.d(TAG, "Creating pre key response message.....")
                return instance.createPreKeyResponseMessage()
            }

        @JvmStatic
        fun getMessageType(messageFrame: MessageFrame?): MessageType? {
            Log.d(TAG, "Getting message type.....")
            if (messageFrame == null) return null

            if (messageFrame.preKeyResponse != null && messageFrame.ciphertextMessage != null) {
                Log.d(TAG, "UPDATED_PRE_KEY_MESSAGE_WITH_CONTENT detected.....")
                return MessageType.UPDATED_PRE_KEY_RESPONSE_MESSAGE_AND_SIGNAL_MESSAGE
            } else if (messageFrame.preKeyResponse != null) {
                Log.d(TAG, "PRE_KEY_RESPONSE_MESSAGE detected.....")
                return MessageType.PRE_KEY_RESPONSE_MESSAGE
            } else if (messageFrame.ciphertextMessage != null) {
                Log.d(TAG, "SIGNAL_MESSAGE detected.....")
                // hint: PreKeySignalMessage or SignalMessage (ciphertextType is only set here!)
                instance.logMessageType(messageFrame.ciphertextType)
                return MessageType.SIGNAL_MESSAGE
            }
            return null
        }

        @JvmStatic
        fun extractContactFromMessageFrame(messageFrame: MessageFrame): Any? {
            Log.d(TAG, "Extracting contact from message frame.....")
            return instance.extractContactFromFrame(messageFrame)
        }

        @JvmStatic
        @Throws(ContactDuplicatedException::class, ContactIncorrectException::class)
        fun addContact(
            firstName: CharSequence?,
            lastName: CharSequence,
            signalProtocolAddressName: String?,
            deviceId: Int
        ): Contact {
            Log.d(TAG, "Creating and adding contact to contact list.....")
            return instance.createAndAddContactToList(
                firstName,
                lastName,
                signalProtocolAddressName,
                deviceId
            )
        }

        @JvmStatic
        val contactList: ArrayList<Contact>?
            get() {
                Log.d(TAG, "Getting contact list.....")
                return instance.contactListFromAccount
            }

        @JvmStatic
        fun removeContactFromContactListAndProtocol(contact: Contact) {
            Log.d(TAG, "Removing contact from contact list and protocol.....")
            instance.removeContact(contact)
        }

        @JvmStatic
        fun getFingerprint(contact: Contact?): Fingerprint? {
            Log.d(TAG, "Generating fingerprint.....")
            return instance.createFingerprint(contact)
        }

        @JvmStatic
        @Throws(ContactUnknownException::class)
        fun verifyContact(contact: Contact?) {
            Log.d(TAG, "Verifying contact.....")
            instance.verifyContactInContactList(contact)
        }

        @JvmStatic
        @Throws(ContactUnknownException::class)
        fun getUnencryptedMessagesList(contact: Contact?): List<MessageStorage>? {
            Log.d(TAG, "Getting unencrypted messages list.....")
            return instance.getUnencryptedMessagesListFromAccount(contact)
        }

        @JvmStatic
        val nameOfAccount: String
            get() {
                Log.d(TAG, "Getting account name (uuid).....")
                return instance.accountName
            }
    }
}

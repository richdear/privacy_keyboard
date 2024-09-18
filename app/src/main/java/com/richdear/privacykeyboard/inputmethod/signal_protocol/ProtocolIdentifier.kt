package com.richdear.privacykeyboard.inputmethod.signal_protocol

import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.PreKeyMetadataStoreFullImplementation
import com.richdear.privacykeyboard.inputmethod.signal_protocol.stores.SignalProtocolStoreFullImplementation
import org.signal.libsignal.protocol.SignalProtocolAddress

enum class ProtocolIdentifier(val className: Class<*>) {
    UNIQUE_USER_ID(String::class.java),
    METADATA_STORE(PreKeyMetadataStoreFullImplementation::class.java),
    PROTOCOL_STORE(SignalProtocolStoreFullImplementation::class.java),
    PROTOCOL_ADDRESS(SignalProtocolAddress::class.java),
    DEVICE_ID(Int::class.java),
    UNENCRYPTED_MESSAGES(ArrayList::class.java),
    CONTACTS(ArrayList::class.java)
}

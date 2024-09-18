package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores


abstract class PreKeyMetadataAbstractStore {
    open var nextSignedPreKeyId: Int = 0
    open var activeSignedPreKeyId: Int = 0
    open var isSignedPreKeyRegistered: Boolean = false
    open var signedPreKeyFailureCount: Int = 0

    open var nextOneTimePreKeyId: Int = 0

    open var nextSignedPreKeyRefreshTime: Long = 0L
    open var oldSignedPreKeyDeletionTime: Long = 0L
}

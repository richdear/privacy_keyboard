package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

class PreKeyMetadataStoreFullImplementation : PreKeyMetadataAbstractStore() {
    override var nextSignedPreKeyId: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override var activeSignedPreKeyId: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override var isSignedPreKeyRegistered: Boolean = false
        get() = field
        set(value) {
            field = value
        }

    override var signedPreKeyFailureCount: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override var nextOneTimePreKeyId: Int = 0
        get() = field
        set(value) {
            field = value
        }

    override var nextSignedPreKeyRefreshTime: Long = 0L
        get() = field
        set(value) {
            field = value
        }

    override var oldSignedPreKeyDeletionTime: Long = 0L
        get() = field
        set(value) {
            field = value
        }
}


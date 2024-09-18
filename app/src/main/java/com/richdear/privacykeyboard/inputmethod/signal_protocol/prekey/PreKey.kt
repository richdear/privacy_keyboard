package com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey

import com.fasterxml.jackson.annotation.JsonProperty

open class PreKey {
    @JsonProperty
    var keyId: Long = 0

    @JsonProperty
    var publicKey: String? = null

    constructor()

    constructor(keyId: Long, publicKey: String?) {
        this.keyId = keyId
        this.publicKey = publicKey
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` == null || `object` !is PreKey) return false
        val that = `object`

        return if (publicKey == null) {
            keyId == that.keyId && that.publicKey == null
        } else {
            (keyId == that.keyId) && this.publicKey == that.publicKey
        }
    }

    override fun hashCode(): Int {
        return if (publicKey == null) {
            keyId.toInt()
        } else {
            keyId.toInt() xor publicKey.hashCode()
        }
    }
}

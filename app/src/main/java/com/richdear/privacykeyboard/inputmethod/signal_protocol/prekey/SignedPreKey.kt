package com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey

import com.fasterxml.jackson.annotation.JsonProperty

class SignedPreKey : PreKey {
    @JsonProperty
    var signature: String? = null
        private set

    constructor()

    constructor(keyId: Long, publicKey: String?, signature: String?) : super(keyId, publicKey) {
        this.signature = signature
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` == null || `object` !is SignedPreKey) return false
        val that = `object`

        return if (signature == null) {
            super.equals(`object`) && that.signature == null
        } else {
            super.equals(`object`) && signature == that.signature
        }
    }

    override fun hashCode(): Int {
        return if (signature == null) {
            super.hashCode()
        } else {
            super.hashCode() xor signature.hashCode()
        }
    }
}


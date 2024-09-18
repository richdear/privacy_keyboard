package com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding

import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.Base64
import java.io.IOException
import java.nio.charset.StandardCharsets

object Base64Encoder {
    fun encode(message: String): String {
        val minifiedJson = EncodingHelper.minifyJSON(message)
        return Base64.encodeBytes(minifiedJson.toByteArray(StandardCharsets.UTF_8))
    }

    @Throws(IOException::class)
    fun decode(encodedText: String?): String {
        val decodedWithSimplifiedKeys = String(Base64.decode(encodedText)!!)
        return EncodingHelper.deSimplifyJsonKeys(decodedWithSimplifiedKeys)
    }
}

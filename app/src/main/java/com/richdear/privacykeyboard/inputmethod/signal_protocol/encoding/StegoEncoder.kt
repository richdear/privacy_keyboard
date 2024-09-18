package com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding

import android.content.Context
import android.util.Log
import com.richdear.privacykeyboard.R
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.compressString
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.convertBinaryToByteArray
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.convertBinaryToInvisibleString
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.convertByteArrayToBinary
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.convertInvisibleStringToBinary
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.deSimplifyJsonKeys
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.decompressString
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper.minifyJSON
import java.io.IOException
import java.util.Random
import java.util.regex.Pattern

object StegoEncoder {
    val TAG: String = StegoEncoder::class.java.simpleName

    val mSentencesMap: MutableMap<Int, String> = HashMap()

    private fun init(context: Context) {
        extractSentencesAndPutInMap(
            mSentencesMap,
            context.resources.getString(R.string.e2ee_stego_human_origin)
        )
        extractSentencesAndPutInMap(
            mSentencesMap,
            context.resources.getString(R.string.e2ee_stego_computer_science_history)
        )
    }

    // test only
    fun initForTest(stego1: String, stego2: String) {
        extractSentencesAndPutInMap(mSentencesMap, stego1)
        extractSentencesAndPutInMap(mSentencesMap, stego2)
    }

    private fun extractSentencesAndPutInMap(sentencesMap: MutableMap<Int, String>, text: String) {
        val regex = "([^.]\\w*[,\\s]*[^.]*)"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(text)

        var i = 0
        while (matcher.find()) {
            val sentence = text.substring(matcher.start(), matcher.end())
                .replace("\n".toRegex(), " ")
                .replace("\\s+".toRegex(), " ")
                .trim { it <= ' ' }
            sentencesMap[i] = sentence
            i++
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun encode(message: String?, context: Context?): String? {
        if (message == null) return null

        // for testing use initForTest method before calling this method
        if (mSentencesMap.size == 0 && context != null) init(context)

        Log.d(TAG, "message: $message")
        Log.d(TAG, "length message (bytes): " + message.toByteArray().size)

        val minifiedJson = minifyJSON(message)
        Log.d(TAG, "minifiedJson message: $minifiedJson")

        val compressedMessage = compressString(minifiedJson)
        val decoySentence = mSentencesMap[Random().nextInt(mSentencesMap.size)]
        val binaryMessage = convertByteArrayToBinary(compressedMessage)

        Log.d(TAG, "binary message: $binaryMessage")
        Log.d(TAG, "binary message (bytes): " + binaryMessage.toByteArray().size)

        val invisibleMessage = convertBinaryToInvisibleString(binaryMessage)
        Log.d(TAG, "length invisible message: " + invisibleMessage.length)

        return decoySentence + invisibleMessage
    }

    @JvmStatic
    @Throws(IOException::class)
    fun decode(encodedText: String?): String? {
        if (encodedText == null) return null
        val binary = convertInvisibleStringToBinary(encodedText)
        Log.d(TAG, "binary message: $binary")
        Log.d(TAG, "length invisible message: " + binary.length)
        val compressedResult = convertBinaryToByteArray(binary)
        val decompressedResult = decompressString(compressedResult)
        return deSimplifyJsonKeys(decompressedResult)
    }
}

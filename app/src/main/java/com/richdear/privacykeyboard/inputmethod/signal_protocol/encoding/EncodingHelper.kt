package com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding

import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.Inflater
import java.util.zip.InflaterOutputStream

object EncodingHelper {
    private val TAG: String = EncodingHelper::class.java.simpleName
    var simplifiedJSONMap: HashMap<String, String> = initSimplifiedJsonMap()

    @JvmStatic
    fun convertByteArrayToBinary(input: ByteArray): String {
        val result = StringBuilder()
        for (b in input) {
            var `val` = b.toInt()
            for (i in 0..7) {
                result.append(if ((`val` and 128) == 0) 0 else 1) // 128 = 1000 0000
                `val` = `val` shl 1
            }
        }
        return result.toString()
    }

    @JvmStatic
    fun convertBinaryToByteArray(binary: String?): ByteArray {
        return BigInteger(binary, 2).toByteArray()
    }

    @JvmStatic
    fun convertInvisibleStringToBinary(encodedMessage: String): String {
        val result = StringBuilder()
        val resultUnicode = StringBuilder()
        val regex = "\\p{C}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(encodedMessage)

        while (matcher.find()) {
            val s = encodedMessage.substring(matcher.start(), matcher.end())
            when (s) {
                "\u200C" -> {
                    result.append("0000")
                    resultUnicode.append("\\u200C")
                }

                "\u200D" -> {
                    result.append("0001")
                    resultUnicode.append("\\u200D")
                }

                "\u2060" -> {
                    result.append("0010")
                    resultUnicode.append("\\u2060")
                }

                "\u2062" -> {
                    result.append("0011")
                    resultUnicode.append("\\u2062")
                }

                "\u200B" -> {
                    result.append("0100")
                    resultUnicode.append("\\u200B")
                }

                "\u200E" -> {
                    result.append("0101")
                    resultUnicode.append("\\u200E")
                }

                "\u200F" -> {
                    result.append("0110")
                    resultUnicode.append("\\u200F")
                }

                "\u2064" -> {
                    result.append("0111")
                    resultUnicode.append("\\u2064")
                }

                "\u206A" -> {
                    result.append("1000")
                    resultUnicode.append("\\u206A")
                }

                "\u206B" -> {
                    result.append("1001")
                    resultUnicode.append("\\u206B")
                }

                "\u206C" -> {
                    result.append("1010")
                    resultUnicode.append("\\u206C")
                }

                "\u206D" -> {
                    result.append("1011")
                    resultUnicode.append("\\u206D")
                }

                "\u206E" -> {
                    result.append("1100")
                    resultUnicode.append("\\u206E")
                }

                "\u206F" -> {
                    result.append("1101")
                    resultUnicode.append("\\u206F")
                }

                "\uFEFF" -> {
                    result.append("1110")
                    resultUnicode.append("\\uFEFF")
                }

                "\u061C" -> {
                    result.append("1111")
                    resultUnicode.append("\\u061C")
                }
            }
        }
        Log.d(TAG, resultUnicode.toString())
        return result.toString()
    }

    @JvmStatic
    fun convertBinaryToInvisibleString(binaryString: String): String {
        val result = StringBuilder()
        val resultUnicode = StringBuilder()

        var i = 0
        while (i < binaryString.length) {
            val startInclusive = i
            val endExclusive = i + 4

            if (endExclusive >= binaryString.length + 1) {
                i += 4
                continue
            }
            val binaryDigits = binaryString.substring(startInclusive, endExclusive)

            when (binaryDigits) {
                "0000" -> {
                    result.append("\u200C")
                    resultUnicode.append("\\u200C")
                }

                "0001" -> {
                    result.append("\u200D")
                    resultUnicode.append("\\u200D")
                }

                "0010" -> {
                    result.append("\u2060")
                    resultUnicode.append("\\u2060")
                }

                "0011" -> {
                    result.append("\u2062")
                    resultUnicode.append("\\u2062")
                }

                "0100" -> {
                    result.append("\u200B")
                    resultUnicode.append("\\u200B")
                }

                "0101" -> {
                    result.append("\u200E")
                    resultUnicode.append("\\u200E")
                }

                "0110" -> {
                    result.append("\u200F")
                    resultUnicode.append("\\u200F")
                }

                "0111" -> {
                    result.append("\u2064")
                    resultUnicode.append("\\u2064")
                }

                "1000" -> {
                    result.append("\u206A")
                    resultUnicode.append("\\u206A")
                }

                "1001" -> {
                    result.append("\u206B")
                    resultUnicode.append("\\u206B")
                }

                "1010" -> {
                    result.append("\u206C")
                    resultUnicode.append("\\u206C")
                }

                "1011" -> {
                    result.append("\u206D")
                    resultUnicode.append("\\u206D")
                }

                "1100" -> {
                    result.append("\u206E")
                    resultUnicode.append("\\u206E")
                }

                "1101" -> {
                    result.append("\u206F")
                    resultUnicode.append("\\u206F")
                }

                "1110" -> {
                    result.append("\uFEFF")
                    resultUnicode.append("\\uFEFF")
                }

                "1111" -> {
                    result.append("\u061C")
                    resultUnicode.append("\\u061C")
                }
            }
            i += 4
        }

        Log.d(TAG, resultUnicode.toString())
        return result.toString()
    }

    @JvmStatic
    @Throws(IOException::class)
    fun encodedTextContainsInvisibleCharacters(encodedText: String?): Boolean {
        if (encodedText == null || encodedText.isEmpty()) throw IOException("There is no message to check")
        val regex = "\\p{C}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(encodedText)
        return matcher.find()
    }

    @JvmStatic
    fun minifyJSON(json: String): String {
        val minifiedJSON = json.replace(" ".toRegex(), "")
            .replace("\n".toRegex(), "")
        return simplifyJsonKeys(minifiedJSON)
    }

    @JvmStatic
    @Throws(IOException::class)
    fun compressString(message: String): ByteArray {
        val stream = ByteArrayOutputStream()
        val compresser = Deflater(Deflater.BEST_COMPRESSION, true)
        val deflaterOutputStream = DeflaterOutputStream(stream, compresser)
        deflaterOutputStream.write(message.toByteArray(StandardCharsets.UTF_8))
        deflaterOutputStream.close()
        return stream.toByteArray()
    }

    @JvmStatic
    @Throws(IOException::class)
    fun decompressString(compressedMessage: ByteArray?): String {
        val stream2 = ByteArrayOutputStream()
        val decompresser = Inflater(true)
        val inflaterOutputStream = InflaterOutputStream(stream2, decompresser)
        inflaterOutputStream.write(compressedMessage)
        inflaterOutputStream.close()
        return stream2.toString()
    }

    private fun simplifyJsonKeys(json: String): String {
        return json
            .replace(
                "\"preKeyResponse\"".toRegex(),
                "\"" + simplifiedJSONMap["preKeyResponse"] + "\""
            )
            .replace("\"identityKey\"".toRegex(), "\"" + simplifiedJSONMap["identityKey"] + "\"")
            .replace("\"publicKey\"".toRegex(), "\"" + simplifiedJSONMap["publicKey"] + "\"")
            .replace("\"devices\"".toRegex(), "\"" + simplifiedJSONMap["devices"] + "\"")
            .replace("\"deviceId\"".toRegex(), "\"" + simplifiedJSONMap["deviceId"] + "\"")
            .replace(
                "\"registrationId\"".toRegex(),
                "\"" + simplifiedJSONMap["registrationId"] + "\""
            )
            .replace("\"signedPreKey\"".toRegex(), "\"" + simplifiedJSONMap["signedPreKey"] + "\"")
            .replace("\"keyId\"".toRegex(), "\"" + simplifiedJSONMap["keyId"] + "\"")
            .replace("\"signature\"".toRegex(), "\"" + simplifiedJSONMap["signature"] + "\"")
            .replace("\"preKey\"".toRegex(), "\"" + simplifiedJSONMap["preKey"] + "\"")
            .replace(
                "\"ciphertextMessage\"".toRegex(),
                "\"" + simplifiedJSONMap["ciphertextMessage"] + "\""
            )
            .replace(
                "\"ciphertextType\"".toRegex(),
                "\"" + simplifiedJSONMap["ciphertextType"] + "\""
            )
            .replace("\"timestamp\"".toRegex(), "\"" + simplifiedJSONMap["timestamp"] + "\"")
            .replace(
                "\"signalProtocolAddressName\"".toRegex(),
                "\"" + simplifiedJSONMap["signalProtocolAddressName"] + "\""
            )
    }

    private fun initSimplifiedJsonMap(): HashMap<String, String> {
        val simplifiedJSONMap = HashMap<String, String>()
        simplifiedJSONMap["preKeyResponse"] = "pR"
        simplifiedJSONMap["identityKey"] = "i"
        simplifiedJSONMap["publicKey"] = "pK"
        simplifiedJSONMap["devices"] = "d"
        simplifiedJSONMap["deviceId"] = "dI"
        simplifiedJSONMap["registrationId"] = "rI"
        simplifiedJSONMap["signedPreKey"] = "sK"
        simplifiedJSONMap["keyId"] = "k"
        simplifiedJSONMap["signature"] = "s"
        simplifiedJSONMap["preKey"] = "prK"
        simplifiedJSONMap["ciphertextMessage"] = "c"
        simplifiedJSONMap["ciphertextType"] = "cT"
        simplifiedJSONMap["timestamp"] = "t"
        simplifiedJSONMap["signalProtocolAddressName"] = "a"
        return simplifiedJSONMap
    }

    @JvmStatic
    fun deSimplifyJsonKeys(simplifiedJSON: String): String {
        return simplifiedJSON
            .replace("\"pR\"".toRegex(), "\"" + getMapKeyFromValue("pR") + "\"")
            .replace("\"i\"".toRegex(), "\"" + getMapKeyFromValue("i") + "\"")
            .replace("\"pK\"".toRegex(), "\"" + getMapKeyFromValue("pK") + "\"")
            .replace("\"d\"".toRegex(), "\"" + getMapKeyFromValue("d") + "\"")
            .replace("\"dI\"".toRegex(), "\"" + getMapKeyFromValue("dI") + "\"")
            .replace("\"iK\"".toRegex(), "\"" + getMapKeyFromValue("iK") + "\"")
            .replace("\"rI\"".toRegex(), "\"" + getMapKeyFromValue("rI") + "\"")
            .replace("\"k\"".toRegex(), "\"" + getMapKeyFromValue("k") + "\"")
            .replace("\"s\"".toRegex(), "\"" + getMapKeyFromValue("s") + "\"")
            .replace("\"sK\"".toRegex(), "\"" + getMapKeyFromValue("sK") + "\"")
            .replace("\"c\"".toRegex(), "\"" + getMapKeyFromValue("c") + "\"")
            .replace("\"cT\"".toRegex(), "\"" + getMapKeyFromValue("cT") + "\"")
            .replace("\"t\"".toRegex(), "\"" + getMapKeyFromValue("t") + "\"")
            .replace("\"prK\"".toRegex(), "\"" + getMapKeyFromValue("prK") + "\"")
            .replace("\"a\"".toRegex(), "\"" + getMapKeyFromValue("a") + "\"")
    }

    private fun getMapKeyFromValue(value: String): String? {
        var key: String? = null
        for ((key1, value1) in simplifiedJSONMap) {
            if (value == value1) {
                key = key1
            }
        }
        return key
    }
}

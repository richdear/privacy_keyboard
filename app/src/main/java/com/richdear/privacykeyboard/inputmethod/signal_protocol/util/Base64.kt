package com.richdear.privacykeyboard.inputmethod.signal_protocol.util

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FilterInputStream
import java.io.FilterOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.ObjectStreamClass
import java.io.OutputStream
import java.io.Serializable
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import kotlin.math.max
import kotlin.math.min


object Base64 {


    const val NO_OPTIONS: Int = 0


    const val ENCODE: Int = 1



    const val DECODE: Int = 0



    const val GZIP: Int = 2


    const val DONT_GUNZIP: Int = 4



    const val DO_BREAK_LINES: Int = 8


    const val URL_SAFE: Int = 16



    const val ORDERED: Int = 32




    private const val MAX_LINE_LENGTH = 76



    private const val EQUALS_SIGN = '='.code.toByte()



    private const val NEW_LINE = '\n'.code.toByte()



    private const val PREFERRED_ENCODING = "US-ASCII"


    private const val WHITE_SPACE_ENC: Byte = -5 // Indicates white space in encoding
    private const val EQUALS_SIGN_ENC: Byte = -1 // Indicates equals sign in encoding





    private val _STANDARD_ALPHABET = byteArrayOf(
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        '+'.code.toByte(),
        '/'.code.toByte()
    )



    private val _STANDARD_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        62,  // Plus sign at decimal 43
        -9, -9, -9,  // Decimal 44 - 46
        63,  // Slash at decimal 47
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,  // Letters 'A' through 'N'
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  // Letters 'O' through 'Z'
        -9, -9, -9, -9, -9, -9,  // Decimal 91 - 96
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,  // Letters 'a' through 'm'
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,  // Letters 'n' through 'z'
        -9, -9, -9, -9 // Decimal 123 - 126

    )




    private val _URL_SAFE_ALPHABET = byteArrayOf(
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        '-'.code.toByte(),
        '_'.code.toByte()
    )


    private val _URL_SAFE_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        -9,  // Plus sign at decimal 43
        -9,  // Decimal 44
        62,  // Minus sign at decimal 45
        -9,  // Decimal 46
        -9,  // Slash at decimal 47
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,  // Letters 'A' through 'N'
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,  // Letters 'O' through 'Z'
        -9, -9, -9, -9,  // Decimal 91 - 94
        63,  // Underscore at decimal 95
        -9,  // Decimal 96
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,  // Letters 'a' through 'm'
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,  // Letters 'n' through 'z'
        -9, -9, -9, -9 // Decimal 123 - 126

    )




    private val _ORDERED_ALPHABET = byteArrayOf(
        '-'.code.toByte(),
        '0'.code.toByte(),
        '1'.code.toByte(),
        '2'.code.toByte(),
        '3'.code.toByte(),
        '4'.code.toByte(),
        '5'.code.toByte(),
        '6'.code.toByte(),
        '7'.code.toByte(),
        '8'.code.toByte(),
        '9'.code.toByte(),
        'A'.code.toByte(),
        'B'.code.toByte(),
        'C'.code.toByte(),
        'D'.code.toByte(),
        'E'.code.toByte(),
        'F'.code.toByte(),
        'G'.code.toByte(),
        'H'.code.toByte(),
        'I'.code.toByte(),
        'J'.code.toByte(),
        'K'.code.toByte(),
        'L'.code.toByte(),
        'M'.code.toByte(),
        'N'.code.toByte(),
        'O'.code.toByte(),
        'P'.code.toByte(),
        'Q'.code.toByte(),
        'R'.code.toByte(),
        'S'.code.toByte(),
        'T'.code.toByte(),
        'U'.code.toByte(),
        'V'.code.toByte(),
        'W'.code.toByte(),
        'X'.code.toByte(),
        'Y'.code.toByte(),
        'Z'.code.toByte(),
        '_'.code.toByte(),
        'a'.code.toByte(),
        'b'.code.toByte(),
        'c'.code.toByte(),
        'd'.code.toByte(),
        'e'.code.toByte(),
        'f'.code.toByte(),
        'g'.code.toByte(),
        'h'.code.toByte(),
        'i'.code.toByte(),
        'j'.code.toByte(),
        'k'.code.toByte(),
        'l'.code.toByte(),
        'm'.code.toByte(),
        'n'.code.toByte(),
        'o'.code.toByte(),
        'p'.code.toByte(),
        'q'.code.toByte(),
        'r'.code.toByte(),
        's'.code.toByte(),
        't'.code.toByte(),
        'u'.code.toByte(),
        'v'.code.toByte(),
        'w'.code.toByte(),
        'x'.code.toByte(),
        'y'.code.toByte(),
        'z'.code.toByte()
    )


    private val _ORDERED_DECODABET = byteArrayOf(
        -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal  0 -  8
        -5, -5,  // Whitespace: Tab and Linefeed
        -9, -9,  // Decimal 11 - 12
        -5,  // Whitespace: Carriage Return
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 14 - 26
        -9, -9, -9, -9, -9,  // Decimal 27 - 31
        -5,  // Whitespace: Space
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,  // Decimal 33 - 42
        -9,  // Plus sign at decimal 43
        -9,  // Decimal 44
        0,  // Minus sign at decimal 45
        -9,  // Decimal 46
        -9,  // Slash at decimal 47
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10,  // Numbers zero through nine
        -9, -9, -9,  // Decimal 58 - 60
        -1,  // Equals sign at decimal 61
        -9, -9, -9,  // Decimal 62 - 64
        11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,  // Letters 'A' through 'M'
        24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,  // Letters 'N' through 'Z'
        -9, -9, -9, -9,  // Decimal 91 - 94
        37,  // Underscore at decimal 95
        -9,  // Decimal 96
        38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,  // Letters 'a' through 'm'
        51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63,  // Letters 'n' through 'z'
        -9, -9, -9, -9 // Decimal 123 - 126

    )




    private fun getAlphabet(options: Int): ByteArray {
        return if ((options and URL_SAFE) == URL_SAFE) {
            _URL_SAFE_ALPHABET
        } else if ((options and ORDERED) == ORDERED) {
            _ORDERED_ALPHABET
        } else {
            _STANDARD_ALPHABET
        }
    } // end getAlphabet



    private fun getDecodabet(options: Int): ByteArray {
        return if ((options and URL_SAFE) == URL_SAFE) {
            _URL_SAFE_DECODABET
        } else if ((options and ORDERED) == ORDERED) {
            _ORDERED_DECODABET
        } else {
            _STANDARD_DECODABET
        }
    } // end getAlphabet


    fun getEncodedLengthWithoutPadding(unencodedLength: Int): Int {
        val remainderBytes = unencodedLength % 3
        var paddingBytes = 0

        if (remainderBytes != 0) paddingBytes = 3 - remainderBytes

        return (((unencodedLength + 2) / 3) * 4) - paddingBytes
    }

    fun getEncodedBytesForTarget(targetSize: Int): Int {
        return (targetSize * 3) / 4
    }




    private fun encode3to4(
        b4: ByteArray,
        threeBytes: ByteArray?,
        numSigBytes: Int,
        options: Int
    ): ByteArray {
        encode3to4(threeBytes, 0, numSigBytes, b4, 0, options)
        return b4
    } // end encode3to4



    private fun encode3to4(
        source: ByteArray?, srcOffset: Int, numSigBytes: Int,
        destination: ByteArray, destOffset: Int, options: Int
    ): ByteArray {
        val ALPHABET = getAlphabet(options)

        //           1         2         3
        // 01234567890123456789012345678901 Bit position
        // --------000000001111111122222222 Array position from threeBytes
        // --------|    ||    ||    ||    | Six bit groups to index ALPHABET
        //          >>18  >>12  >> 6  >> 0  Right shift necessary
        //                0x3f  0x3f  0x3f  Additional AND

        // Create buffer with zero-padding if there are only one or two
        // significant bytes passed in the array.
        // We have to shift left 24 in order to flush out the 1's that appear
        // when Java treats a value as negative that is cast from a byte to an int.
        val inBuff = ((if (numSigBytes > 0) ((source!![srcOffset].toInt() shl 24) ushr 8) else 0)
                or (if (numSigBytes > 1) ((source!![srcOffset + 1].toInt() shl 24) ushr 16) else 0)
                or (if (numSigBytes > 2) ((source!![srcOffset + 2].toInt() shl 24) ushr 24) else 0))

        when (numSigBytes) {
            3 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = ALPHABET[inBuff ushr 6 and 0x3f]
                destination[destOffset + 3] = ALPHABET[inBuff and 0x3f]
                return destination
            }

            2 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = ALPHABET[inBuff ushr 6 and 0x3f]
                destination[destOffset + 3] = EQUALS_SIGN
                return destination
            }

            1 -> {
                destination[destOffset] = ALPHABET[inBuff ushr 18]
                destination[destOffset + 1] = ALPHABET[inBuff ushr 12 and 0x3f]
                destination[destOffset + 2] = EQUALS_SIGN
                destination[destOffset + 3] = EQUALS_SIGN
                return destination
            }

            else -> return destination
        }
    } // end encode3to4



    fun encode(raw: ByteBuffer, encoded: ByteBuffer) {
        val raw3 = ByteArray(3)
        val enc4 = ByteArray(4)

        while (raw.hasRemaining()) {
            val rem = min(3.0, raw.remaining().toDouble()).toInt()
            raw[raw3, 0, rem]
            encode3to4(enc4, raw3, rem, NO_OPTIONS)
            encoded.put(enc4)
        } // end input remaining
    }



    fun encode(raw: ByteBuffer, encoded: CharBuffer) {
        val raw3 = ByteArray(3)
        val enc4 = ByteArray(4)

        while (raw.hasRemaining()) {
            val rem = min(3.0, raw.remaining().toDouble()).toInt()
            raw[raw3, 0, rem]
            encode3to4(enc4, raw3, rem, NO_OPTIONS)
            for (i in 0..3) {
                encoded.put((enc4[i].toInt() and 0xFF).toChar())
            }
        } // end input remaining
    }




    @JvmOverloads
    @Throws(IOException::class)
    fun encodeObject(serializableObject: Serializable?, options: Int = NO_OPTIONS): String {
        if (serializableObject == null) {
            throw NullPointerException("Cannot serialize a null object.")
        } // end if: null


        // Streams
        var baos: ByteArrayOutputStream? = null
        var b64os: java.io.OutputStream? = null
        var gzos: GZIPOutputStream? = null
        var oos: ObjectOutputStream? = null


        try {
            // ObjectOutputStream -> (GZIP) -> Base64 -> ByteArrayOutputStream
            baos = ByteArrayOutputStream()
            b64os = OutputStream(baos, ENCODE or options)
            if ((options and GZIP) != 0) {
                // Gzip
                gzos = GZIPOutputStream(b64os)
                oos = ObjectOutputStream(gzos)
            } else {
                // Not gzipped
                oos = ObjectOutputStream(b64os)
            }
            oos.writeObject(serializableObject)
        } // end try
        catch (e: IOException) {
            // Catch it and then throw it immediately so that
            // the finally{} block is called for cleanup.
            throw e
        } // end catch
        finally {
            try {
                oos!!.close()
            } catch (e: Exception) {
            }
            try {
                gzos!!.close()
            } catch (e: Exception) {
            }
            try {
                b64os!!.close()
            } catch (e: Exception) {
            }
            try {
                baos!!.close()
            } catch (e: Exception) {
            }
        } // end finally


        // Return value according to relevant encoding.
        return try {
            baos!!.toString(PREFERRED_ENCODING)
        } // end try
        catch (uue: UnsupportedEncodingException) {
            // Fall back to some Java default
            baos.toString()
        } // end catch
    } // end encode
    // end encodeObject



    fun encodeBytes(source: ByteArray): String {
        // Since we're not going to have the GZIP encoding turned on,
        // we're not going to have an java.io.IOException thrown, so
        // we should not force the user to have to catch it.
        var encoded: String? = null
        try {
            encoded = encodeBytes(source, 0, source.size, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { ex.message!! }
        } // end catch

        checkNotNull(encoded)
        return encoded
    } // end encodeBytes

    @JvmOverloads
    fun encodeBytesWithoutPadding(
        source: ByteArray,
        offset: Int,
        length: Int
    ): String {
        var encoded: String? = null

        try {
            encoded = encodeBytes(source, offset, length, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { ex.message!! }
        }

        checkNotNull(encoded)

        return if (encoded[encoded.length - 2] == '=') encoded.substring(0, encoded.length - 2)
        else if (encoded[encoded.length - 1] == '=') encoded.substring(0, encoded.length - 1)
        else encoded
    }

    @JvmStatic
    fun encodeBytesWithoutPadding(source: ByteArray): String {
        return encodeBytesWithoutPadding(source, 0, source.size)
    }


    @Throws(IOException::class)
    fun encodeBytes(source: ByteArray, options: Int): String {
        return encodeBytes(source, 0, source.size, options)
    } // end encodeBytes



    fun encodeBytes(source: ByteArray?, off: Int, len: Int): String {
        // Since we're not going to have the GZIP encoding turned on,
        // we're not going to have an java.io.IOException thrown, so
        // we should not force the user to have to catch it.
        var encoded: String? = null
        try {
            encoded = encodeBytes(source, off, len, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { ex.message!! }
        } // end catch

        checkNotNull(encoded)
        return encoded
    } // end encodeBytes



    @Throws(IOException::class)
    fun encodeBytes(source: ByteArray?, off: Int, len: Int, options: Int): String {
        val encoded = encodeBytesToBytes(source, off, len, options)

        // Return value according to relevant encoding.
        return try {
            String(encoded, charset(PREFERRED_ENCODING))
        } // end try
        catch (uue: UnsupportedEncodingException) {
            String(encoded)
        } // end catch
    } // end encodeBytes



    fun encodeBytesToBytes(source: ByteArray): ByteArray? {
        var encoded: ByteArray? = null
        try {
            encoded = encodeBytesToBytes(source, 0, source.size, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { "IOExceptions only come from GZipping, which is turned off: " + ex.message }
        }
        return encoded
    }



    @Throws(IOException::class)
    fun encodeBytesToBytes(source: ByteArray?, off: Int, len: Int, options: Int): ByteArray {
        if (source == null) {
            throw NullPointerException("Cannot serialize a null array.")
        } // end if: null


        require(off >= 0) { "Cannot have negative offset: $off" } // end if: off < 0


        require(len >= 0) { "Cannot have length offset: $len" } // end if: len < 0


        require(off + len <= source.size) {
            String.format(
                "Cannot have offset of %d and length of %d with array of length %d",
                off,
                len,
                source.size
            )
        } // end if: off < 0


        // Compress?
        if ((options and GZIP) != 0) {
            var baos: ByteArrayOutputStream? = null
            var gzos: GZIPOutputStream? = null
            var b64os: OutputStream? = null

            try {
                // GZip -> Base64 -> ByteArray
                baos = ByteArrayOutputStream()
                b64os = OutputStream(baos, ENCODE or options)
                gzos = GZIPOutputStream(b64os)

                gzos.write(source, off, len)
                gzos.close()
            } // end try
            catch (e: IOException) {
                // Catch it and then throw it immediately so that
                // the finally{} block is called for cleanup.
                throw e
            } // end catch
            finally {
                try {
                    gzos!!.close()
                } catch (e: Exception) {
                }
                try {
                    b64os!!.close()
                } catch (e: Exception) {
                }
                try {
                    baos!!.close()
                } catch (e: Exception) {
                }
            } // end finally


            return baos!!.toByteArray()
        } // end if: compress

        else {
            val breakLines = (options and DO_BREAK_LINES) > 0

            //int    len43   = len * 4 / 3;
            //byte[] outBuff = new byte[   ( len43 )                      // Main 4:3
            //                           + ( (len % 3) > 0 ? 4 : 0 )      // Account for padding
            //                           + (breakLines ? ( len43 / MAX_LINE_LENGTH ) : 0) ]; // New lines
            // Try to determine more precisely how big the array needs to be.
            // If we get it right, we don't have to do an array copy, and
            // we save a bunch of memory.
            var encLen =
                (len / 3) * 4 + (if (len % 3 > 0) 4 else 0) // Bytes needed for actual encoding
            if (breakLines) {
                encLen += encLen / MAX_LINE_LENGTH // Plus extra newline characters
            }
            val outBuff = ByteArray(encLen)


            var d = 0
            var e = 0
            val len2 = len - 2
            var lineLength = 0
            while (d < len2) {
                encode3to4(source, d + off, 3, outBuff, e, options)

                lineLength += 4
                if (breakLines && lineLength >= MAX_LINE_LENGTH) {
                    outBuff[e + 4] = NEW_LINE
                    e++
                    lineLength = 0
                } // end if: end of line

                d += 3
                e += 4
            }

            if (d < len) {
                encode3to4(source, d + off, len - d, outBuff, e, options)
                e += 4
            } // end if: some padding needed


            // Only resize array if we didn't guess it right.
            if (e < outBuff.size - 1) {
                val finalOut = ByteArray(e)
                System.arraycopy(outBuff, 0, finalOut, 0, e)
                //System.err.println("Having to resize array from " + outBuff.length + " to " + e );
                return finalOut
            } else {
                //System.err.println("No need to resize array.");
                return outBuff
            }
        } // end else: don't compress
    } // end encodeBytesToBytes




    private fun decode4to3(
        source: ByteArray?, srcOffset: Int,
        destination: ByteArray?, destOffset: Int, options: Int
    ): Int {
        // Lots of error checking and exception throwing

        if (source == null) {
            throw NullPointerException("Source array was null.")
        } // end if

        if (destination == null) {
            throw NullPointerException("Destination array was null.")
        } // end if

        require(!(srcOffset < 0 || srcOffset + 3 >= source.size)) {
            String.format(
                "Source array with length %d cannot have offset of %d and still process four bytes.",
                source.size,
                srcOffset
            )
        } // end if

        require(!(destOffset < 0 || destOffset + 2 >= destination.size)) {
            String.format(
                "Destination array with length %d cannot have offset of %d and still store three bytes.",
                destination.size,
                destOffset
            )
        } // end if


        val DECODABET = getDecodabet(options)

        // Example: Dk==
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            // Two ways to do the same thing. Don't know which way I like best.
            //int outBuff =   ( ( DECODABET[ source[ srcOffset    ] ] << 24 ) >>>  6 )
            //              | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
            val outBuff = (((DECODABET[source[srcOffset].toInt()].toInt() and 0xFF) shl 18)
                    or ((DECODABET[source[srcOffset + 1].toInt()].toInt() and 0xFF) shl 12))

            destination[destOffset] = (outBuff ushr 16).toByte()
            return 1
        } else if (source[srcOffset + 3] == EQUALS_SIGN) {
            // Two ways to do the same thing. Don't know which way I like best.
            //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
            //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
            //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
            val outBuff = (((DECODABET[source[srcOffset].toInt()].toInt() and 0xFF) shl 18)
                    or ((DECODABET[source[srcOffset + 1].toInt()].toInt() and 0xFF) shl 12)
                    or ((DECODABET[source[srcOffset + 2].toInt()].toInt() and 0xFF) shl 6))

            destination[destOffset] = (outBuff ushr 16).toByte()
            destination[destOffset + 1] = (outBuff ushr 8).toByte()
            return 2
        } else {
            // Two ways to do the same thing. Don't know which way I like best.
            //int outBuff =   ( ( DECODABET[ source[ srcOffset     ] ] << 24 ) >>>  6 )
            //              | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
            //              | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
            //              | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
            val outBuff = (((DECODABET[source[srcOffset].toInt()].toInt() and 0xFF) shl 18)
                    or ((DECODABET[source[srcOffset + 1].toInt()].toInt() and 0xFF) shl 12)
                    or ((DECODABET[source[srcOffset + 2].toInt()].toInt() and 0xFF) shl 6)
                    or ((DECODABET[source[srcOffset + 3].toInt()].toInt() and 0xFF)))


            destination[destOffset] = (outBuff shr 16).toByte()
            destination[destOffset + 1] = (outBuff shr 8).toByte()
            destination[destOffset + 2] = outBuff.toByte()

            return 3
        }
    } // end decodeToBytes



    fun decode(source: ByteArray): ByteArray? {
        var decoded: ByteArray? = null
        try {
            decoded = decode(source, 0, source.size, NO_OPTIONS)
        } catch (ex: IOException) {
            assert(false) { "IOExceptions only come from GZipping, which is turned off: " + ex.message }
        }
        return decoded
    }



    @Throws(IOException::class)
    fun decode(source: ByteArray?, off: Int, len: Int, options: Int): ByteArray {
        // Lots of error checking and exception throwing

        if (source == null) {
            throw NullPointerException("Cannot decode null source array.")
        } // end if

        require(!(off < 0 || off + len > source.size)) {
            String.format(
                "Source array with length %d cannot have offset of %d and process %d bytes.",
                source.size,
                off,
                len
            )
        } // end if


        if (len == 0) {
            return ByteArray(0)
        } else require(len >= 4) { "Base64-encoded string must have at least four characters, but length specified was $len" } // end if


        val DECODABET = getDecodabet(options)

        val len34 = len * 3 / 4 // Estimate on array size
        val outBuff = ByteArray(len34) // Upper limit on size of output
        var outBuffPosn = 0 // Keep track of where we're writing

        val b4 = ByteArray(4) // Four byte buffer from source, eliminating white space
        var b4Posn = 0 // Keep track of four byte input buffer
        var i = 0 // Source array counter
        var sbiCrop: Byte = 0 // Low seven bits (ASCII) of input
        var sbiDecode: Byte = 0 // Special value from DECODABET

        i = off
        while (i < off + len) {
            // Loop through source
            sbiCrop = (source[i].toInt() and 0x7f).toByte() // Only the low seven bits
            sbiDecode = DECODABET[sbiCrop.toInt()] // Special value

            // White space, Equals sign, or legit Base64 character
            // Note the values such as -5 and -9 in the
            // DECODABETs at the top of the file.
            if (sbiDecode >= WHITE_SPACE_ENC) {
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    b4[b4Posn++] = sbiCrop // Save non-whitespace
                    if (b4Posn > 3) {                  // Time to decode?
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, options)
                        b4Posn = 0

                        // If that was the equals sign, break out of 'for' loop
                        if (sbiCrop == EQUALS_SIGN) {
                            break
                        } // end if: equals sign
                    } // end if: quartet built
                } // end if: equals sign or better
            } // end if: white space, equals sign or better
            else {
                // There's a bad input character in the Base64 stream.
                throw IOException(
                    String.format(
                        "Bad Base64 input character '%c' in array position %d", source[i], i
                    )
                )
            } // end else:

            i++
        }

        val out = ByteArray(outBuffPosn)
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn)
        return out
    } // end decode


    @JvmStatic
    @Throws(IOException::class)
    fun decodeWithoutPadding(source: String): ByteArray? {
        var source = source
        val padding = source.length % 4

        if (padding == 1) source = "$source="
        else if (padding == 2) source = "$source=="
        else if (padding == 3) source = "$source="

        return decode(source)
    }




    @JvmOverloads
    @Throws(IOException::class)
    fun decode(s: String?, options: Int = DONT_GUNZIP): ByteArray? {
        if (s == null) {
            throw NullPointerException("Input string was null.")
        } // end if


        var bytes: ByteArray
        bytes = try {
            s.toByteArray(charset(PREFERRED_ENCODING))
        } // end try
        catch (uee: UnsupportedEncodingException) {
            s.toByteArray()
        } // end catch


        //</change>

        // Decode
        bytes = decode(bytes, 0, bytes.size, options)

        // Check to see if it's gzip-compressed
        // GZIP Magic Two-Byte Number: 0x8b1f (35615)
        val dontGunzip = (options and DONT_GUNZIP) != 0
        if ((bytes != null) && (bytes.size >= 4) && (!dontGunzip)) {
            val head = (bytes[0].toInt() and 0xff) or ((bytes[1].toInt() shl 8) and 0xff00)
            if (GZIPInputStream.GZIP_MAGIC == head) {
                var bais: ByteArrayInputStream? = null
                var gzis: GZIPInputStream? = null
                var baos: ByteArrayOutputStream? = null
                val buffer = ByteArray(2048)
                var length = 0

                try {
                    baos = ByteArrayOutputStream()
                    bais = ByteArrayInputStream(bytes)
                    gzis = GZIPInputStream(bais)

                    while ((gzis.read(buffer).also { length = it }) >= 0) {
                        baos.write(buffer, 0, length)
                    } // end while: reading input


                    // No error? Get new bytes.
                    bytes = baos.toByteArray()
                } // end try
                catch (e: IOException) {
                    e.printStackTrace()
                    // Just return originally-decoded bytes
                } // end catch
                finally {
                    try {
                        baos!!.close()
                    } catch (e: Exception) {
                    }
                    try {
                        gzis!!.close()
                    } catch (e: Exception) {
                    }
                    try {
                        bais!!.close()
                    } catch (e: Exception) {
                    }
                } // end finally
            } // end if: gzipped
        } // end if: bytes.length >= 2


        return bytes
    } // end decode




    @JvmOverloads
    @Throws(IOException::class, ClassNotFoundException::class)
    fun decodeToObject(
        encodedObject: String?, options: Int = NO_OPTIONS, loader: ClassLoader? = null
    ): Any? {
        // Decode and gunzip if necessary

        val objBytes = decode(encodedObject, options)

        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        var obj: Any? = null

        try {
            bais = ByteArrayInputStream(objBytes)

            // If no custom class loader is provided, use Java's builtin OIS.
            ois = if (loader == null) {
                ObjectInputStream(bais)
            } // end if: no loader provided

            else {
                object : ObjectInputStream(bais) {
                    @Throws(IOException::class, ClassNotFoundException::class)
                    public override fun resolveClass(streamClass: ObjectStreamClass): Class<*> {
                        val c = Class.forName(streamClass.name, false, loader)
                        return c // Class loader knows of this class.
                            ?: super.resolveClass(streamClass) // end else: not null
                    } // end resolveClass
                } // end ois
            } // end else: no custom class loader


            obj = ois.readObject()
        } // end try
        catch (e: IOException) {
            throw e // Catch and throw in order to execute finally{}
        } // end catch
        catch (e: ClassNotFoundException) {
            throw e // Catch and throw in order to execute finally{}
        } // end catch
        finally {
            try {
                bais!!.close()
            } catch (e: Exception) {
            }
            try {
                ois!!.close()
            } catch (e: Exception) {
            }
        } // end finally


        return obj
    } // end decodeObject



    @Throws(IOException::class)
    fun encodeToFile(dataToEncode: ByteArray?, filename: String?) {
        if (dataToEncode == null) {
            throw NullPointerException("Data to encode was null.")
        } // end iff


        var bos: OutputStream? = null
        try {
            bos = OutputStream(
                FileOutputStream(filename), ENCODE
            )
            bos.write(dataToEncode)
        } // end try
        catch (e: IOException) {
            throw e // Catch and throw to execute finally{} block
        } // end catch: java.io.IOException
        finally {
            try {
                bos!!.close()
            } catch (e: Exception) {
            }
        } // end finally
    } // end encodeToFile



    @Throws(IOException::class)
    fun decodeToFile(dataToDecode: String, filename: String?) {
        var bos: OutputStream? = null
        try {
            bos = OutputStream(
                FileOutputStream(filename), DECODE
            )
            bos.write(dataToDecode.toByteArray(charset(PREFERRED_ENCODING)))
        } // end try
        catch (e: IOException) {
            throw e // Catch and throw to execute finally{} block
        } // end catch: java.io.IOException
        finally {
            try {
                bos!!.close()
            } catch (e: Exception) {
            }
        } // end finally
    } // end decodeToFile



    @Throws(IOException::class)
    fun decodeFromFile(filename: String?): ByteArray? {
        var decodedData: ByteArray? = null
        var bis: InputStream? = null
        try {
            // Set up some useful variables
            val file = File(filename)
            var buffer: ByteArray? = null
            var length = 0
            var numBytes = 0

            // Check for size of file
            if (file.length() > Int.MAX_VALUE) {
                throw IOException("File is too big for this convenience method (" + file.length() + " bytes).")
            } // end if: file too big for int index

            buffer = ByteArray(file.length().toInt())

            // Open a stream
            bis = InputStream(
                BufferedInputStream(
                    FileInputStream(file)
                ), DECODE
            )

            // Read until done
            while ((bis.read(buffer, length, 4096).also { numBytes = it }) >= 0) {
                length += numBytes
            } // end while


            // Save in a variable to return
            decodedData = ByteArray(length)
            System.arraycopy(buffer, 0, decodedData, 0, length)
        } // end try
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        } // end catch: java.io.IOException
        finally {
            try {
                bis!!.close()
            } catch (e: Exception) {
            }
        } // end finally


        return decodedData
    } // end decodeFromFile



    @Throws(IOException::class)
    fun encodeFromFile(filename: String?): String? {
        var encodedData: String? = null
        var bis: InputStream? = null
        try {
            // Set up some useful variables
            val file = File(filename)
            val buffer = ByteArray(
                max((file.length() * 1.4).toInt().toDouble(), 40.0).toInt()
            ) // Need max() for math on small files (v2.2.1)
            var length = 0
            var numBytes = 0

            // Open a stream
            bis = InputStream(
                BufferedInputStream(
                    FileInputStream(file)
                ), ENCODE
            )

            // Read until done
            while ((bis.read(buffer, length, 4096).also { numBytes = it }) >= 0) {
                length += numBytes
            } // end while


            // Save in a variable to return
            encodedData = String(buffer, 0, length, charset(PREFERRED_ENCODING))
        } // end try
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        } // end catch: java.io.IOException
        finally {
            try {
                bis!!.close()
            } catch (e: Exception) {
            }
        } // end finally


        return encodedData
    } // end encodeFromFile


    @Throws(IOException::class)
    fun encodeFileToFile(infile: String?, outfile: String?) {
        val encoded = encodeFromFile(infile)
        var out: java.io.OutputStream? = null
        try {
            out = BufferedOutputStream(
                FileOutputStream(outfile)
            )
            out.write(encoded!!.toByteArray(StandardCharsets.US_ASCII)) // Strict, 7-bit output.
        } // end try
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        } // end catch
        finally {
            try {
                out!!.close()
            } catch (ex: Exception) {
            }
        } // end finally
    } // end encodeFileToFile



    @Throws(IOException::class)
    fun decodeFileToFile(infile: String?, outfile: String?) {
        val decoded = decodeFromFile(infile)
        var out: java.io.OutputStream? = null
        try {
            out = BufferedOutputStream(
                FileOutputStream(outfile)
            )
            out.write(decoded)
        } // end try
        catch (e: IOException) {
            throw e // Catch and release to execute finally{}
        } // end catch
        finally {
            try {
                out!!.close()
            } catch (ex: Exception) {
            }
        } // end finally
    } // end decodeFileToFile




    class InputStream @JvmOverloads constructor(
        `in`: java.io.InputStream?, // Record options used to create the stream.
        private val options: Int = DECODE
    ) : FilterInputStream(`in`) {
        private val encode: Boolean // Encoding or decoding
        private var position: Int // Current position in the buffer
        private val buffer: ByteArray // Small buffer holding converted data
        private val bufferLength: Int // Length of buffer (3 or 4)
        private var numSigBytes = 0 // Number of meaningful bytes in the buffer
        private var lineLength: Int
        private val breakLines: Boolean // Break lines at less than 80 characters
        private val decodabet: ByteArray // Local copies to avoid extra method calls




        init {
//            this.options = options // Record for later
            this.breakLines = (options and DO_BREAK_LINES) > 0
            this.encode = (options and ENCODE) > 0
            this.bufferLength = if (encode) 4 else 3
            this.buffer = ByteArray(bufferLength)
            this.position = -1
            this.lineLength = 0
            this.decodabet = getDecodabet(
                options
            )
        } // end constructor
        // end constructor



        @Throws(IOException::class)
        override fun read(): Int {
            // Do we need to get data?

            if (position < 0) {
                if (encode) {
                    val b3 = ByteArray(3)
                    var numBinaryBytes = 0
                    for (i in 0..2) {
                        val b = `in`.read()

                        // If end of stream, b is -1.
                        if (b >= 0) {
                            b3[i] = b.toByte()
                            numBinaryBytes++
                        } else {
                            break // out of for loop
                        } // end else: end of stream
                    } // end for: each needed input byte


                    if (numBinaryBytes > 0) {
                        encode3to4(b3, 0, numBinaryBytes, buffer, 0, options)
                        position = 0
                        numSigBytes = 4
                    } // end if: got data
                    else {
                        return -1 // Must be end of stream
                    } // end else
                } // end if: encoding

                else {
                    val b4 = ByteArray(4)
                    var i = 0
                    i = 0
                    while (i < 4) {
                        // Read four "meaningful" bytes:
                        var b = 0
                        do {
                            b = `in`.read()
                        } while (b >= 0 && decodabet[b and 0x7f] <= WHITE_SPACE_ENC)

                        if (b < 0) {
                            break // Reads a -1 if end of stream
                        } // end if: end of stream


                        b4[i] = b.toByte()
                        i++
                    }

                    if (i == 4) {
                        numSigBytes = decode4to3(b4, 0, buffer, 0, options)
                        position = 0
                    } // end if: got four characters
                    else if (i == 0) {
                        return -1
                    } // end else if: also padded correctly
                    else {
                        // Must have broken out from above.
                        throw IOException("Improperly padded Base64 input.")
                    } // end
                } // end else: decode
            } // end else: get data


            // Got data?
            if (position >= 0) {
                // End of relevant data?
                if ( position >= numSigBytes) {
                    return -1
                } // end if: got data


                if (encode && breakLines && lineLength >= MAX_LINE_LENGTH) {
                    lineLength = 0
                    return '\n'.code
                } // end if
                else {
                    lineLength++ // This isn't important when decoding

                    // but throwing an extra "if" seems
                    // just as wasteful.
                    val b = buffer[position++].toInt()

                    if (position >= bufferLength) {
                        position = -1
                    } // end if: end


                    return b and 0xFF // This is how you "cast" a byte that's
                    // intended to be unsigned.
                } // end else
            } // end if: position >= 0

            else {
                throw IOException("Error in Base64 code reading stream.")
            } // end else
        } // end read



        @Throws(IOException::class)
        override fun read(dest: ByteArray, off: Int, len: Int): Int {
            var b: Int
            var i = 0
            while (i < len) {
                b = read()

                if (b >= 0) {
                    dest[off + i] = b.toByte()
                } else if (i == 0) {
                    return -1
                } else {
                    break // Out of 'for' loop
                } // Out of 'for' loop

                i++
            }
            return i
        } // end read
    } // end inner class InputStream




    class OutputStream @JvmOverloads constructor(
        out: java.io.OutputStream?,
        options: Int = ENCODE
    ) : FilterOutputStream(out) {
        private val encode = options and ENCODE != 0
        private var position: Int
        private var buffer: ByteArray?
        private val bufferLength = if (encode) 3 else 4
        private var lineLength: Int
        private val breakLines = options and DO_BREAK_LINES != 0
        private val b4: ByteArray // Scratch used in a few places
        private var suspendEncoding: Boolean
        private val options: Int // Record for later
        private val decodabet: ByteArray // Local copies to avoid extra method calls



        init {
            this.buffer = ByteArray(bufferLength)
            this.position = 0
            this.lineLength = 0
            this.suspendEncoding = false
            this.b4 = ByteArray(4)
            this.options = options
            this.decodabet = getDecodabet(options)
        } // end constructor
        // end constructor



        @Throws(IOException::class)
        override fun write(theByte: Int) {
            // Encoding suspended?
            if (suspendEncoding) {
                out.write(theByte)
                return
            } // end if: supsended


            // Encode?
            if (encode) {
                buffer!![position++] = theByte.toByte()
                if (position >= bufferLength) { // Enough to encode.

                    out.write(encode3to4(b4, buffer, bufferLength, options))

                    lineLength += 4
                    if (breakLines && lineLength >= MAX_LINE_LENGTH) {
                        out.write(NEW_LINE.toInt())
                        lineLength = 0
                    } // end if: end of line


                    position = 0
                } // end if: enough to output
            } // end if: encoding

            else {
                // Meaningful Base64 character?
                if (decodabet[theByte and 0x7f] > WHITE_SPACE_ENC) {
                    buffer!![position++] = theByte.toByte()
                    if (position >= bufferLength) { // Enough to output.

                        val len = decode4to3(buffer, 0, b4, 0, options)
                        out.write(b4, 0, len)
                        position = 0
                    } // end if: enough to output
                } // end if: meaningful base64 character
                else if (decodabet[theByte and 0x7f] != WHITE_SPACE_ENC) {
                    throw IOException("Invalid character in Base64 data.")
                } // end else: not white space either
            } // end else: decoding
        } // end write



        @Throws(IOException::class)
        override fun write(theBytes: ByteArray, off: Int, len: Int) {
            // Encoding suspended?
            if (suspendEncoding) {
                out.write(theBytes, off, len)
                return
            } // end if: supsended


            for (i in 0 until len) {
                write(theBytes[off + i].toInt())
            } // end for: each byte written
        } // end write



        @Throws(IOException::class)
        fun flushBase64() {
            if (position > 0) {
                if (encode) {
                    out.write(encode3to4(b4, buffer, position, options))
                    position = 0
                } // end if: encoding
                else {
                    throw IOException("Base64 input not properly padded.")
                } // end else: decoding
            } // end if: buffer partially full
        } // end flush



        @Throws(IOException::class)
        override fun close() {
            // 1. Ensure that pending characters are written
            flushBase64()

            // 2. Actually close the stream
            // Base class both flushes and closes.
            super.close()

            buffer = null
            out = null
        } // end close



        @Throws(IOException::class)
        fun suspendEncoding() {
            flushBase64()
            this.suspendEncoding = true
        } // end suspendEncoding



        fun resumeEncoding() {
            this.suspendEncoding = false
        } // end resumeEncoding
    } // end inner class OutputStream
} // end class Base64

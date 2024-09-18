package com.richdear.privacykeyboard.inputmethod.crypto

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Deprecated("") // Basic AES encryption class for testing
object AESCrypt {
    private const val ALGORITHM = "AES"

    @Throws(Exception::class)
    fun encrypt(value: CharSequence, password: CharSequence): String {
        val key = generateKey(password)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedByteValue =
            cipher.doFinal(value.toString().toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    fun decrypt(value: CharSequence, password: CharSequence): String {
        val key = generateKey(password)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedValue64 = Base64.decode(value.toString(), Base64.DEFAULT)
        val decryptedByteValue = cipher.doFinal(decryptedValue64)
        return String(decryptedByteValue, StandardCharsets.UTF_8)
    }

    private fun generateKey(password: CharSequence): Key {
        val key: Key = SecretKeySpec(password.toString().toByteArray(), ALGORITHM)
        return key
    }
}
package cs.mad.week5lab.utils

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object EncryptionUtils {
    private const val ALGORITHM = "AES"
    private const val KEY = "MySuperSecretKey" // Should be stored securely

    private fun generateKey(): Key {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256) // 256-bit AES encryption
        return keyGenerator.generateKey()
    }

    private fun getKey(): Key {
        return SecretKeySpec(KEY.toByteArray(), ALGORITHM)
    }

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, getKey())
        val encryptedValue = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    fun decrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, getKey())
        val decodedValue = Base64.decode(data, Base64.DEFAULT)
        val decryptedValue = cipher.doFinal(decodedValue)
        return String(decryptedValue)
    }
}

    package cs.mad.week5lab.activities

    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import androidx.appcompat.app.AppCompatActivity
    import androidx.biometric.BiometricManager
    import androidx.biometric.BiometricPrompt
    import androidx.core.content.ContextCompat
    import cs.mad.week5lab.R
    import cs.mad.week5lab.databinding.ActivityMainBinding

    class MainActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if (checkBiometricSupport()) {
                showBiometricPrompt()
            } else {
                // Proceed without biometric authentication
                initializeApp()
            }

            binding.cardGeneratePhrase.setOnClickListener {
                startActivity(Intent(this, GeneratePhraseActivity::class.java))
            }


            binding.cardSavedPhrases.setOnClickListener {
                startActivity(Intent(this, SavedPhrasesActivity::class.java))
            }
            binding.cardAddPhrases.setOnClickListener {
                startActivity(Intent(this, AddPhraseActivity::class.java))
            }
        }

        private fun checkBiometricSupport(): Boolean {
            val biometricManager = BiometricManager.from(this)
            return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> false
                else -> false
            }
        }

        private fun showBiometricPrompt() {
            val executor = ContextCompat.getMainExecutor(this)
            val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("Biometric", "Authentication error: $errString")
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        finish() // Close the app if the user cancels the biometric prompt
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("Biometric", "Authentication succeeded!")
                    initializeApp()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.e("Biometric", "Authentication failed")
                }
            })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }

        private fun initializeApp() {
            // Initialize your app components here
        }
    }

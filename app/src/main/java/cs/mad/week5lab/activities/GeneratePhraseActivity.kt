package cs.mad.week5lab.activities

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cs.mad.week5lab.R
import java.util.*

class GeneratePhraseActivity : AppCompatActivity() {
    private lateinit var checkboxAlphabets: CheckBox
    private lateinit var checkboxAlphanumeric: CheckBox
    private lateinit var checkboxNumeric: CheckBox
    private lateinit var checkboxSpecialSymbols: CheckBox
    private lateinit var editTextPassphraseLength: EditText
    private lateinit var textViewGeneratedPhrase: TextView
    private lateinit var progressBarStrengthMeter: ProgressBar
    private lateinit var buttonGeneratePhrase: Button
    private lateinit var buttonCopy: Button
    private lateinit var buttonSave: Button
//    private lateinit var buttonOk: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_phrase)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        checkboxAlphabets = findViewById(R.id.checkbox_alphabets)
        checkboxAlphanumeric = findViewById(R.id.checkbox_alphanumeric)
        checkboxNumeric = findViewById(R.id.checkbox_numeric)
        checkboxSpecialSymbols = findViewById(R.id.checkbox_special_symbols)
        editTextPassphraseLength = findViewById(R.id.edittext_passphrase_length)
        textViewGeneratedPhrase = findViewById(R.id.textview_generated_phrase)
        progressBarStrengthMeter = findViewById(R.id.progressbar_strength_meter)
        buttonGeneratePhrase = findViewById(R.id.button_generate_phrase)
        buttonCopy = findViewById(R.id.button_copy)
        buttonSave = findViewById(R.id.button_save)
//        buttonOk = findViewById(R.id.button_ok)

        loadPreferences()

        buttonGeneratePhrase.setOnClickListener {
            val selectedCategories = mutableListOf<String>()
            if (checkboxAlphabets.isChecked) selectedCategories.add("Alphabets")
            if (checkboxAlphanumeric.isChecked) selectedCategories.add("Alphanumeric")
            if (checkboxNumeric.isChecked) selectedCategories.add("Numeric")
            if (checkboxSpecialSymbols.isChecked) selectedCategories.add("Special Symbols")

            val length = editTextPassphraseLength.text.toString().toIntOrNull() ?: 0
            val generatedPhrase = generatePhrase(selectedCategories, length)
            textViewGeneratedPhrase.text = generatedPhrase
            updateStrengthMeter(generatedPhrase)
        }

        buttonCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = textViewGeneratedPhrase.text
            Toast.makeText(this, "Phrase copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        buttonSave.setOnClickListener {
            val intent = Intent(this, SavePasswordActivity::class.java)
            intent.putExtra("generated_password", textViewGeneratedPhrase.text.toString())
            startActivity(intent)
        }


//        buttonOk.setOnClickListener {
//            savePreferences()
//            finish() // Close the activity after saving preferences
//        }
    }

    private fun generatePhrase(categories: List<String>, length: Int): String {
        val chars = StringBuilder()

        if ("Alphabets" in categories) chars.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
        if ("Alphanumeric" in categories) chars.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")
        if ("Numeric" in categories) chars.append("0123456789")
        if ("Special Symbols" in categories) chars.append("!@#$%^&*()-_=+[]{}|;:'\",.<>?/\\`~")

        val random = Random()
        val phrase = StringBuilder()
        for (i in 0 until length) {
            if (chars.isNotEmpty()) {
                phrase.append(chars[random.nextInt(chars.length)])
            }
        }
        return phrase.toString()
    }

    private fun updateStrengthMeter(phrase: String) {
        var strength = 0
        if (phrase.length >= 8) strength += 25
        if (phrase.length >= 12) strength += 25
        if (phrase.matches(Regex(".*[a-z].*"))) strength += 10
        if (phrase.matches(Regex(".*[A-Z].*"))) strength += 10
        if (phrase.matches(Regex(".*[0-9].*"))) strength += 10
        if (phrase.matches(Regex(".*[!@#\$%^&*()-_=+\\[\\]{}|;:'\",.<>?/\\\\`~].*"))) strength += 20

        progressBarStrengthMeter.progress = strength
    }

    private fun savePreferences() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("checkbox_alphabets", checkboxAlphabets.isChecked)
        editor.putBoolean("checkbox_alphanumeric", checkboxAlphanumeric.isChecked)
        editor.putBoolean("checkbox_numeric", checkboxNumeric.isChecked)
        editor.putBoolean("checkbox_special_symbols", checkboxSpecialSymbols.isChecked)
        editor.apply()
    }

    private fun loadPreferences() {
        checkboxAlphabets.isChecked = sharedPreferences.getBoolean("checkbox_alphabets", false)
        checkboxAlphanumeric.isChecked = sharedPreferences.getBoolean("checkbox_alphanumeric", false)
        checkboxNumeric.isChecked = sharedPreferences.getBoolean("checkbox_numeric", false)
        checkboxSpecialSymbols.isChecked = sharedPreferences.getBoolean("checkbox_special_symbols", false)
    }
}

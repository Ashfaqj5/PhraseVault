package cs.mad.week5lab.activities

import android.os.Bundle
import android.text.ClipboardManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cs.mad.week5lab.R
import java.util.*

class GeneratePhraseActivity : AppCompatActivity() {
    private lateinit var spinnerWordlistCategory: Spinner
    private lateinit var editTextPassphraseLength: EditText
    private lateinit var textViewGeneratedPhrase: TextView
    private lateinit var progressBarStrengthMeter: ProgressBar
    private lateinit var buttonGeneratePhrase: Button
    private lateinit var buttonCopy: Button
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_phrase)

        spinnerWordlistCategory = findViewById(R.id.spinner_wordlist_category)
        editTextPassphraseLength = findViewById(R.id.edittext_passphrase_length)
        textViewGeneratedPhrase = findViewById(R.id.textview_generated_phrase)
        progressBarStrengthMeter = findViewById(R.id.progressbar_strength_meter)
        buttonGeneratePhrase = findViewById(R.id.button_generate_phrase)
        buttonCopy = findViewById(R.id.button_copy)
        buttonSave = findViewById(R.id.button_save)

        // Set up the spinner with categories
        val categories = resources.getStringArray(R.array.wordlist_categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerWordlistCategory.adapter = adapter

        buttonGeneratePhrase.setOnClickListener {
            val category = spinnerWordlistCategory.selectedItem.toString()
            val length = editTextPassphraseLength.text.toString().toIntOrNull() ?: 0
            val generatedPhrase = generatePhrase(category, length)
            textViewGeneratedPhrase.text = generatedPhrase
            updateStrengthMeter(generatedPhrase)
        }

        buttonCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = textViewGeneratedPhrase.text
            Toast.makeText(this, "Phrase copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        buttonSave.setOnClickListener {
            // Save the generated phrase
        }
    }

    private fun generatePhrase(category: String, length: Int): String {
        val chars = when (category) {
            "Alphabets" -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            "Alphanumeric" -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
            "Numeric" -> "0123456789"
            "Special Symbols" -> "!@#$%^&*()-_=+[]{}|;:'\",.<>?/\\`~"
            else -> "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        }

        val random = Random()
        val phrase = StringBuilder()
        for (i in 0 until length) {
            phrase.append(chars[random.nextInt(chars.length)])
        }
        return phrase.toString()
    }

    private fun updateStrengthMeter(phrase: String) {
        // Simple strength calculation based on length and variety of characters
        var strength = 0
        if (phrase.length >= 8) strength += 25
        if (phrase.length >= 12) strength += 25
        if (phrase.matches(Regex(".*[a-z].*"))) strength += 10
        if (phrase.matches(Regex(".*[A-Z].*"))) strength += 10
        if (phrase.matches(Regex(".*[0-9].*"))) strength += 10
        if (phrase.matches(Regex(".*[!@#\$%^&*()-_=+\\[\\]{}|;:'\",.<>?/\\\\`~].*"))) strength += 20

        progressBarStrengthMeter.progress = strength
    }
}

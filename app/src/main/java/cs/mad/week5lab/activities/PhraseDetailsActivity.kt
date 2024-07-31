
package cs.mad.week5lab.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cs.mad.week5lab.R
import cs.mad.week5lab.database.AppDatabase
import cs.mad.week5lab.utils.EncryptionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhraseDetailsActivity : AppCompatActivity() {

    private lateinit var textViewName: TextView
    private lateinit var textViewPassword: TextView
    private lateinit var textViewUrl: TextView
    private lateinit var textViewExtra: TextView
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phrase_details)

        textViewName = findViewById(R.id.text_view_name)
        textViewPassword = findViewById(R.id.text_view_password)
        textViewUrl = findViewById(R.id.text_view_url)
        textViewExtra = findViewById(R.id.text_view_extra)

        appDatabase = AppDatabase.getInstance(this)

        // Get the phrase ID from the intent
        val phraseId = intent.getIntExtra("PHRASE_ID", -1)

        // Fetch the phrase details from the database
        if (phraseId != -1) {
            fetchPhraseDetails(phraseId)
        }
    }

    private fun fetchPhraseDetails(phraseId: Int) {
        println("phraseid--->$phraseId")
        lifecycleScope.launch {
            val phrase = withContext(Dispatchers.IO) {
                println("phraseId call start")
                appDatabase.passwordDao().getPasswordById(phraseId)

            }
            //print the phrase
            println("phrase1234$phrase")
            phrase?.let {
                textViewName.text = it.name
                textViewPassword.text = EncryptionUtils.decrypt(it.password)
                textViewUrl.text = it.url
                textViewExtra.text = it.extra
            }
        }
    }
}

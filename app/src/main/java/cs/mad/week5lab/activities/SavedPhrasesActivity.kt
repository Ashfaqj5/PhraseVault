package cs.mad.week5lab.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.R
import cs.mad.week5lab.database.AppDatabase
import cs.mad.week5lab.database.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedPhrasesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SavedPhrasesAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_phrases)

        recyclerView = findViewById(R.id.recycler_view_saved_phrases)
        recyclerView.layoutManager = LinearLayoutManager(this)

        database = AppDatabase.getInstance(this)

        // Load saved phrases using coroutines
        lifecycleScope.launch {
            val savedPhrases = loadSavedPhrases()
            if (savedPhrases.isNotEmpty()) {
                adapter = SavedPhrasesAdapter(savedPhrases) { phrase ->
                    val intent = Intent(this@SavedPhrasesActivity, PhraseDetailsActivity::class.java)
                    intent.putExtra("PHRASE_ID", phrase.id)
                    startActivity(intent)
                }
                recyclerView.adapter = adapter
            } else {
                // Log or handle the case where no phrases are found
                println("No saved phrases found.")
            }
        }
    }

    private suspend fun loadSavedPhrases(): List<PasswordEntity> {
        return withContext(Dispatchers.IO) {
            val phrases = database.passwordDao().getAllPasswords()
            println("Fetched ${phrases.size} phrases from database.")
            phrases
        }
    }
}

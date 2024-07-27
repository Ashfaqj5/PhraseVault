package cs.mad.week5lab.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cs.mad.week5lab.database.AppDatabase
import cs.mad.week5lab.databinding.ActivityStudySetBinding
import cs.mad.week5lab.entities.Flashcard
import kotlinx.coroutines.launch

class StudySetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudySetBinding
    private val flashcardDao by lazy {
        AppDatabase.getDatabase(this).flashcardDao()
    }
    private var flashcards = mutableListOf<Flashcard>()
    private val totalAmount get() = flashcards.size
    private var isShowingDefinition = false
    private var position = 0
        set(value) = if (value == flashcards.size) field = 0 else field = value
    private var missed = mutableListOf<Flashcard>()
    private var correct = 0
    private var completed = 0
    private val isComplete get() = flashcards.isEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudySetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val setId = intent.getIntExtra("SET_ID", 0)
        loadFlashcards(setId)

        binding.currentCard.setOnClickListener {
            isShowingDefinition = !isShowingDefinition
            updateCurrentCard()
        }
        updateCurrentCard()
        binding.completedCount.text = "0/$totalAmount"
    }

    private fun loadFlashcards(setId: Int) {
        lifecycleScope.launch {
            flashcards = flashcardDao.getFlashcardsForSet(setId).toMutableList()
            updateCurrentCard()
        }
    }

    private fun updateCurrentCard() {
        binding.currentCard.text = if (isComplete) "Set Complete!!!"
        else if (isShowingDefinition) flashcards[position].definition else flashcards[position].term
    }

    fun missCurrent(view: View) {
        if (isComplete) return

        if (!missed.contains(flashcards[position])) missed.add(flashcards[position])
        binding.missedCount.text = "Missed: ${missed.size}"
        skipCurrent(view)
    }

    fun skipCurrent(view: View) {
        if (isComplete) return

        position++

        isShowingDefinition = false
        updateCurrentCard()
    }

    fun correctCurrent(view: View) {
        if (isComplete) return

        completed++
        if (!missed.contains(flashcards[position])) correct++
        binding.completedCount.text = "$completed/$totalAmount"
        binding.correctCount.text = "Correct: $correct"
        flashcards.removeAt(position)
        if (position == flashcards.size) position = 0

        isShowingDefinition = false
        updateCurrentCard()
    }

    fun quitStudying(view: View) {
        finish()
    }
}
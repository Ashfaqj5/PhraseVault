//package cs.mad.week5lab.activities
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import cs.mad.week5lab.R
//import cs.mad.week5lab.adapters.FlashcardSetAdapter
//import cs.mad.week5lab.database.AppDatabase
//import cs.mad.week5lab.databinding.ActivityFlashcardSetBinding
//import cs.mad.week5lab.entities.FlashcardSet
//import kotlinx.coroutines.launch
//
//class FlashcardSetActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityFlashcardSetBinding
//    private val flashcardSetDao by lazy {
//        AppDatabase.getDatabase(this).flashcardSetDao()
//    }
//    private val flashcardSetAdapter by lazy { FlashcardSetAdapter(listOf()) }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityFlashcardSetBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.flashcardSetRecycler.adapter = flashcardSetAdapter
//        loadFlashcardSets()
//    }
//
//    private fun loadFlashcardSets() {
//        lifecycleScope.launch {
//            val sets = flashcardSetDao.getAllFlashcardSets()
//            flashcardSetAdapter.updateData(sets)
//        }
//    }
//
//    fun addSet(view: View) {
//        val newSet = FlashcardSet(title = "New Set " + (flashcardSetAdapter.itemCount + 1))
//        lifecycleScope.launch {
//            val id = flashcardSetDao.insertFlashcardSet(newSet)
//            val updatedSet = newSet.copy(id = id.toInt())
//            val currentData = flashcardSetAdapter.getData()
//            flashcardSetAdapter.updateData(currentData + updatedSet)
//            binding.flashcardSetRecycler.smoothScrollToPosition(flashcardSetAdapter.itemCount - 1)
//        }
//    }
//}

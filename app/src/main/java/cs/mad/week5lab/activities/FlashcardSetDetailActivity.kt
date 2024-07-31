//package cs.mad.week5lab.activities
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import kotlinx.coroutines.launch
//import cs.mad.week5lab.adapters.FlashcardAdapter
//import cs.mad.week5lab.database.AppDatabase
//import cs.mad.week5lab.databinding.ActivityFlashcardSetDetailBinding
//import cs.mad.week5lab.entities.Flashcard
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.RecyclerView
//
//class FlashcardSetDetailActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityFlashcardSetDetailBinding
//    private val flashcardDao by lazy {
//        AppDatabase.getDatabase(this).flashcardDao()
//    }
//    private lateinit var flashcardAdapter: FlashcardAdapter
//    private var setId: Int = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityFlashcardSetDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setId = intent.getIntExtra("SET_ID", 0)
//        flashcardAdapter = FlashcardAdapter(listOf(), this)
//        binding.flashcardRecycler.adapter = flashcardAdapter
//
//        loadFlashcards()
//
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
//            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
//                val flashcard = flashcardAdapter.getItem(h.adapterPosition)
//                lifecycleScope.launch {
//                    flashcardDao.deleteFlashcard(flashcard)
//                    loadFlashcards()
//                }
//            }
//        }).attachToRecyclerView(binding.flashcardRecycler)
//    }
//
//    private fun loadFlashcards() {
//        lifecycleScope.launch {
//            val flashcards = flashcardDao.getFlashcardsForSet(setId)
//            flashcardAdapter.updateData(flashcards)
//        }
//    }
//
//    fun addFlashcard(view: View) {
//        val numFlashcards = flashcardAdapter.itemCount
//        val newFlashcard = Flashcard(term = "New Term "+ (numFlashcards+1), definition = "New Definition "+(numFlashcards+1), flashcardSetId = setId)
//        lifecycleScope.launch {
//            flashcardDao.insertFlashcard(newFlashcard)
//            loadFlashcards()
//        }
//    }
//
//    fun startStudying(view: View) {
//        startActivity(Intent(this, StudySetActivity::class.java).apply {
//            putExtra("SET_ID", setId)
//        })
//    }
//}
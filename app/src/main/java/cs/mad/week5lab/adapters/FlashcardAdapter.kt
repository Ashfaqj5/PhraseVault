//package cs.mad.week5lab.adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.EditText
//import android.app.AlertDialog
//import android.view.ViewGroup
//import android.widget.TextView
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import androidx.recyclerview.widget.RecyclerView
//import cs.mad.week5lab.R
//import cs.mad.week5lab.database.AppDatabase
//import cs.mad.week5lab.entities.Flashcard
//
//
//class FlashcardAdapter(private val dataSet: List<Flashcard>, private val context: Context) : RecyclerView.Adapter<FlashcardAdapter.ViewHolder>() {
//    private val data = dataSet.toMutableList()
//    private val flashcardDao = AppDatabase.getDatabase(context).flashcardDao()
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = view.findViewById(R.id.term_text)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
//        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_item, parent, false)
//    )
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val flashcard = data[position]
//        holder.textView.text = flashcard.term
//
//        holder.itemView.setOnClickListener {
//            val alert = AlertDialog.Builder(it.context)
//                .setTitle(flashcard.term)
//                .setMessage(flashcard.definition)
//                .setPositiveButton("Edit") { dialog, _ ->
//                    showEditDialog(it.context, position)
//                    dialog.dismiss()
//                }
//                .create()
//            alert.show()
//        }
//    }
//
//    override fun getItemCount() = data.size
//
//    fun add(flashcard: Flashcard) {
//        data.add(flashcard)
//        notifyItemInserted(data.size - 1)
//    }
//
//    fun removeAt(index: Int) {
//        data.removeAt(index)
//        notifyDataSetChanged()
//    }
//
//    fun getItem(index: Int): Flashcard = data[index]
//
//    fun updateData(newData: List<Flashcard>) {
//        data.clear()
//        data.addAll(newData)
//        notifyDataSetChanged()
//    }
//
//    private fun showEditDialog(context: Context, position: Int) {
//        val flashcard = data[position]
//        val editTitle = EditText(context)
//        val editBody = EditText(context)
//        editTitle.setText(flashcard.term)
//        editBody.setText(flashcard.definition)
//
//        val alert = AlertDialog.Builder(context)
//            .setCustomTitle(editTitle)
//            .setView(editBody)
//            .setNegativeButton("Delete") { dialog, _ ->
//                removeAt(position)
//                CoroutineScope(Dispatchers.IO).launch {
//                    flashcardDao.deleteFlashcard(flashcard)
//                }
//                dialog.dismiss()
//            }
//            .setNeutralButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .setPositiveButton("Done") { dialog, _ ->
//                flashcard.term = editTitle.text.toString()
//                flashcard.definition = editBody.text.toString()
//                notifyItemChanged(position)
//                CoroutineScope(Dispatchers.IO).launch {
//                    flashcardDao.updateFlashcard(flashcard)
//                }
//                dialog.dismiss()
//            }
//            .create()
//        alert.show()
//    }
//}

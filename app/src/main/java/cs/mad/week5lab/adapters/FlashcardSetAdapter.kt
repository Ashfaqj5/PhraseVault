package cs.mad.week5lab.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.R
import cs.mad.week5lab.activities.FlashcardSetDetailActivity
import cs.mad.week5lab.entities.FlashcardSet

class FlashcardSetAdapter(dataSet: List<FlashcardSet>) : RecyclerView.Adapter<FlashcardSetAdapter.ViewHolder>() {
    private val data = dataSet.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.flashcard_set_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flashcardSet = data[position]
        holder.textView.text = flashcardSet.title
        holder.itemView.setOnClickListener {
            val context = it.context
            val intent = Intent(context, FlashcardSetDetailActivity::class.java).apply {
                putExtra("SET_ID", flashcardSet.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = data.size

    fun updateData(newData: List<FlashcardSet>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    fun getData(): List<FlashcardSet> {
        return data.toList()
    }
}

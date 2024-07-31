package cs.mad.week5lab.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.week5lab.R
import cs.mad.week5lab.database.PasswordEntity

class SavedPhrasesAdapter(
    private val savedPhrases: List<PasswordEntity>,
    private val onItemClick: (PasswordEntity) -> Unit
) : RecyclerView.Adapter<SavedPhrasesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.text_view_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_phrase, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phrase = savedPhrases[position]
        holder.textViewName.text = phrase.name
        holder.itemView.setOnClickListener {
            onItemClick(phrase)
        }
    }

    override fun getItemCount(): Int = savedPhrases.size
}

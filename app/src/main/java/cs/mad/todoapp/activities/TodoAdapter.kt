package cs.mad.todoapp.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cs.mad.todoapp.R

class TodoAdapter(
    private val items: MutableList<String>,
    private val onEdit: (Int) -> Unit,
    private val onComplete: (Int) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.itemTextView)
        val completeButton: Button = itemView.findViewById(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemTextView.text = items[position]
        holder.itemView.setOnClickListener {
            onEdit(position)
        }
        holder.completeButton.setOnClickListener {
            onComplete(position)
        }
    }

    override fun getItemCount(): Int = items.size
}

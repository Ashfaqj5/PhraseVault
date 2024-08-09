    package cs.mad.todoapp.activities

    import android.content.Context
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.widget.Button
    import android.widget.EditText
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import cs.mad.todoapp.R

    class MainActivity : AppCompatActivity() {

        private lateinit var todoAdapter: TodoAdapter
        private val sharedPreferences by lazy {
            getSharedPreferences("MADTodo", Context.MODE_PRIVATE)
        }
        private val todoList: MutableList<String> by lazy {
            loadTodoList()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)


            todoAdapter = TodoAdapter(todoList, this::editItem, this::completeItem)
            findViewById<RecyclerView>(R.id.todoRecyclerView).apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = todoAdapter
            }

            findViewById<Button>(R.id.addButton).setOnClickListener {
                addItemDialog()
            }
        }

        private fun loadTodoList(): MutableList<String> {
            val storedList = sharedPreferences.getStringSet("TODO_LIST", setOf())?.toMutableList() ?: mutableListOf()
            return storedList
        }

        private fun saveTodoList() {
            sharedPreferences.edit().putStringSet("TODO_LIST", todoList.toSet()).apply()
        }

        private fun addItemDialog() {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_item, null)
            val editText = dialogView.findViewById<EditText>(R.id.editTextItem)

            AlertDialog.Builder(this)
                .setTitle("Add Todo")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    val newItem = editText.text.toString()
                    if (newItem.isNotBlank()) {
                        todoList.add(newItem)
                        todoAdapter.notifyItemInserted(todoList.size - 1)
                        saveTodoList()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }

        private fun editItem(position: Int) {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_item, null)
            val editText = dialogView.findViewById<EditText>(R.id.editTextItem)
            editText.setText(todoList[position])

            AlertDialog.Builder(this)
                .setTitle("Edit Todo")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    val updatedItem = editText.text.toString()
                    if (updatedItem.isNotBlank()) {
                        todoList[position] = updatedItem
                        todoAdapter.notifyItemChanged(position)
                        saveTodoList()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }

        private fun completeItem(position: Int) {
//            todoList.removeAt(position)
//            todoAdapter.notifyItemRemoved(position)
//            saveTodoList()
            if (position >= 0 && position < todoList.size) {
                todoList.removeAt(position)
                todoAdapter.notifyItemRemoved(position)
                todoAdapter.notifyItemRangeChanged(position, todoList.size)
                saveTodoList()
            }
        }
    }

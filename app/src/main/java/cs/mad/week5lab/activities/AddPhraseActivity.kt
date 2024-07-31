package cs.mad.week5lab.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import cs.mad.week5lab.R
import cs.mad.week5lab.database.AppDatabase
import cs.mad.week5lab.database.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddPhraseActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUrl: EditText
    private lateinit var editTextExtra: EditText
    private lateinit var buttonSave: Button
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_phrase)

        editTextName = findViewById(R.id.edittext_name)
        editTextPassword = findViewById(R.id.edittext_password)
        editTextUrl = findViewById(R.id.edittext_url)
        editTextExtra = findViewById(R.id.edittext_extra)
        buttonSave = findViewById(R.id.button_save)

        appDatabase = AppDatabase.getInstance(this)

        buttonSave.setOnClickListener {
            savePasswordDetails()
        }
    }

    private fun savePasswordDetails() {
        val name = editTextName.text.toString()
        val password = editTextPassword.text.toString()
        val url = editTextUrl.text.toString()
        val extra = editTextExtra.text.toString()

        val passwordEntity = PasswordEntity(name = name, password = password, url = url, extra = extra)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase.passwordDao().insert(passwordEntity)
            }
            finish()
        }
    }
}

//package cs.mad.week5lab.activities
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import androidx.appcompat.app.AppCompatActivity
//import cs.mad.week5lab.R
//import cs.mad.week5lab.database.AppDatabase
//import cs.mad.week5lab.database.PasswordEntity
//
//class SavePasswordActivity : AppCompatActivity() {
//
//    private lateinit var editTextName: EditText
//    private lateinit var editTextPassword: EditText
//    private lateinit var editTextUrl: EditText
//    private lateinit var editTextExtra: EditText
//    private lateinit var buttonSave: Button
//    private lateinit var appDatabase: AppDatabase
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_save_password)
//
//        editTextName = findViewById(R.id.edittext_name)
//        editTextPassword = findViewById(R.id.edittext_password)
//        editTextUrl = findViewById(R.id.edittext_url)
//        editTextExtra = findViewById(R.id.edittext_extra)
//        buttonSave = findViewById(R.id.button_save)
//
//        appDatabase = AppDatabase.getInstance(this)
//
//        // Retrieve the generated password from the intent
//        val generatedPassword = intent.getStringExtra("generated_password")
//        editTextPassword.setText(generatedPassword)
//
//        buttonSave.setOnClickListener {
//            savePasswordDetails()
//        }
//    }
//
//    private fun savePasswordDetails() {
//        val name = editTextName.text.toString()
//        val password = editTextPassword.text.toString()
//        val url = editTextUrl.text.toString()
//        val extra = editTextExtra.text.toString()
//
//        // Create a new password entity
//        val passwordEntity = PasswordEntity(name = name, password = password, url = url, extra = extra)
//
//        // Save the password entity to the database
//        appDatabase.passwordDao().insert(passwordEntity)
//
//        // Navigate back to the home screen
//        finish()
//    }
//}
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

class SavePasswordActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUrl: EditText
    private lateinit var editTextExtra: EditText
    private lateinit var buttonSave: Button
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_password)

        editTextName = findViewById(R.id.edittext_name)
        editTextPassword = findViewById(R.id.edittext_password)
        editTextUrl = findViewById(R.id.edittext_url)
        editTextExtra = findViewById(R.id.edittext_extra)
        buttonSave = findViewById(R.id.button_save)

        appDatabase = AppDatabase.getInstance(this)

        // Retrieve the generated password from the intent
        val generatedPassword = intent.getStringExtra("generated_password")
        editTextPassword.setText(generatedPassword)

        buttonSave.setOnClickListener {
            savePasswordDetails()
        }
    }

    private fun savePasswordDetails() {
        val name = editTextName.text.toString()
        val password = editTextPassword.text.toString()
        val url = editTextUrl.text.toString()
        val extra = editTextExtra.text.toString()

        // Create a new password entity
        val passwordEntity = PasswordEntity(name = name, password = password, url = url, extra = extra)

        // Save the password entity to the database within a coroutine
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase.passwordDao().insert(passwordEntity)
            }
            // Navigate back to the home screen
            finish()
        }
    }
}

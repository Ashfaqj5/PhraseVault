package cs.mad.todoapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(passwordEntity: PasswordEntity)

    @Query("SELECT * FROM passwords")
    suspend fun getAllPasswords(): List<PasswordEntity>

    @Query("SELECT * FROM passwords WHERE id = :id")
    suspend fun getPasswordById(id: Int): PasswordEntity?
}

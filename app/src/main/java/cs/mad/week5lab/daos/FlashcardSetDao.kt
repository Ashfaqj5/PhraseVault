package cs.mad.week5lab.daos

import androidx.room.*
import cs.mad.week5lab.entities.FlashcardSet

@Dao
interface FlashcardSetDao {
    @Query("SELECT * FROM flashcard_sets")
    suspend fun getAllFlashcardSets(): List<FlashcardSet>

    @Insert
    suspend fun insertFlashcardSet(flashcardSet: FlashcardSet): Long

    @Update
    suspend fun updateFlashcardSet(flashcardSet: FlashcardSet)

    @Delete
    suspend fun deleteFlashcardSet(flashcardSet: FlashcardSet)
}

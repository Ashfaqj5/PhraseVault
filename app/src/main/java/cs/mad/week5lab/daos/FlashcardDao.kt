package cs.mad.week5lab.daos

import androidx.room.*
import cs.mad.week5lab.entities.Flashcard

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards WHERE flashcardSetId = :setId")
    suspend fun getFlashcardsForSet(setId: Int): List<Flashcard>

    @Insert
    suspend fun insertFlashcard(flashcard: Flashcard): Long

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)
}

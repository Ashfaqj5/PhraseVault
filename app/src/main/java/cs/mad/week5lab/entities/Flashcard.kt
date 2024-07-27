package cs.mad.week5lab.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcards")
data class Flashcard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var term: String,
    var definition: String,
    val flashcardSetId: Int
)

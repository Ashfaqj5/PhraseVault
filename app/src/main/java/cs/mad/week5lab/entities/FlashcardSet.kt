package cs.mad.week5lab.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_sets")
data class FlashcardSet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)

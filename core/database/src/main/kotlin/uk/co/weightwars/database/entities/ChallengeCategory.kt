package uk.co.weightwars.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChallengeCategory(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val title: String
)
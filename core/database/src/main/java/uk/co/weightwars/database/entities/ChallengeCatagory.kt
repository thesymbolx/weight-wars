package uk.co.weightwars.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_category")
data class ChallengeCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
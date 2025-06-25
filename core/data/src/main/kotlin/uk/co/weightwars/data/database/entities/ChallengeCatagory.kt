package uk.co.weightwars.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_category")
data class ChallengeCategory(
    @PrimaryKey val id: String,
    val title: String
)
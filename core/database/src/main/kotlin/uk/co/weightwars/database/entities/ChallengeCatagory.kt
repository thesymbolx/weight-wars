package uk.co.weightwars.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_challenges")
data class ChallengeCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
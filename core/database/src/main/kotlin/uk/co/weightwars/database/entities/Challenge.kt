package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


data class ChallengeWithCategory(
    @Embedded val challengeCategory: ChallengeCategory,
    @Relation(parentColumn = "categoryId", entityColumn = "categoryId")
    val challenges: List<Challenge>
)

@Entity
data class ChallengeCategory(
    @PrimaryKey(autoGenerate = true) val categoryId: Long = 0,
    val title: String
)

@Entity
data class Challenge (
    @PrimaryKey(autoGenerate = true) val challengeId: Long = 0,
    val title: String,
    val days: Int,
    val hasHardCoreMode: Boolean,
    val categoryId: Long = 0
)
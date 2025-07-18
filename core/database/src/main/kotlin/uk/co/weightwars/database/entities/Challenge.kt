package uk.co.weightwars.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class Challenge (
    @PrimaryKey(autoGenerate = true) val challengeId: Long = 0,
    val title: String,
    val days: Int,
    val hasHardCoreMode: Boolean
)
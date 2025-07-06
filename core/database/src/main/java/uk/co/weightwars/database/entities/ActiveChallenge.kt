package uk.co.weightwars.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_challenges")
data class ActiveChallenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val hasHardCoreMode: Boolean
)
package uk.co.weightwars.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class Challenge (
    @PrimaryKey val id: String,
    val title: String,
    val hasHardCoreMode: Boolean
)
package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class CurrentUser(
    @Embedded val profile: Profile,
    @Relation(parentColumn = "profileId", entityColumn = "profileParentId")
    val friends: List<Friend>
)

@Entity
data class Profile(
    @PrimaryKey val profileId: Long = 0,
    val name: String,
)

@Entity
data class Friend(
    @PrimaryKey val friendId: Long = 0,
    val name: String,
    val profileParentId: Long = -1
)
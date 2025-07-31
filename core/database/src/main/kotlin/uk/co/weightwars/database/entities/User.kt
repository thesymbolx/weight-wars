package uk.co.weightwars.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class UserWithFriend(
    @Embedded val user: User,
    @Relation(parentColumn = "userId", entityColumn = "friendId")
    val friends: List<Friend>
)

@Entity
data class User(
    @PrimaryKey val userId: Long = 0,
    val name: String,
)

@Entity
data class Friend(
    @PrimaryKey val friendId: Long = 0,
    val name: String,
    val userParentId: Long
)
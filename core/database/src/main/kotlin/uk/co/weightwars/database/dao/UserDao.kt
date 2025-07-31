package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.Friend
import uk.co.weightwars.database.entities.User
import uk.co.weightwars.database.entities.UserWithFriend

@Dao
interface UserDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(userWithFriend: UserWithFriend) {
        val userId = insertUser(userWithFriend.user)

        val friends = userWithFriend.friends.map {
            it.copy(
                userParentId = userId
            )
        }

        insertFriends(friends)
    }

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getCurrentUser() : UserWithFriend?

    @Query("SELECT * FROM user LIMIT 1")
    fun getCurrentUserFlow(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<Friend>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long
}
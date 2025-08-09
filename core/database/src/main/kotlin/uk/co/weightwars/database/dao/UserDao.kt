package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.database.entities.Friend
import uk.co.weightwars.database.entities.Profile

@Dao
interface UserDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(currentUser: CurrentUser) {
        insertProfile(currentUser.profile)

        val newFriends = currentUser.friends.map {
            it.copy(profileParentId = currentUser.profile.profileId)
        }

        val existingFriends = getFriendsByProfileParentId(currentUser.profile.profileId)
        val friendsToRemove = existingFriends.filterNot { existingFriend -> newFriends.any { it.friendId == existingFriend.friendId } }
        deleteFriends(friendsToRemove)

        insertFriends(newFriends)
    }

    @Query("SELECT * FROM profile LIMIT 1")
    suspend fun getCurrentUser() : CurrentUser?

    @Query("SELECT * FROM profile LIMIT 1")
    fun getCurrentUserAsFlow() : Flow<CurrentUser?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<Friend>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile): Long

    @Query("SELECT * FROM friend WHERE profileParentId = :profileParentId")
    suspend fun getFriendsByProfileParentId(profileParentId: String): List<Friend>

    @androidx.room.Delete
    suspend fun deleteFriends(friends: List<Friend>)
}
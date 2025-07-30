package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User) : Long

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getCurrentUser() : User?

    @Query("SELECT id FROM user LIMIT 1")
    fun getCurrentUserId(): Flow<Long>
}
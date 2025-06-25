package uk.co.weightwars.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.weightwars.data.database.entities.Challenge

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges")
    suspend fun getAll(): List<Challenge>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Challenge)

    @Delete
    suspend fun delete(user: Challenge)
}
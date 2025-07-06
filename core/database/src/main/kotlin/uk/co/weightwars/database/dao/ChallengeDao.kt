package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.weightwars.database.entities.Challenge

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges")
    suspend fun getAll(): List<Challenge>

    @Query("SELECT * FROM challenges WHERE id = :id") // Select all columns and filter by id
    suspend fun getChallenge(id: Int): Challenge

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Challenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favorite: List<Challenge>)

    @Delete
    suspend fun delete(user: Challenge)
}
package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.weightwars.database.entities.ActiveChallenge

@Dao
interface ActiveChallengeDao {
    @Query("SELECT * FROM active_challenges")
    suspend fun getAll(): List<ActiveChallenge>

    @Query("SELECT * FROM active_challenges WHERE id = :id")
    suspend fun getById(id: Int) : ActiveChallenge

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: ActiveChallenge)

    @Delete
    suspend fun delete(challenge: ActiveChallenge)
}
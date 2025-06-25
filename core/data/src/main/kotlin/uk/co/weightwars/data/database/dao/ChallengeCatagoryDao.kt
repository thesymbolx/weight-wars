package uk.co.weightwars.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.weightwars.data.database.entities.ChallengeCategory

@Dao
interface ChallengeCategoryDao {
    @Query("SELECT * FROM challenge_category")
    suspend fun getAll(): List<ChallengeCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: ChallengeCategory)

    @Delete
    suspend fun delete(user: ChallengeCategory)
}
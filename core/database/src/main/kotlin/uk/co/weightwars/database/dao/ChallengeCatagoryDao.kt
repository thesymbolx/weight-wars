package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.co.weightwars.database.entities.ChallengeCategory

@Dao
interface ChallengeCategoryDao {
    @Query("SELECT * FROM category_challenges")
    suspend fun getAll(): List<ChallengeCategory>

    @Query("SELECT * FROM category_challenges WHERE categoryId = :id")
    suspend fun getCategory(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: ChallengeCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favorite: List<ChallengeCategory>)

    @Delete
    suspend fun delete(user: ChallengeCategory)
}
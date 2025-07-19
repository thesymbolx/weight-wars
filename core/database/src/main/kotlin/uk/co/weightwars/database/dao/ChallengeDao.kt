package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeWithCategory

@Dao
interface ChallengeDao {
    @Transaction
    @Query("SELECT * FROM ChallengeCategory")
    suspend fun getAllCategories(): List<ChallengeWithCategory>

    @Transaction
    @Query("SELECT * FROM ChallengeCategory WHERE categoryId = :categoryId")
    suspend fun getCategory(categoryId: Long): ChallengeWithCategory

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challengeWithCategory: ChallengeWithCategory) {
        val challengeWithCategoryId = insert(challengeWithCategory.challengeCategory)

        val challenges = challengeWithCategory.challenges.map {
            it.copy(
                categoryId = challengeWithCategoryId
            )
        }

        insert(challenges)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: ChallengeCategory) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: List<Challenge>) : List<Long>

    @Query("SELECT * FROM Challenge WHERE challengeId = :challengeId")
    suspend fun getChallenge(challengeId: Long): Challenge
}
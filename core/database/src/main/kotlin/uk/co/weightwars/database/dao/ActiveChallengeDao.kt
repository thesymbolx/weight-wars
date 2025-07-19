package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.ActiveChallengeItem
import uk.co.weightwars.database.entities.ChallengeInfo

@Dao
interface ActiveChallengeDao {
    @Transaction
    @Query("SELECT * FROM ChallengeInfo")
    suspend fun getAll(): List<ActiveChallenge>

    @Transaction
    @Query("SELECT * FROM ChallengeInfo")
    fun getAllFlow(): Flow<List<ActiveChallenge>>

    @Transaction
    @Query("SELECT * FROM ChallengeInfo WHERE challengeInfoId = :challengeInfoId")
    fun getById(challengeInfoId: Long) : Flow<ActiveChallenge>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activeChallenge: ActiveChallenge) {
        val infoId = insert(activeChallenge.challengeInfo)

        val activeChallengeItems = activeChallenge.activeChallengeItems.map {
            it.copy(
                challengeInfoParentId = infoId
            )
        }

        insert(activeChallengeItems)
    }

    @Transaction
    @Delete
    suspend fun delete(challenge: ActiveChallenge) {
        delete(challenge.challengeInfo)
        delete(challenge.activeChallengeItems)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<ActiveChallengeItem>)

    @Delete
    suspend fun delete(items: List<ActiveChallengeItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: ChallengeInfo) : Long

    @Delete
    suspend fun delete(items: ChallengeInfo)
}
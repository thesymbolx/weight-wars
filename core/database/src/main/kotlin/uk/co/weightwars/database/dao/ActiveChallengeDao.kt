package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.database.entities.ActiveChallengeItemEntity
import uk.co.weightwars.database.entities.ChallengeInfoEntity

@Dao
interface ActiveChallengeDao {
    @Transaction
    @Query("SELECT * FROM ChallengeInfoEntity")
    suspend fun getAll(): List<ActiveChallengeEntity>

    @Transaction
    @Query("SELECT * FROM ChallengeInfoEntity")
    fun getAllFlow(): Flow<List<ActiveChallengeEntity>>

    @Transaction
    @Query("SELECT * FROM ChallengeInfoEntity WHERE challengeInfoId = :challengeInfoId")
    fun getById(challengeInfoId: Long) : Flow<ActiveChallengeEntity>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activeChallengeEntity: ActiveChallengeEntity) {
        val infoId = insert(activeChallengeEntity.challengeInfoEntity)

        val activeChallengeItems = activeChallengeEntity.activeChallengeItemEntities.map {
            it.copy(
                challengeInfoParentId = infoId
            )
        }

        insert(activeChallengeItems)
    }

    @Transaction
    @Delete
    suspend fun delete(challenge: ActiveChallengeEntity) {
        delete(challenge.challengeInfoEntity)
        delete(challenge.activeChallengeItemEntities)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<ActiveChallengeItemEntity>)

    @Delete
    suspend fun delete(items: List<ActiveChallengeItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: ChallengeInfoEntity) : Long

    @Delete
    suspend fun delete(items: ChallengeInfoEntity)
}
package uk.co.weightwars.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.database.entities.SubChallengeEntity
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
    fun getByIdFlow(challengeInfoId: String) : Flow<ActiveChallengeEntity>

    @Transaction
    @Query("SELECT * FROM ChallengeInfoEntity WHERE challengeInfoId = :challengeInfoId")
    fun getById(challengeInfoId: String) : ActiveChallengeEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activeChallengeEntity: ActiveChallengeEntity) {
        val infoId = insert(activeChallengeEntity.challengeInfoEntity)

        val activeChallengeItems = activeChallengeEntity.subChallenges.map {
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
        delete(challenge.subChallenges)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<SubChallengeEntity>)

    @Delete
    suspend fun delete(items: List<SubChallengeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: ChallengeInfoEntity) : Long

    @Delete
    suspend fun delete(items: ChallengeInfoEntity)
}
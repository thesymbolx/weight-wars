package uk.co.weightwars.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.data.models.toActiveChallenge
import uk.co.weightwars.data.models.toActiveChallengeEntity
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.network.datasource.ActiveChallengeDataSource
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val userDao: UserDao,
    private val activeChallengeDao: ActiveChallengeDao,
    private val activeChallengeDataSource: ActiveChallengeDataSource
) {
    fun getActiveChallenge(id: String): Flow<ActiveChallenge> {
        return activeChallengeDao.getByIdFlow(id)
            .map {
                it.toActiveChallenge()
            }
    }

    fun getActiveChallenges(): Flow<ActiveChallenge> = flow {
        val currentUserId: Long? = withContext(Dispatchers.IO) {
            val currentUserId = userDao.getCurrentUser()?.profile?.profileId
            if (currentUserId == null) {
                emitAll(emptyFlow())
            }
            currentUserId
        }

        if(currentUserId == null) return@flow

        emitAll(
            activeChallengeDataSource.getUserActiveChallenges("$currentUserId").flatMapLatest { userActiveChallengeIds ->
                if (userActiveChallengeIds.isEmpty()) {
                    emptyFlow()
                } else {
                    userActiveChallengeIds.asFlow().flatMapMerge { userActiveChallengeId ->
                        activeChallengeDataSource.getActiveChallenge(userActiveChallengeId)
                            .map { firebaseActiveChallenge ->
                                withContext(Dispatchers.IO) {
                                    val cachedActiveChallenge = activeChallengeDao.getById(firebaseActiveChallenge.id)

                                    if(cachedActiveChallenge != null) {
                                        cachedActiveChallenge.toActiveChallenge()
                                    } else {
                                        val activeChallengeEntity = firebaseActiveChallenge.toActiveChallengeEntity()
                                        activeChallengeDao.insert(activeChallengeEntity)
                                        activeChallengeEntity.toActiveChallenge()
                                    }
                                }
                            }
                    }
                }
            }
        )
    }

    suspend fun deleteActiveChallenge(challenge: ActiveChallengeEntity) =
        activeChallengeDao.delete(challenge)

    suspend fun createActiveChallenge(activeChallenge: ActiveChallenge) {
        val firebaseNodeKey = activeChallengeDataSource.createActiveChallenge(activeChallenge.toNetworkChallenge())
        activeChallengeDao.insert(activeChallenge.toActiveChallengeEntity(firebaseNodeKey))
    }

    suspend fun updateActiveChallenge(activeChallenge: ActiveChallenge) {
//        val firebaseNodeKey = activeChallengeDataSource.saveActiveChallenge(activeChallenge.toNetworkChallenge())
//
//        activeChallengeDao.insert(activeChallenge.toActiveChallengeEntity())
    }
}
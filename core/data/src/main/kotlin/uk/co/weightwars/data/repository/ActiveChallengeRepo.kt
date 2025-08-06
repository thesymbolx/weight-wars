package uk.co.weightwars.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.data.models.toActiveChallenge
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.network.datasource.ActiveChallengeDataSource
import uk.co.weightwars.network.model.FirebaseAction
import uk.co.weightwars.network.model.FirebaseActiveChallenge
import java.lang.Exception
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val userDao: UserDao,
    private val activeChallengeDao: ActiveChallengeDao,
    private val activeChallengeDataSource: ActiveChallengeDataSource
) {
    fun getActiveChallenge(id: Long): Flow<ActiveChallenge> {
        return activeChallengeDao.getById(id)
            .map {
                it.toActiveChallenge()
            }
    }

    suspend fun getActiveChallenges() : Flow<String> {
       val currentUserId = userDao.getCurrentUser()?.profile?.profileId ?: throw Exception("make work offline only")

        return activeChallengeDataSource.getUserActiveChallenges("$currentUserId").flatMapLatest { userActiveChallengeIds ->
            flow {
                userActiveChallengeIds.forEach { userActiveChallengeId ->
                    activeChallengeDataSource.getActiveChallenges(userActiveChallengeId).map { activeChallenge ->
                        when(activeChallenge) {
                            is FirebaseAction.Added -> emit(activeChallenge.data.toString())
                            is FirebaseAction.Modified -> emit(activeChallenge.data.toString())
                            is FirebaseAction.Removed<*> -> throw Exception()
                        }
                    }
                }
            }
        }
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
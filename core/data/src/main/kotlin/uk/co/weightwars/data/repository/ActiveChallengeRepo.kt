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
import uk.co.weightwars.data.models.Participant
import uk.co.weightwars.data.models.toActiveChallenge
import uk.co.weightwars.data.models.toNetworkChallenge
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.network.datasource.ActiveChallengeRemoteDataSource
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val userDao: UserDao,
    private val activeChallengeRemoteDataSource: ActiveChallengeRemoteDataSource
) {
    fun getActiveChallenge(id: String): Flow<ActiveChallenge> =
        activeChallengeRemoteDataSource.getActiveChallenge(id).map {
            it.toActiveChallenge()
        }

    fun getActiveChallenges(): Flow<ActiveChallenge> = flow {
        val currentUserId: String? = withContext(Dispatchers.IO) {
            val currentUserId = userDao.getCurrentUser()?.profile?.profileId
            if (currentUserId == null) {
                emitAll(emptyFlow())
            }
            currentUserId
        }

        if(currentUserId == null) return@flow

        emitAll(
            activeChallengeRemoteDataSource.getUserActiveChallenges(currentUserId).flatMapLatest { userActiveChallengeIds ->
                if (userActiveChallengeIds.isEmpty()) {
                    emptyFlow()
                } else {
                    userActiveChallengeIds.asFlow().flatMapMerge { userActiveChallengeId ->
                        activeChallengeRemoteDataSource.getActiveChallenge(userActiveChallengeId)
                            .map { firebaseActiveChallenge ->
                                firebaseActiveChallenge.toActiveChallenge()
                            }
                    }
                }
            }
        )
    }

    suspend fun createActiveChallenge(activeChallenge: ActiveChallenge, participantIds: List<String>) {
         activeChallengeRemoteDataSource.createActiveChallenge(activeChallenge.toNetworkChallenge(), participantIds)
    }
}
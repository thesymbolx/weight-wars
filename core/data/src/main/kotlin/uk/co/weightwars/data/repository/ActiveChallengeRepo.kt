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
       val currentUserId = userDao.getCurrentUser()?.profile?.profileId ?: return flow { emit("null") }

        //This flow returns activeChallengeIDs in a flow
        activeChallengeDataSource.getUserActiveChallenges("$currentUserId")

        //use the above ids here
        activeChallengeDataSource.getActiveChallenges(/*id here*/)
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
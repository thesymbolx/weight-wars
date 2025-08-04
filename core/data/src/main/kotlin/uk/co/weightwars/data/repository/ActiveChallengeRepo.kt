package uk.co.weightwars.data.repository

import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.network.datasource.ActiveChallengeDataSource
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao,
    private val activeChallengeDataSource: ActiveChallengeDataSource
) {
    fun getActiveChallenge(id: Long): ActiveChallenge {
        activeChallengeDao.getById(id)
    }


    fun getActiveChallenges() =
        activeChallengeDao.getAllFlow()

    suspend fun deleteActiveChallenge(challenge: ActiveChallengeEntity) =
        activeChallengeDao.delete(challenge)

    suspend fun updateActiveChallenge(activeChallenge: ActiveChallenge) {
        activeChallengeDao.insert(activeChallenge.toActiveChallengeEntity())
        activeChallengeDataSource.saveActiveChallenge(activeChallenge.toNetworkChallenge())
    }
}
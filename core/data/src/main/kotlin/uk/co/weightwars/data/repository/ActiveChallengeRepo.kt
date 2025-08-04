package uk.co.weightwars.data.repository

import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.network.datasource.UserRemoteDataSource
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    fun getActiveChallenge(id: Long) =
        activeChallengeDao.getById(id)

    fun getActiveChallenges() =
        activeChallengeDao.getAllFlow()

    suspend fun deleteActiveChallenge(challenge: ActiveChallengeEntity) =
        activeChallengeDao.delete(challenge)

    suspend fun updateActiveChallenge(challenge: ActiveChallengeEntity) {
        activeChallengeDao.insert(challenge)


    }
}
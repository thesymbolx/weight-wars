package uk.co.weightwars.data

import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.entities.ActiveChallenge
import javax.inject.Inject

class ActiveChallengeRepo @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao
) {
    fun getActiveChallenge(id: Long) =
        activeChallengeDao.getById(id)

    fun getActiveChallenges() =
        activeChallengeDao.getAllFlow()

    suspend fun deleteActiveChallenge(challenge: ActiveChallenge) =
        activeChallengeDao.delete(challenge)

    suspend fun updateActiveChallenge(challenge: ActiveChallenge) =
        activeChallengeDao.insert(challenge)
}
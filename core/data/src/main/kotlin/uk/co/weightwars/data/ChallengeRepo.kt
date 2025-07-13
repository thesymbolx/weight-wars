package uk.co.weightwars.data

import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.entities.ActiveChallenge
import javax.inject.Inject

class ChallengeRepo @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao
) {
    suspend fun getActiveChallenge(id: Int) =
        activeChallengeDao.getById(id)

    fun getActiveChallenges() =
        activeChallengeDao.getAllFlow()

    suspend fun deleteActiveChallenge(challenge: ActiveChallenge) =
        activeChallengeDao.delete(challenge)

    suspend fun updateActiveChallenge(challenge: ActiveChallenge) =
        activeChallengeDao.insert(challenge)
}
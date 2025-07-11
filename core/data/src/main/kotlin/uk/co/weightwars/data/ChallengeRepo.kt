package uk.co.weightwars.data

import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.entities.ActiveChallenge
import javax.inject.Inject

class ChallengeRepo @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao
) {
    suspend fun getActiveChallenge(id: Int) =
        activeChallengeDao.getById(id)

    suspend fun getActiveChallenges() =
        activeChallengeDao.getAll()

    suspend fun deleteActiveChallenge(challenge: ActiveChallenge) =
        activeChallengeDao.delete(challenge)
}
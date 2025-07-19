package uk.co.weightwars.data

import androidx.lifecycle.ViewModel
import uk.co.weightwars.database.dao.ChallengeCategoryDao
import uk.co.weightwars.database.dao.ChallengeDao
import javax.inject.Inject

class ChallengeRepo @Inject constructor(
    private val challengeDao: ChallengeDao,
    private val challengeCategoryDao: ChallengeCategoryDao
) : ViewModel() {
    suspend fun getCategoryChallenge(id: Long) = challengeCategoryDao.getCategory(id)

    suspend fun getCategoryChallenges() = challengeCategoryDao.getAll()

    suspend fun getChallenge(id: Long) = challengeDao.getChallenge(id)

    suspend fun getChallenges() = challengeDao.getAll()
}
package uk.co.weightwars.data

import androidx.lifecycle.ViewModel
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeWithCategory
import javax.inject.Inject

class ChallengeRepo @Inject constructor(
    private val challengeDao: ChallengeDao,
) : ViewModel() {
    suspend fun setChallengeCategory(challengeWithCategory: ChallengeWithCategory) =
        challengeDao.insert(challengeWithCategory)

    suspend fun getAllCategories() = challengeDao.getAllCategories()

    suspend fun getCategory(categoryId: Long) = challengeDao.getCategory(categoryId)

    suspend fun getChallenges(categoryId: Long): List<Challenge> {
        val category = getCategory(categoryId)
        return category.challenges
    }

    suspend fun getChallenge(challengeId: Long) = challengeDao.getChallenge(challengeId)
}
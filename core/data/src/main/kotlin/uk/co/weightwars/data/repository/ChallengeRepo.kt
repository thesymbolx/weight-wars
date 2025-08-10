package uk.co.weightwars.data.repository

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import uk.co.weightwars.data.models.Challenge
import uk.co.weightwars.data.models.ChallengeCategory
import uk.co.weightwars.data.models.toChallengeCategory
import uk.co.weightwars.network.datasource.ChallengeRemoteDataSource
import javax.inject.Inject

class ChallengeRepo @Inject constructor(
    private val challengeRemoteDataSource: ChallengeRemoteDataSource,
) : ViewModel() {

    fun getAllCategories() = challengeRemoteDataSource.getChallenges().map { challenges ->
        challenges.map {
            it.toChallengeCategory()
        }
    }

    fun getCategory(categoryId: Int): Flow<ChallengeCategory> =
        challengeRemoteDataSource.getChallenges().map {
            it.first { it.categoryId == categoryId }.toChallengeCategory()
        }

    suspend fun getChallenges(categoryId: Int): List<Challenge> {
        val categoryValue = getCategory(categoryId).first()
        return categoryValue.challenges
    }
}
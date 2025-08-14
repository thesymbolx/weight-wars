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
import uk.co.weightwars.network.datasource.ScoreRemoteDataSource
import uk.co.weightwars.network.model.FirebaseScore
import java.time.LocalDate
import javax.inject.Inject
import kotlin.Int

class ActiveChallengeRepo @Inject constructor(
    private val userDao: UserDao,
    private val activeChallengeRemoteDataSource: ActiveChallengeRemoteDataSource,
    private val scoreRemoteDataSource: ScoreRemoteDataSource
) {
    fun getActiveChallenge(id: String): Flow<ActiveChallengeWithScores> = flow {
        val currentUserId: String = withContext(Dispatchers.IO) {
            userDao.getCurrentUser()!!.profile.profileId
        }

        emitAll(
            activeChallengeRemoteDataSource.getActiveChallenge(id).flatMapLatest { firebaseActiveChallenge ->
                scoreRemoteDataSource.getScores(currentUserId, id).map { scores ->
                    val activeChallenge = firebaseActiveChallenge.toActiveChallenge()

                    val subChallengesWithScores = activeChallenge.subChallenges.map { subChallenge ->

                        val scoreList: List<FirebaseScore> = scores[subChallenge.subChallengeId] ?: emptyList()

                        SubChallengeWithScore(
                            subChallengeId = subChallenge.subChallengeId,
                            title = subChallenge.title,
                            scores = scoreList.map { score ->
                                Score(
                                    localDate = LocalDate.parse(score.date),
                                    score = score.score
                                )
                            },
                            lengthInDays = subChallenge.lengthInDays
                        )
                    }

                    with(activeChallenge) {
                        ActiveChallengeWithScores(
                            activeChallengeId = id,
                            title = title,
                            startDate = startDate,
                            days = days,
                            isHardcoreMode = isHardcoreMode,
                            subChallenges = subChallengesWithScores,
                            participants = participants
                        )
                    }
                }
            }
        )
    }


    fun getActiveChallenges(): Flow<ActiveChallenge> = flow {
        val currentUserId: String = withContext(Dispatchers.IO) {
            userDao.getCurrentUser()!!.profile.profileId
        }

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

    suspend fun setScores(
        activeChallengeWithScores: ActiveChallengeWithScores
    ) {
        val userId: String = withContext(Dispatchers.IO) {
            userDao.getCurrentUser()!!.profile.profileId
        }

        scoreRemoteDataSource.setScores(
            userId = userId,
            activeChallengeId = activeChallengeWithScores.activeChallengeId,
            scores = activeChallengeWithScores.subChallenges.flatMap {
                it.scores.map { score ->
                    FirebaseScore(
                        challengeId = it.subChallengeId,
                        date = score.localDate.toString(),
                        score = score.score
                    )
                }
            }
        )
    }
}

data class ActiveChallengeWithScores(
    val activeChallengeId: String = "",
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
    val subChallenges: List<SubChallengeWithScore>,
    val participants: List<Participant>
)

data class SubChallengeWithScore(
    val subChallengeId: Int ,
    val title: String,
    val scores: List<Score>,
    val lengthInDays: Int
)

data class Score(
    val localDate: LocalDate,
    val score: Int
)
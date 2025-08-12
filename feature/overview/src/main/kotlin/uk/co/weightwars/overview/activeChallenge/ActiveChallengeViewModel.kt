package uk.co.weightwars.overview.activeChallenge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.data.models.ScoreMark
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.data.repository.ActiveChallengeWithScores
import uk.co.weightwars.domain.ConsecutiveDateUseCase
import java.time.LocalDate

data class ActiveChallengeState(
    val name: String = "",
    val challenges: List<ChallengeState> = emptyList(),
    val totalScore: Int = 0
)

data class ChallengeState(
    val name: String,
    val totalScore: Int,
    val challengeDate: List<ChallengeDayState>,
)

data class ChallengeDayState(
    val id: Int,
    val localDate: LocalDate,
    val formattedDate: String,
    val dayName: String,
    val score: Int,
)

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    activeChallengeRepo: ActiveChallengeRepo,
    private val consecutiveDateUseCase: ConsecutiveDateUseCase,
    savedState: SavedStateHandle
) : ViewModel() {
    lateinit var activeChallengeEntity: ActiveChallengeWithScores

    val id = savedState.get<String>("activeChallengeId") ?: throw Exception()

    val uiState = activeChallengeRepo.getActiveChallenge(id).map { activeChallenge ->
        this.activeChallengeEntity = activeChallenge

        val challengeState = activeChallenge.subChallenges.map { challenge ->
            val consecutiveDate = consecutiveDateUseCase(
                date = activeChallenge.startDate,
                daysCount = challenge.lengthInDays
            )

            ChallengeState(
                name = challenge.title,
                totalScore = challenge.scores.sumOf { it.score },
                challengeDate = consecutiveDate.map { date ->
                    val score = challenge.scores.firstOrNull { it.localDate == date.localDate }

                    ChallengeDayState(
                        id = challenge.subChallengeId,
                        localDate = date.localDate,
                        formattedDate = date.formattedDate,
                        dayName = date.dayName,
                        score = score?.score ?: 0,
                    )
                }
            )
        }

        ActiveChallengeState(
            name = activeChallenge.title,
            totalScore = activeChallenge.subChallenges.sumOf { score ->
                score.scores.sumOf { it.score }
            },
            challenges = challengeState
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ActiveChallengeState()
    )

    fun score(challengeId: Int, date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {



//        val scoredFullMark = date == LocalDate.now()
//        val challengeItems = activeChallengeEntity.activeChallengeItemEntities.toMutableList()
//        var challengeItem = challengeItems.first { challengeId == it.activeChallengeItemId }
//        val scores = challengeItem.scoreEntities.toMutableSet()
//
//        scores.add(
//            ScoreEntity(
//                localDate = date,
//                score = if (scoredFullMark) 20 else 10,
//                mark = if (scoredFullMark) ScoreMark.FULL else ScoreMark.HALF
//            )
//        )
//
//        challengeItem = challengeItem.copy(
//            scoreEntities = scores
//        )
//
//        challengeItems.add(challengeItem)
//
//        activeChallengeEntity = activeChallengeEntity.copy(
//            activeChallengeItemEntities = challengeItems
//        )
//
//        activeChallengeRepo.updateActiveChallenge(activeChallengeEntity)
    }
}
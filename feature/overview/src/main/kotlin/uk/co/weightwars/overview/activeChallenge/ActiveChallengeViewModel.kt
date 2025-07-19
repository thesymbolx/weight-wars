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
import uk.co.weightwars.data.ActiveChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.Score
import uk.co.weightwars.database.entities.ScoreMark
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
    val id: Long,
    val localDate: LocalDate,
    val formattedDate: String,
    val dayName: String,
    val score: Int,
    val mark: ScoreMark,
)

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val activeChallengeRepo: ActiveChallengeRepo,
    private val consecutiveDateUseCase: ConsecutiveDateUseCase,
    savedState: SavedStateHandle
) : ViewModel() {
    lateinit var activeChallenge: ActiveChallenge

    val id = savedState.get<Long>("activeChallengeId") ?: throw Exception()

    val uiState = activeChallengeRepo.getActiveChallenge(id).map { activeChallenge ->
        this.activeChallenge = activeChallenge

        val challengeState = activeChallenge.activeChallengeItems.map { challenge ->
            val consecutiveDate = consecutiveDateUseCase(
                activeChallenge.challengeInfo.startDate,
                challenge.lengthInDays
            )

            ChallengeState(
                name = challenge.title,
                totalScore = challenge.scores.sumOf { it.score },
                challengeDate = consecutiveDate.map { date ->
                    val score = challenge.scores.firstOrNull { it.localDate == date.localDate }

                    ChallengeDayState(
                        id = challenge.activeChallengeItemId,
                        localDate = date.localDate,
                        formattedDate = date.formattedDate,
                        dayName = date.dayName,
                        score = score?.score ?: 0,
                        mark = score?.mark ?: ScoreMark.NONE
                    )
                }
            )
        }

        ActiveChallengeState(
            name = activeChallenge.challengeInfo.title,
            totalScore = activeChallenge.activeChallengeItems.sumOf { score ->
                score.scores.sumOf { it.score }
            },
            challenges = challengeState
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ActiveChallengeState()
    )

    fun deleteChallenge() = viewModelScope.launch(Dispatchers.IO) {
        activeChallengeRepo.deleteActiveChallenge(activeChallenge)
    }

    fun score(challengeId: Long, date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        val scoredFullMark = date == LocalDate.now()
        val challengeItems = activeChallenge.activeChallengeItems.toMutableList()
        var challengeItem = challengeItems.first { challengeId == it.activeChallengeItemId }
        val scores = challengeItem.scores.toMutableSet()

        scores.add(
            Score(
                localDate = date,
                score = if (scoredFullMark) 20 else 10,
                mark = if (scoredFullMark) ScoreMark.FULL else ScoreMark.HALF
            )
        )

        challengeItem = challengeItem.copy(
            scores = scores
        )

        challengeItems.add(challengeItem)

        activeChallenge = activeChallenge.copy(
            activeChallengeItems = challengeItems
        )

        activeChallengeRepo.updateActiveChallenge(activeChallenge)
    }
}
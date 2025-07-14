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
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.ScoreMark
import uk.co.weightwars.database.entities.ScoredDate
import uk.co.weightwars.domain.ConsecutiveDay
import uk.co.weightwars.domain.ConsecutiveDaysUseCase
import java.time.LocalDate
import kotlin.collections.map

data class ActiveChallengeState(
    val name: String = "",
    val scoringDate: List<ScoringDateState> = emptyList(),
    val totalScore: String = ""
)

data class ScoringDateState(
    val score: Int,
    val mark: ScoreMark,
    val consecutiveDay: ConsecutiveDay
)

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo,
    private val consecutiveDaysUseCase: ConsecutiveDaysUseCase,
    savedState: SavedStateHandle
) : ViewModel() {
    lateinit var challenge: ActiveChallenge

    val id = savedState.get<Int>("activeChallengeId") ?: throw Exception()

    val uiState = challengeRepo.getActiveChallenge(id).map { activeChallenge ->
        this.challenge = activeChallenge
        val consecutiveDays = consecutiveDaysUseCase(challenge.startDate, challenge.days)

        val scoringDate = consecutiveDays.map { consecutiveDay ->
            val scoredDate = activeChallenge.scoring.scores.firstOrNull {
                it.localDate == consecutiveDay.localDate
            }

            ScoringDateState(
                score = scoredDate?.score ?: 0,
                mark = scoredDate?.mark ?: ScoreMark.NONE,
                consecutiveDay = consecutiveDay,
            )
        }

        ActiveChallengeState(
            name = challenge.title,
            totalScore = challenge.scoring.total.toString(),
            scoringDate = scoringDate
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ActiveChallengeState()
    )

    fun deleteChallenge() = viewModelScope.launch(Dispatchers.IO) {
        challengeRepo.deleteActiveChallenge(challenge)
    }

    fun score(date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        val scoredFullMark = date == LocalDate.now()

        val scores = challenge
            .scoring
            .scores
            .toMutableSet()
            .apply {
                add(
                    ScoredDate(
                        localDate = date,
                        score = if(scoredFullMark) 20 else 10,
                        mark = if (scoredFullMark) ScoreMark.FULL else ScoreMark.HALF
                    )
                )
            }

        val updatedActiveChallenge = challenge.copy(
            scoring = challenge.scoring.copy(
                total = scores.sumOf { it.score },
                scores = scores
            )
        )

        challengeRepo.updateActiveChallenge(updatedActiveChallenge)
    }
}
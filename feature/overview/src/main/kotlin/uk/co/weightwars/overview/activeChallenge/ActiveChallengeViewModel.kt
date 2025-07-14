package uk.co.weightwars.overview.activeChallenge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.MissingFieldException
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.domain.ConsecutiveDay
import uk.co.weightwars.domain.ConsecutiveDaysUseCase
import uk.co.weightwars.overview.overviewScreen.OverviewUiState
import java.time.LocalDate
import java.util.MissingFormatArgumentException
import kotlin.collections.map

data class ActiveChallengeState(
    val name: String = "",
    val dayState: List<ScoringState> = emptyList()
)

data class ScoringState(
    val isSelected: Boolean,
    val consecutiveDay: ConsecutiveDay,
    val scoreState: ScoreState
)

enum class ScoreState {
    SUCCESS,
    FAILED,
    NO_STATE
}

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo,
    private val consecutiveDaysUseCase: ConsecutiveDaysUseCase,
    private val savedState: SavedStateHandle
) : ViewModel() {
    lateinit var challenge: ActiveChallenge

    val id = savedState.get<Int>("activeChallengeId") ?: throw Exception()

    val uiState = challengeRepo.getActiveChallenge(id).map { activeChallenge ->
        this.challenge = activeChallenge
        val consecutiveDays = consecutiveDaysUseCase(challenge.startDate, challenge.days)

        ActiveChallengeState(
            name = challenge.title,
            dayState = consecutiveDays.map { consecutiveDay ->
                ScoringState(
                    isSelected = challenge.scoring.scoredDates.contains(consecutiveDay.localDate),
                    consecutiveDay = consecutiveDay,
                    scoreState = ScoreState.NO_STATE
                )
            }
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ActiveChallengeState()
    )

    fun deleteChallenge() = viewModelScope.launch {
        challengeRepo.deleteActiveChallenge(challenge)
    }

    fun onDayClick(date: LocalDate) = viewModelScope.launch {
        val scoredDates = challenge
            .scoring
            .scoredDates
            .toMutableSet()

        scoredDates.add(date)

        challengeRepo.updateActiveChallenge(
            challenge.copy(scoring = challenge.scoring.copy(scoredDates))
        )
    }
}
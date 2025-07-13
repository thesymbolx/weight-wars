package uk.co.weightwars.overview.activeChallenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.domain.ConsecutiveDay
import uk.co.weightwars.domain.ConsecutiveDaysUseCase
import java.time.LocalDate

data class ActiveChallengeState(
    val name: String = "",
    val dayState: List<DayState> = emptyList()
)

data class DayState(
    val isSelected: Boolean,
    val consecutiveDay: ConsecutiveDay
)

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo,
    private val consecutiveDaysUseCase: ConsecutiveDaysUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ActiveChallengeState())

    lateinit var challenge: ActiveChallenge

    fun getChallenge(id: Int) = viewModelScope.launch {
        val challenge = challengeRepo.getActiveChallenge(id)
        this@ActiveChallengeViewModel.challenge = challenge
        val consecutiveDays = consecutiveDaysUseCase(challenge.startDate, challenge.days)

        uiState = uiState.copy(
            name = challenge.title,
            dayState = consecutiveDays.map { consecutiveDay ->
                DayState(
                    isSelected = challenge.scoring.scoredDates.contains(consecutiveDay.localDate),
                    consecutiveDay = consecutiveDay
                )
            }
        )
    }

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
package uk.co.weightwars.challenges.creation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.data.repository.ChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.ActiveChallengeItem
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeInfo
import java.time.LocalDate
import javax.inject.Inject

data class ChallengeCreationUiState(
    val isHardCordMode: Boolean = false,
    val selectedChallengeState: List<SelectedChallengeState> = emptyList(),
    val challengeSaved: Boolean = false
)

data class SelectedChallengeState(
    val id: Long,
    val title: String
)


@HiltViewModel
class ChallengeCreationViewModel @Inject constructor(
    val activeChallengeRepo: ActiveChallengeRepo,
    val challengeRepo: ChallengeRepo
) : ViewModel() {
    val challenges = mutableSetOf<Challenge>()

    var uiState by mutableStateOf(ChallengeCreationUiState())
        private set

    fun addChallenge(challengeId: Long) = viewModelScope.launch {
        val challenge: Challenge = with(Dispatchers.IO) {
            challengeRepo.getChallenge(challengeId)
        }

        challenges.add(challenge)

        uiState = uiState.copy(
            selectedChallengeState = challenges.map {
                SelectedChallengeState(
                    id = it.challengeId,
                    title = it.title
                )
            }
        )
    }

    fun saveActiveChallenge() = viewModelScope.launch {
        val activeChallengeLength = challenges.maxOf { it.days }
        val hardCoreMode = uiState.isHardCordMode

        with(Dispatchers.IO) {
            activeChallengeRepo.updateActiveChallenge(
                ActiveChallenge(
                    challengeInfo = ChallengeInfo(
                        title = challenges.joinToString(separator = ", ") { it.title },
                        startDate = LocalDate.now(),
                        days = activeChallengeLength,
                        isHardcoreMode = hardCoreMode,
                    ),
                    activeChallengeItems = challenges.map {
                        ActiveChallengeItem(
                            title = it.title,
                            lengthInDays = it.days
                        )
                    }
                )
            )
        }

        uiState = uiState.copy(
            challengeSaved = true
        )
    }

    fun clearSavedEvent() {
        uiState = uiState.copy(
            challengeSaved = false
        )
    }
}
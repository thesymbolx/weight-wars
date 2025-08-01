package uk.co.weightwars.challenges.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.data.repository.ChallengeRepo
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.ActiveChallengeItem
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeInfo
import java.time.LocalDate
import javax.inject.Inject

data class ChallengeCreationUiState(
    val isHardCordMode: Boolean = false,
    val selectedChallengeState: List<SelectedChallengeState> = emptyList(),
    val challengeSaved: Boolean = false,
    val friends: List<FriendState> = emptyList()
)

data class SelectedChallengeState(
    val id: Long,
    val title: String
)

data class FriendState(
    val name: String,
    val isSelected: Boolean
)

@HiltViewModel
class ChallengeCreationViewModel @Inject constructor(
    private val activeChallengeRepo: ActiveChallengeRepo,
    private val challengeRepo: ChallengeRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    val challenges = mutableSetOf<Challenge>()

    private val _uiState = MutableStateFlow(ChallengeCreationUiState())

    val uiState = _uiState.asStateFlow()

    fun getFriends() = viewModelScope.launch(Dispatchers.IO) {
        val currentUser = userRepo.getCurrentUser()
       // val friends = userRepo.getFriends()
    }

    fun addChallenge(challengeId: Long) = viewModelScope.launch {
        val challenge: Challenge = with(Dispatchers.IO) {
            challengeRepo.getChallenge(challengeId)
        }

        challenges.add(challenge)

        _uiState.update {
            it.copy(
                selectedChallengeState = challenges.map {
                    SelectedChallengeState(
                        id = it.challengeId,
                        title = it.title
                    )
                }
            )
        }
    }

    fun saveActiveChallenge() = viewModelScope.launch {
        val activeChallengeLength = challenges.maxOf { it.days }
        val hardCoreMode = _uiState.value.isHardCordMode

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

        _uiState.update {
            it.copy(
                challengeSaved = true
            )
        }
    }

    fun clearSavedEvent() {
        _uiState.update {
            it.copy(
                challengeSaved = false
            )
        }
    }
}
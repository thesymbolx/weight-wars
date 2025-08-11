package uk.co.weightwars.challenges.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.data.models.Challenge
import uk.co.weightwars.data.models.SubChallenge
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.data.repository.ChallengeRepo
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.CurrentUser
import java.time.LocalDate
import javax.inject.Inject

data class ChallengeCreationUiState(
    val saveEnabled: Boolean = false,
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
    val id: String,
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

    var currentUser: CurrentUser? = null

    init {
        getFriends()
    }

    fun getFriends() = viewModelScope.launch(Dispatchers.IO) {
        currentUser = userRepo.getCurrentUser()
        val friends = currentUser?.friends ?: emptyList()

        _uiState.update {
            it.copy(
                friends = friends.map {
                    FriendState(
                        id = it.friendId,
                        name = it.name,
                        isSelected = false
                    )
                }
            )
        }
    }

    fun addChallenge(challengeId: Int) = viewModelScope.launch {
        val challenge = with(Dispatchers.IO) {
            // Get all categories and find the specific challenge by ID
            val categories = challengeRepo.getAllCategories().first()
            categories.flatMap { it.challenges }.find { it.challengeId == challengeId }
        }

        // Only add the challenge if it was found
        challenge?.let { foundChallenge ->
            challenges.add(foundChallenge)

            _uiState.update {
                it.copy(
                    saveEnabled = currentUser != null,
                    selectedChallengeState = challenges.map {
                        SelectedChallengeState(
                            id = it.challengeId.toLong(),
                            title = it.title
                        )
                    }
                )
            }
        }
    }

    fun toggleFriend(friendState: FriendState) {
        val friends = _uiState.value.friends.toMutableList()
        val index = friends.indexOf(friendState)

        friends.set(
            index = index,
            element = friendState.copy(
                isSelected = !friendState.isSelected
            )
        )

        _uiState.update {
            it.copy(
                friends = friends
            )
        }
    }

    fun saveActiveChallenge() = viewModelScope.launch {
        val activeChallengeLength = challenges.maxOf { it.days }
        val hardCoreMode = _uiState.value.isHardCordMode
        val participants = _uiState.value.friends.filter { it.isSelected }

        with(Dispatchers.IO) {
            activeChallengeRepo.createActiveChallenge(
                activeChallenge = ActiveChallenge(
                    title = challenges.joinToString(separator = ", ") { it.title },
                    startDate = LocalDate.now(),
                    days = activeChallengeLength,
                    isHardcoreMode = hardCoreMode,
                    subChallenges = challenges.map { challenge ->
                        SubChallenge(
                            subChallengeId = challenge.challengeId,
                            title = challenge.title,
                            lengthInDays = challenge.days
                        )
                    }
                ),
                participantIds = participants.map { it.id } + currentUser!!.profile.profileId
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
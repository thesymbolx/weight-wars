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

data class ActiveChallengeState(val name: String = "")

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo
) : ViewModel() {
    var uiState by mutableStateOf(ActiveChallengeState())

    var challenge: ActiveChallenge? = null

    fun getChallenge(id: Int) = viewModelScope.launch {
        val challenge = challengeRepo.getActiveChallenge(id)
        uiState = uiState.copy(
            name = challenge.title
        )

        this@ActiveChallengeViewModel.challenge = challenge
    }

    fun deleteChallenge() = viewModelScope.launch {
        val challenge = challenge ?: return@launch
        challengeRepo.deleteActiveChallenge(challenge)
    }
}
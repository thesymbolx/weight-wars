package uk.co.weightwars.challenges.challenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.entities.Challenge
import javax.inject.Inject

data class ChallengeUiState(val challenges: List<Challenge> = listOf())

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo
) : ViewModel() {
    var uiState by mutableStateOf(ChallengeUiState())

    fun getChallenges(categoryId: Long) = viewModelScope.launch {
        val challenges = challengeRepo.getChallenges(categoryId)
        uiState = uiState.copy(challenges = challenges)
    }
}
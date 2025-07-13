package uk.co.weightwars.overview.overviewScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallenge
import javax.inject.Inject

data class OverviewUiState(
    val challenges: List<ActiveChallenge> = emptyList()
)

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo
) : ViewModel() {
    var uiState  = challengeRepo.getActiveChallenges().map { activeChallenge ->
        OverviewUiState(
            challenges = activeChallenge
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = OverviewUiState()
    )

}
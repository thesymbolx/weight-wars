package uk.co.weightwars.overview.overviewScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.models.ActiveChallenge
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import javax.inject.Inject

data class OverviewUiState(
    val challenges: List<ActiveChallenge> = emptyList()
)

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val activeChallengeRepo: ActiveChallengeRepo
) : ViewModel() {
    var uiState  = activeChallengeRepo
        .getActiveChallenges()
        .scan(emptyList<ActiveChallenge>()) { acc, value ->
            acc + value
        }
        .map { activeChallenges ->
            OverviewUiState(
                challenges = activeChallenges
            )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = OverviewUiState()
    )



}
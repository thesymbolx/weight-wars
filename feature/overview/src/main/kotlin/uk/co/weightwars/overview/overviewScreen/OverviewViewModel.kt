package uk.co.weightwars.overview.overviewScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import javax.inject.Inject

data class OverviewUiState(
    val challenges: List<ActiveChallengeEntity> = emptyList()
)

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val activeChallengeRepo: ActiveChallengeRepo
) : ViewModel() {
    val uiState = MutableStateFlow(OverviewUiState())


//    var uiState  = activeChallengeRepo.getActiveChallenges().map { activeChallenge ->
//        OverviewUiState(
//            challenges = activeChallenge
//        )
//    }.stateIn(
//        viewModelScope,
//        started = WhileSubscribed(5_000),
//        initialValue = OverviewUiState()
//    )

    init {
        viewModelScope.launch {
            activeChallengeRepo.getActiveChallenges().collect {
                Log.d("booboo", "$it")
            }
        }

    }

}
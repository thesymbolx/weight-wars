package uk.co.weightwars.challenges.challengeCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import uk.co.weightwars.data.models.ChallengeCategory
import uk.co.weightwars.data.repository.ChallengeRepo
import javax.inject.Inject

data class ChallengeCategoryUiState(
    val categories: List<ChallengeCategory> = listOf()
)

@HiltViewModel
class ChallengeCategoryViewModel @Inject constructor(
    private val challengeRepo: ChallengeRepo
) : ViewModel() {
    var uiState  = challengeRepo.getAllCategories().map { challenges ->
        ChallengeCategoryUiState(
            categories = challenges
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ChallengeCategoryUiState()
    )
}
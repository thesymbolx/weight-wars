package uk.co.weightwars.challenges.challengeCategory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ChallengeRepo
import uk.co.weightwars.database.entities.ChallengeCategory
import javax.inject.Inject

data class ChallengeCategoryUiState(
    val categories: List<ChallengeCategory> = listOf()
)

@HiltViewModel
class ChallengeCategoryViewModel @Inject constructor(
    private val challengeCategoryDao: ChallengeRepo,
) : ViewModel() {
    var uiState by mutableStateOf(ChallengeCategoryUiState())

    fun getChallengeCategories() = viewModelScope.launch {
        val categories = challengeCategoryDao.getAllCategories()

        uiState = uiState.copy(
            categories = categories.map { it.challengeCategory }
        )
    }
}
package uk.co.weightwars.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.ChallengeCategoryDao
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.ChallengeCategory
import javax.inject.Inject

data class OverviewUiState(
    val challenges: List<ActiveChallenge> = emptyList()
)

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val activeChallengeDao: ActiveChallengeDao
) : ViewModel() {
    var uiState by mutableStateOf(OverviewUiState())

    fun getChallenges() = viewModelScope.launch {
        val activeChallenges = activeChallengeDao.getAll()
        uiState = uiState.copy(challenges = activeChallenges)
    }
}
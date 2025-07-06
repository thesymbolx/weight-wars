package uk.co.weightwars.challenges.challenge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.Challenge
import javax.inject.Inject

data class ChallengeUiState(
    val categories: List<Challenge> = listOf()
)

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeDao: ChallengeDao,
    private val activeChallengeDao: ActiveChallengeDao
) : ViewModel() {
    var uiState by mutableStateOf(ChallengeUiState())

    fun getChallengeCategories() = viewModelScope.launch {
        val categories = challengeDao.getAll()

        uiState = uiState.copy(
            categories = categories
        )
    }

    fun saveChallenge(challengeId: Int) = viewModelScope.launch {

        val challenge = challengeDao.getChallenge(challengeId)


        activeChallengeDao.insert(ActiveChallenge(
            challenge.id,
            challenge.title,
            challenge.hasHardCoreMode
        ))
    }
}
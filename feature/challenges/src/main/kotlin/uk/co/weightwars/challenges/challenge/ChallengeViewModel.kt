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
import uk.co.weightwars.database.entities.Scoring
import java.time.LocalDate
import javax.inject.Inject

data class ChallengeUiState(val challenges: List<Challenge> = listOf())

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeDao: ChallengeDao,
    private val activeChallengeDao: ActiveChallengeDao
) : ViewModel() {
    var uiState by mutableStateOf(ChallengeUiState())

    fun getChallenges() = viewModelScope.launch {
        val challenges = challengeDao.getAll()
        uiState = uiState.copy(challenges = challenges)
    }

    fun saveChallenge(challengeId: Int) = viewModelScope.launch {
        val challenge = challengeDao.getChallenge(challengeId)

        val now = LocalDate.now()

        activeChallengeDao.insert(
            ActiveChallenge(
                id = challenge.id,
                title = challenge.title,
                startDate = now,
                days = challenge.days,
                scoring = Scoring(scoredDates = emptySet()),
                isHardcoreMode = false
            )
        )
    }
}
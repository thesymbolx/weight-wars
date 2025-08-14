package uk.co.weightwars.overview.activeChallenge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ActiveChallengeRepo
import uk.co.weightwars.data.repository.ActiveChallengeWithScores
import uk.co.weightwars.domain.ConsecutiveDateUseCase
import java.time.LocalDate
import uk.co.weightwars.data.repository.Score
import uk.co.weightwars.data.repository.UserRepo

data class ActiveChallengeState(
    val name: String = "",
    val challenges: List<ChallengeState> = emptyList(),
    val totalScore: Int = 0,
    val participants: List<ParticipantState> = emptyList()
)

data class ParticipantState(
    val id: String,
    val name: String,
    val totalScore: Int
)

data class ChallengeState(
    val name: String,
    val totalScore: Int,
    val challengeDate: List<ChallengeDayState>,
)

data class ChallengeDayState(
    val id: Int,
    val localDate: LocalDate,
    val formattedDate: String,
    val dayName: String,
    val score: Int,
)

@HiltViewModel
class ActiveChallengeViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val activeChallengeRepo: ActiveChallengeRepo,
    private val consecutiveDateUseCase: ConsecutiveDateUseCase,
    savedState: SavedStateHandle
) : ViewModel() {
    private lateinit var currentActiveChallenge: ActiveChallengeWithScores

    val id = savedState.get<String>("activeChallengeId") ?: throw Exception()

    val uiState = activeChallengeRepo.getActiveChallenge(id).map { activeChallenge ->
        this.currentActiveChallenge = activeChallenge

        val challengeState = activeChallenge.subChallenges.map { challenge ->
            val consecutiveDate = consecutiveDateUseCase(
                date = activeChallenge.startDate,
                daysCount = challenge.lengthInDays
            )

            ChallengeState(
                name = challenge.title,
                totalScore = challenge.scores.sumOf { it.score },
                challengeDate = consecutiveDate.map { date ->
                    val score = challenge.scores.firstOrNull { it.localDate == date.localDate }

                    ChallengeDayState(
                        id = challenge.subChallengeId,
                        localDate = date.localDate,
                        formattedDate = date.formattedDate,
                        dayName = date.dayName,
                        score = score?.score ?: 0,
                    )
                }
            )
        }

        ActiveChallengeState(
            name = activeChallenge.title,
            totalScore = activeChallenge.subChallenges.sumOf { score ->
                score.scores.sumOf { it.score }
            },
            challenges = challengeState,
            participants = activeChallenge.participants.map {
                ParticipantState(
                    id = it.participantId,
                    name = it.name,
                    totalScore = it.total
                )
            }.sortedBy { it.totalScore }
        )
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ActiveChallengeState()
    )

    fun score(challengeId: Int, date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        val currentUser = userRepo.getCurrentUser()

        val scoredFullMark = date == LocalDate.now()
        val challengeItems = currentActiveChallenge.subChallenges.toMutableList()
        var challengeItem = challengeItems.first { challengeId == it.subChallengeId }
        val scores = challengeItem.scores.toMutableList()

        scores.removeIf { it.localDate == date }

        scores.add(
            Score(
                localDate = date,
                score = if (scoredFullMark) 20 else 10
            )
        )

        challengeItem = challengeItem.copy(
            scores = scores.toList()
        )

        val existingChallengeIndex = challengeItems.indexOfFirst { it.subChallengeId == challengeId }
        if (existingChallengeIndex != -1) {
            challengeItems[existingChallengeIndex] = challengeItem
        } else {
            challengeItems.add(challengeItem)
        }

        val person = currentActiveChallenge.participants.find { it.participantId == currentUser!!.profile.profileId }
        val participants = currentActiveChallenge.participants.toMutableList()

        person?.let {
            val updatedPerson = it.copy(
                total = it.total + if (scoredFullMark) 20 else 10
            )
            val personIndex = participants.indexOfFirst { p -> p.participantId == updatedPerson.participantId }
            if (personIndex != -1) {
                participants[personIndex] = updatedPerson
            }
        }

        currentActiveChallenge = currentActiveChallenge.copy(
            subChallenges = challengeItems,
            participants = participants.toList()
        )

        activeChallengeRepo.setScores(
            currentActiveChallenge
        )
    }
}
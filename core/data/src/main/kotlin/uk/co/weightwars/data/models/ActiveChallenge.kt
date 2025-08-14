package uk.co.weightwars.data.models

import uk.co.weightwars.network.model.FirebaseActiveChallenge
import uk.co.weightwars.network.model.FirebaseParticipant
import uk.co.weightwars.network.model.FirebaseSubChallenge
import java.time.LocalDate
import kotlin.String

data class ActiveChallenge(
    val activeChallengeId: String = "",
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
    val subChallenges: List<SubChallenge>,
    val participants: List<Participant>
)

data class Participant(
    val participantId: String,
    val name: String,
    val total: Int
)

data class SubChallenge(
    val subChallengeId: Int,
    val title: String,
    val lengthInDays: Int
)

data class Score(
    val localDate: LocalDate,
    val score: Int,
    val mark: ScoreMark
)

enum class ScoreMark {
    FULL,
    HALF,
    NONE
}

fun ActiveChallenge.toNetworkChallenge() =
    FirebaseActiveChallenge(
        activeChallengeId = activeChallengeId,
        title = this.title,
        startDate = this.startDate.toString(),
        days = this.days,
        isHardcoreMode = this.isHardcoreMode,
        subChallenges = this.subChallenges.map {
            FirebaseSubChallenge(
                subChallengeId = it.subChallengeId,
                title = it.title,
                lengthInDays = it.lengthInDays
            )
        },
        participants = participants.map {
            FirebaseParticipant(
                id = it.participantId,
                name = it.name,
                totalScore = it.total
            )
        }
    )

fun FirebaseActiveChallenge.toActiveChallenge() =
    ActiveChallenge(
        activeChallengeId = activeChallengeId,
        title = this.title,
        startDate = LocalDate.parse(startDate),
        days = this.days,
        isHardcoreMode = this.isHardcoreMode,
        subChallenges = this.subChallenges.map {
            SubChallenge(
                subChallengeId = it.subChallengeId,
                title = it.title,
                lengthInDays = it.lengthInDays
            )
        },
        participants = participants.map {
            Participant(
                participantId = it.id,
                name = it.name,
                total = it.totalScore
            )
        }
    )
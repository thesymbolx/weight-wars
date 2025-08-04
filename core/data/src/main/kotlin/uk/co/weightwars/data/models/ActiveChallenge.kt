package uk.co.weightwars.data.models

import uk.co.weightwars.network.model.NetworkActiveChallenge
import java.time.LocalDate
import kotlin.String

data class ActiveChallenge(
    val challengeInfoId: Long = -1,
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
    val participantsIds: List<String> = listOf(),
    val subChallenges: List<SubChallenge>
) {
    fun toNetworkChallenge() =
        NetworkActiveChallenge(
            id = "",
            title = this.title,
            startDate = this.startDate.toString(),
            days = this.days,
            isHardcoreMode = this.isHardcoreMode,
            participantsIds = listOf(),
            subChallengeIds = this.subChallenges.map { it.subChallengeId }
        )
}

data class SubChallenge(
    val subChallengeId: Long,
    val title: String,
    val scores: Set<Score> = emptySet(),
    val lengthInDays: Int
)

data class Score(
    val localDate: LocalDate,
    val score: Int,
    val mark: ScoreMark
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Score) return false
        return localDate == other.localDate
    }

    override fun hashCode(): Int {
        return localDate.hashCode()
    }

    override fun toString(): String {
        return "ScoredDate(localDate=$localDate, score=$score, mark=$mark)"
    }
}

enum class ScoreMark {
    FULL,
    HALF,
    NONE
}



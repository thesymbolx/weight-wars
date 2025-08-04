package uk.co.weightwars.data.models

import androidx.room.Entity
import uk.co.weightwars.database.entities.ActiveChallengeEntity
import uk.co.weightwars.database.entities.ChallengeInfoEntity
import uk.co.weightwars.database.entities.ParticipantEntity
import uk.co.weightwars.database.entities.ScoreEntity
import uk.co.weightwars.database.entities.SubChallengeEntity
import uk.co.weightwars.network.model.NetworkActiveChallenge
import java.time.LocalDate
import kotlin.String

data class ActiveChallenge(
    val challengeInfoId: Long = -1,
    val title: String,
    val startDate: LocalDate,
    val days: Int,
    val isHardcoreMode: Boolean,
    val participants: List<Long>,
    val subChallenges: List<SubChallenge>
) {
    fun toNetworkChallenge() =
        NetworkActiveChallenge(
            id = "",
            title = this.title,
            startDate = this.startDate.toString(),
            days = this.days,
            isHardcoreMode = this.isHardcoreMode,
            participantsIds = this.participants.map { it },
            subChallengeIds = this.subChallenges.map { it.subChallengeId }
        )

    fun toActiveChallengeEntity() =
        ActiveChallengeEntity(
            challengeInfoEntity = ChallengeInfoEntity(
                title = title,
                startDate = startDate,
                days = days,
                isHardcoreMode = isHardcoreMode
            ),
            subChallenges = subChallenges.map { subChallenge ->
                SubChallengeEntity(
                    title = subChallenge.title,
                    lengthInDays = subChallenge.lengthInDays,
                    scores = subChallenge.scores.map { score ->
                        ScoreEntity(
                            localDate = score.localDate,
                            score = score.score,
                            mark = score.mark.name
                        )
                    }
                )
            },
            participants = participants.map {
                ParticipantEntity(it)
            }
        )
}

data class Participant(
    val participantId: Long,
)

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

fun ActiveChallengeEntity.toActiveChallenge() =
    ActiveChallenge(
        challengeInfoId = challengeInfoEntity.challengeInfoId,
        title = challengeInfoEntity.title,
        startDate = challengeInfoEntity.startDate,
        days = challengeInfoEntity.days,
        isHardcoreMode = challengeInfoEntity.isHardcoreMode,
        subChallenges = subChallenges.map { subChallenge ->
            SubChallenge(
                subChallengeId = subChallenge.subChallengeId,
                title = subChallenge.title,
                lengthInDays = subChallenge.lengthInDays,
                scores = subChallenge.scores.map { score ->
                    Score(
                        localDate = score.localDate,
                        mark = ScoreMark.valueOf(score.mark),
                        score = score.score
                    )
                }.toSet()
            )
        },
        participants = participants.map { it.participantId }
    )



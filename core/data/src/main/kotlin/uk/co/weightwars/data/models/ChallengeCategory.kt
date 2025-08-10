package uk.co.weightwars.data.models

import uk.co.weightwars.network.model.FirebaseChallenge
import uk.co.weightwars.network.model.FirebaseChallengeCategory

data class ChallengeCategory(
    val categoryId: Int,
    val title: String,
    val challenges: List<Challenge>
)

data class Challenge(
    val challengeId: Int,
    val title: String,
    val description: String,
    val days: Int
)

fun FirebaseChallengeCategory.toChallengeCategory() =
    ChallengeCategory(
        categoryId = categoryId,
        title = title,
        challenges = challenges.map { it.toChallenge() }
    )

fun FirebaseChallenge.toChallenge() =
    Challenge(
        challengeId = challengeId,
        title = title,
        description = description,
        days = days
    )



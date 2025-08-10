package uk.co.weightwars.network.model

data class FirebaseChallengeCategory(
    val categoryId: Int = 0,
    val title: String = "",
    val challenges: List<FirebaseChallenge> = emptyList()
)

data class FirebaseChallenge(
    val challengeId: Int = 0,
    val title: String = "",
    val description: String = "",
    val days: Int = 0
)
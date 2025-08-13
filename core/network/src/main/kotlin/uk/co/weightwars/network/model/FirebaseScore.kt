package uk.co.weightwars.network.model

data class FirebaseScore(
    val challengeId: Int = -1,
    val date: String = "",
    val score: Int = -1
)
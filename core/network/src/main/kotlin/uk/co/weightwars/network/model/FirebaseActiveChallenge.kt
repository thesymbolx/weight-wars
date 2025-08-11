package uk.co.weightwars.network.model

data class FirebaseActiveChallenge(
    val activeChallengeId: String = "",
    val title: String = "",
    val startDate: String = "",
    val days: Int = 0,
    val isHardcoreMode: Boolean = false,
    val subChallenges: List<FirebaseSubChallenge> = listOf()
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "activeChallengeId" to activeChallengeId,
            "title" to title,
            "startDate" to startDate,
            "days" to days,
            "isHardcoreMode" to isHardcoreMode,
            "subChallenges" to subChallenges
        )
    }
}

data class FirebaseSubChallenge(
    val subChallengeId: Int = -1,
    val title: String = "",
    val lengthInDays: Int = -1,
)

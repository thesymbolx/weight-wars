package uk.co.weightwars.network.model

data class FirebaseActiveChallenge(
    val id: String = "",
    val title: String = "",
    val startDate: String = "",
    val days: Int = 0,
    val isHardcoreMode: Boolean = false,
    val participantsIds: List<String> = listOf(),
    val subChallengeIds: List<Long> = listOf()
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "startDate" to startDate,
            "days" to days,
            "isHardcoreMode" to isHardcoreMode,
            "participantsIds" to participantsIds,
        )
    }
}
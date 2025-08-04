package uk.co.weightwars.network.model

data class NetworkActiveChallenge(
    val id: String = "",
    val title: String = "",
    val startDate: String = "",
    val days: Int = 0,
    val isHardcoreMode: Boolean = false,
    val participantsIds: List<String> = listOf(),
    val networkActiveChallengeItem: List<NetworkActiveChallengeItem> = listOf()
)

data class NetworkActiveChallengeItem(
    var activeChallengeItemId: String = "",
    var title: String = "",
    var scores: List<NetworkScore> = listOf(),
    var lengthInDays: Int = 0
)

data class NetworkScore(
    var localDate: String = "",
    var score: Int = 0,
    var mark: String = NetworkScoreMark.NONE.name
)

enum class NetworkScoreMark {
    FULL,
    HALF,
    NONE
}
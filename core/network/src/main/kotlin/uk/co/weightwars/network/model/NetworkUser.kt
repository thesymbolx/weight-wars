package uk.co.weightwars.network.model

data class NetworkUser(
    val id: Long = 0L,
    val name: String = "",
    val friends: Set<Long>
)
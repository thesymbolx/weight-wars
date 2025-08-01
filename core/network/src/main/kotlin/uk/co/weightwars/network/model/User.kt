package uk.co.weightwars.network.model

data class User(
    val id: Long = 0L,
    val name: String = "",
    val friends: List<Long> = emptyList()
)
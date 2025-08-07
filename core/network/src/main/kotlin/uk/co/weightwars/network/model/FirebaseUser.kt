package uk.co.weightwars.network.model

data class FirebaseUser(
    val id: String = "",
    val name: String = "",
    val friends: List<String> = emptyList()
)
package uk.co.weightwars.data

class Facade {
    fun getChallenge(challengeId: String) : Challenge {
        return Challenge(
            id = 1,
            "No Sugar"
        )
    }
}
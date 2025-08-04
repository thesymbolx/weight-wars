package uk.co.weightwars.network.datasource

import com.google.firebase.database.DatabaseReference
import jakarta.inject.Inject
import uk.co.weightwars.network.model.NetworkActiveChallenge

class ActiveChallengeDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val activeChallengeChild = "activeChallenge"
    val usersChild = "users"

    suspend fun createActiveChallenge(networkActiveChallenge: NetworkActiveChallenge) : String {
        val activeChallengeKey = firebaseDatabase.child(activeChallengeChild).push().key ?: throw Exception()

        val challengeWithId = networkActiveChallenge.copy(
            id = activeChallengeKey
        )

        val childUpdates = hashMapOf<String, Any>(
            "/$activeChallengeChild/$activeChallengeKey" to challengeWithId.toMap(),
        )
        networkActiveChallenge.participantsIds.forEach { participantId ->
            val userActiveChallengePath = "/$usersChild/${participantId}/activeChallenges"
            childUpdates.put(userActiveChallengePath, activeChallengeKey)
        }

        firebaseDatabase.updateChildren(childUpdates)

        return activeChallengeKey
    }

}
package uk.co.weightwars.network.datasource

import com.google.firebase.database.DatabaseReference
import jakarta.inject.Inject
import uk.co.weightwars.network.model.NetworkActiveChallenge

class ActiveChallengeDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val activeChallengeChild = "activeChallenge"

    suspend fun saveActiveChallenge(networkActiveChallenge: NetworkActiveChallenge) : NetworkActiveChallenge {
        val activeChallengeKey = firebaseDatabase.child(activeChallengeChild).push().key ?: throw Exception()

        val challengeWithId = networkActiveChallenge.copy(
            id = activeChallengeKey
        )

        val childUpdates = hashMapOf<String, Any>(
            "/$activeChallengeChild/$activeChallengeKey" to challengeWithId.toMap(),
        )

        firebaseDatabase.updateChildren(childUpdates)

        return challengeWithId
    }

}
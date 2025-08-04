package uk.co.weightwars.network.datasource

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
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
        for (participantId in networkActiveChallenge.participantsIds) {
            val userActiveChallengePath = "/$usersChild/${participantId}/activeChallenges"
            val existingActiveChallengesSnapshot = firebaseDatabase.child(userActiveChallengePath).get().await()
            val existingActiveChallenges = existingActiveChallengesSnapshot.getValue<List<String>>()?.toMutableList()
                ?: mutableListOf()

            existingActiveChallenges.add(activeChallengeKey)
            childUpdates[userActiveChallengePath] = existingActiveChallenges
        }
        firebaseDatabase.updateChildren(childUpdates)

        return activeChallengeKey
    }

}
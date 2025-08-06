package uk.co.weightwars.network.datasource

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await
import jakarta.inject.Inject
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.FirebaseAction
import uk.co.weightwars.network.model.NetworkActiveChallenge
import uk.co.weightwars.network.model.User

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

    fun getUserActiveChallenges(userId: String) = callbackFlow {
        val listener = object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                val activeChallengeIds = snapshot.getValue<List<String>>()
                trySend(FirebaseAction.Added(activeChallengeIds))
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                val activeChallengeIds = snapshot.getValue<List<String>>()
                trySend(FirebaseAction.Modified(activeChallengeIds))
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val activeChallengeIds = snapshot.getValue<List<String>>()
                trySend(FirebaseAction.Removed(activeChallengeIds))
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {}

            override fun onCancelled(error: DatabaseError) {}

        }

        val ref = firebaseDatabase.child(usersChild).child(userId).child("activeChallenges")

        ref.addChildEventListener(listener)

    }

    fun getActiveChallenges(activeChallengeIds: List<String>) {

    }


}
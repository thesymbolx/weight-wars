package uk.co.weightwars.network.datasource

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.FirebaseActiveChallenge

class ActiveChallengeRemoteDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val activeChallengeChild = "activeChallenge"
    val usersChild = "users"

    suspend fun createActiveChallenge(firebaseActiveChallenge: FirebaseActiveChallenge) : String {
        val activeChallengeKey = firebaseDatabase.child(activeChallengeChild).push().key ?: throw Exception()

        val challengeWithId = firebaseActiveChallenge.copy(
            id = activeChallengeKey
        )

        val childUpdates = hashMapOf<String, Any>(
            "/$activeChallengeChild/$activeChallengeKey" to challengeWithId.toMap(),
        )
        for (participantId in firebaseActiveChallenge.participantsIds) {
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

    fun getActiveChallenge(activeChallengeId: String): Flow<FirebaseActiveChallenge> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val activeChallengeIds = snapshot.getValue<FirebaseActiveChallenge>()
                if(activeChallengeIds != null) trySend(activeChallengeIds)
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        firebaseDatabase.child(activeChallengeChild).child(activeChallengeId).addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }

    fun getUserActiveChallenges(userId: String) = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val activeChallengeIds = mutableListOf<String>()
                for (childSnapshot in snapshot.children) {
                    val activeChallengeId = childSnapshot.getValue<String>()
                    if (activeChallengeId != null) {
                        activeChallengeIds.add(activeChallengeId)
                    }
                }
                trySend(activeChallengeIds)
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        val ref = firebaseDatabase.child(usersChild).child(userId).child("activeChallenges")
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }
}
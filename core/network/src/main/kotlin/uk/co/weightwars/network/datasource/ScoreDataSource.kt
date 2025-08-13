package uk.co.weightwars.network.datasource

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.FirebaseScore
import javax.inject.Inject

class ScoreRemoteDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val scoresChildNode = "scores"

    suspend fun setScores(
        userId: String,
        activeChallengeId: String,
        scores: List<FirebaseScore>
    ) {
        val ref = firebaseDatabase
            .child(scoresChildNode)
            .child(userId)
            .child(activeChallengeId)


        ref.setValue(scores.toList())
    }

    fun getScores(
        userId: String,
        activeChallengeId: String,
    ) = callbackFlow<Map<Int, List<FirebaseScore>>> {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val scoresFromFirebase = snapshot.getValue<List<FirebaseScore>>()

                val groupedScores: Map<Int, List<FirebaseScore>> = scoresFromFirebase?.groupBy {
                    it.challengeId
                } ?: emptyMap()

                trySend(groupedScores)
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        firebaseDatabase
            .child(scoresChildNode)
            .child(userId)
            .child(activeChallengeId)
            .addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }

    }
}
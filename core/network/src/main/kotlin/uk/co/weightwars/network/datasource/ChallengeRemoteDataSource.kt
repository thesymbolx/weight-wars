package uk.co.weightwars.network.datasource

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.FirebaseChallengeCategory
import javax.inject.Inject

class ChallengeRemoteDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val challengesNode = "challenges"

    fun getChallenges(): Flow<List<FirebaseChallengeCategory>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val challenges = snapshot.getValue<List<FirebaseChallengeCategory>>()
                if(challenges != null) trySend(challenges)
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        firebaseDatabase.child(challengesNode).addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }

}
package uk.co.weightwars.network.datasource

import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.FirebaseActiveChallenge
import uk.co.weightwars.network.model.FirebaseUser
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    val firestore = Firebase.firestore
    val usersNode = "users"




    fun getCurrentUserId() : FirebaseUser {

    }

    fun setUser(firebaseUser: FirebaseUser) {
        firestore
            .collection(usersNode)
            .document(firebaseUser.id)
            .set(firebaseUser)
    }

    fun getAllUsers(): Flow<FirebaseUser> = callbackFlow {
        val listener = object : ChildEventListener {
            override fun onChildAdded(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                val friend = snapshot.getValue<FirebaseUser>()
                if(friend != null) trySend(friend)
            }

            override fun onChildChanged(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                val friend = snapshot.getValue<FirebaseUser>()
                if(friend != null) trySend(friend)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                //TODO("Not yet implemented")
            }

            override fun onChildMoved(
                snapshot: DataSnapshot,
                previousChildName: String?
            ) {
                //TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {}

        }


        firebaseDatabase.child("users").addChildEventListener(listener)

        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }


}
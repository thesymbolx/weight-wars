package uk.co.weightwars.network.datasource

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uk.co.weightwars.network.model.NetworkUser
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    fun setUser(user: NetworkUser) {
        firebaseDatabase.child("users").child("${user.id}").setValue(user)
    }

    fun getFriends(): Flow<NetworkUser> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<NetworkUser>()

                if (user != null) {
                    trySend(user)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        firebaseDatabase.addValueEventListener(listener)

        awaitClose {
            firebaseDatabase.removeEventListener(listener)
        }
    }


}
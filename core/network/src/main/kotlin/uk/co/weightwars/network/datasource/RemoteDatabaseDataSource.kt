package uk.co.weightwars.network.datasource

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uk.co.weightwars.network.model.NetworkUser
import javax.inject.Inject

class RemoteDatabaseDataSource @Inject constructor(
    private val firebaseDatabase: DatabaseReference
) {
    fun setUser(user: NetworkUser) {
        firebaseDatabase
    }
}
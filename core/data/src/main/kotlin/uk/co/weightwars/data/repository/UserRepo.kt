package uk.co.weightwars.data.repository

import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.database.entities.Profile
import uk.co.weightwars.network.datasource.UserRemoteDataSource
import javax.inject.Inject
import uk.co.weightwars.network.model.FirebaseUser
import kotlin.random.Random

class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun getCurrentUser() = userDao.getCurrentUser()

    fun getCurrentUserAsFlow() = userDao.getCurrentUserAsFlow()

    fun createCurrentUser(name: String): CurrentUser {
        val nodeKey = userRemoteDataSource.createUser()

        return CurrentUser(
            profile = Profile(
                profileId = nodeKey,
                name = name
            ),
            friends = emptyList()
        )
    }

    suspend fun saveCurrentUser(currentUser: CurrentUser) {
        val dbUser = userDao.getCurrentUser()
        val user = if(dbUser == null) {
            val firebaseNodeId = userRemoteDataSource.createUser()
            currentUser.copy(profile = currentUser.profile.copy(profileId = firebaseNodeId))
        } else currentUser

        userDao.insertCurrentUser(user)

        val networkFirebaseUser = FirebaseUser(
            id = user.profile.profileId,
            name = user.profile.name,
            friends = user.friends.map { it.friendId }
        )

        userRemoteDataSource.setUser(networkFirebaseUser)
    }

    fun getAllUsers() = userRemoteDataSource.getAllUsers()
} 
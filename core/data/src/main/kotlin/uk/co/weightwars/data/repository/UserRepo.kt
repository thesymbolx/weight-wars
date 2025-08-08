package uk.co.weightwars.data.repository

import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.network.datasource.UserRemoteDataSource
import javax.inject.Inject
import uk.co.weightwars.network.model.FirebaseUser

class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun getCurrentUser() = userDao.getCurrentUser()

    fun getCurrentUserAsFlow() = userDao.getCurrentUserAsFlow()

    suspend fun createCurrentUser(currentUser: CurrentUser) {
        val nodeKey = userRemoteDataSource.createUserKey()

        val userWithKey = currentUser.copy(
            profile = currentUser.profile.copy(
                profileId = nodeKey
            ),
            friends = currentUser.friends.map {
                it.copy(
                    profileParentId = nodeKey
                )
            }
        )

        userRemoteDataSource.setUser(
            FirebaseUser(
                id = nodeKey,
                name = userWithKey.profile.name,
                friends = currentUser.friends.map { it.friendId }
            )
        )

        userDao.insertCurrentUser(userWithKey)
    }

    suspend fun saveCurrentUser(currentUser: CurrentUser) {
        val dbUser = userDao.getCurrentUser()
        val user = if(dbUser == null) {
            val firebaseNodeId = userRemoteDataSource.createUserKey()
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
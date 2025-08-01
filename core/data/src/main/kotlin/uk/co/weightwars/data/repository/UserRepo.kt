package uk.co.weightwars.data.repository

import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.network.datasource.UserRemoteDataSource
import javax.inject.Inject
import uk.co.weightwars.network.model.User
import kotlin.random.Random

class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val userRemoteDataSource: UserRemoteDataSource
) {
    suspend fun getCurrentUser() = userDao.getCurrentUser()

    fun getCurrentUserAsFlow() = userDao.getCurrentUserAsFlow()

    suspend fun saveCurrentUser(currentUser: CurrentUser) {
        val dbUser = userDao.getCurrentUser()
        val user = if(dbUser == null) {
            currentUser.copy(profile = currentUser.profile.copy(profileId = Random.nextLong()))
        } else currentUser

        userDao.insertCurrentUser(user)

        val networkUser = User(
            id = user.profile.profileId,
            name = user.profile.name,
            friends = user.friends.map { it.friendId }
        )

        userRemoteDataSource.setUser(networkUser)
    }

    fun getAllUsers() = userRemoteDataSource.getAllUsers()
} 
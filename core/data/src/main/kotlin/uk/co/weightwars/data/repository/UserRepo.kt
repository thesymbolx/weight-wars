package uk.co.weightwars.data.repository

import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.UserWithFriend
import uk.co.weightwars.network.datasource.UserRemoteDataSource
import javax.inject.Inject
import uk.co.weightwars.network.model.NetworkUser
import kotlin.random.Random

class UserRepo @Inject constructor(
    private val userDao: UserDao,
    private val userRemoteDataSource: UserRemoteDataSource
) {
     fun getAllUsers() = userRemoteDataSource.getAllUsers()

     fun getFriends(userId: Long): Flow<NetworkUser> {
        return userRemoteDataSource.getAllUsers()
    }

    fun currentUserIdFlow() = userDao.getCurrentUserIdFlow()

    suspend fun getUser() = userDao.getCurrentUser()

    suspend fun saveUser(userWithFriend: UserWithFriend) {
        var dbUser = userDao.getCurrentUser()
        var user = if(dbUser == null) {
            userWithFriend.copy(user = userWithFriend.user.copy(userId = Random.nextLong()))
        } else userWithFriend

        userDao.insertFriends(user)

        val networkUser = NetworkUser(id = user.user.userId, name = user.user.name, friends = user.friends.map { it.friendId })
        userRemoteDataSource.setUser(networkUser)
    }
} 
package uk.co.weightwars.data.repository

import kotlinx.coroutines.flow.Flow
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.User
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

    fun getCurrentUserId() = userDao.getCurrentUserId()

    suspend fun getUser() = userDao.getCurrentUser()

    suspend fun saveUser(user: User) {
        var dbUser = userDao.getCurrentUser()
        var user = if(dbUser == null) user.copy(id = Random.nextLong()) else user

        userDao.insert(user)

        val networkUser = NetworkUser(id = user.id, name = user.name)
        userRemoteDataSource.setUser(networkUser)
    }
} 
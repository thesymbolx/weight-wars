package uk.co.weightwars.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.Friend
import javax.inject.Inject

data class FriendsUiState(
    val name: String = "",
    val users: Set<UserState> = emptySet(),
)

data class UserState(
    val id: Long,
    val name: String,
    val isSelected: Boolean
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        loadUsers()
    }

    private fun loadUsers() = viewModelScope.launch {
       // val currentUser = userRepo.getUser()
        //  val currentUserId = currentUser?.user?.userId


        combine(userRepo.currentUserFlow(), userRepo.getAllUsers()) { currentUser, networkUser ->
              val currentUserId = currentUser?.userId

            if (currentUserId != null && networkUser.id != currentUserId) {
                _uiState.update { currentState ->
                    val newUser = UserState(
                        id = networkUser.id,
                        name = networkUser.name,
                        isSelected = false
                    )
                    currentState.copy(
                        name = currentUser.name,
                        users = currentState.users + newUser
                    )
                }
            }
        }.collect()


//        userRepo.getAllUsers().collect { user ->
//            if (currentUserId != null && user.id != currentUserId) {
//                _uiState.update { currentState ->
//                    val newUser = UserState(
//                        id = user.id,
//                        name = user.name,
//                        isSelected = false
//                    )
//                    currentState.copy(
//                        userName = currentUser.user.name,
//                        users = currentState.users + newUser
//                    )
//                }
//            }
//        }
    }

    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun toggleFriend(newFriend: UserState) = viewModelScope.launch(Dispatchers.IO) {
        val friendId = newFriend.id
        val currentUser = userRepo.getUser()

        if (currentUser != null) {
            val friends = currentUser.friends.toMutableList()

            withContext(Dispatchers.Main) {
                _uiState.update { currentState ->
                    val updatedUsers = currentState.users.map { user ->
                        if (user.id == friendId) {
                            user.copy(isSelected = !user.isSelected)
                        } else {
                            user
                        }
                    }
                    currentState.copy(users = updatedUsers.toSet())
                }
            }

            friends?.add(Friend(friendId = newFriend.id, name = newFriend.name))

            val currentUserWithFriend = currentUser.copy(
                friends = friends
            )

            userRepo.saveUser(currentUserWithFriend)
        }
    }

    fun saveUser() = viewModelScope.launch {
        val user = userRepo.getUser()
        val name = _uiState.value.name

        //   val userWithName = user?.copy(user = user.user.copy(name = name)) ?: UserWithFriend(user = User(name), friends = emptyList<>())

        //   userRepo.saveUser(userWithName)
    }

}
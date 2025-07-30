package uk.co.weightwars.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.User
import javax.inject.Inject

data class FriendsUiState(
    val name: String = "",
    val users: Set<UserState> = emptySet()
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
        userRepo.getAllUsers().collect { user ->
            _uiState.update { currentState ->
                val newUser = UserState(
                    id = user.id,
                    name = user.name,
                    isSelected = false
                )
                currentState.copy(
                    users = currentState.users + newUser
                )
            }
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun toggleFriend(friendId: Long) {
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

    fun saveUser() = viewModelScope.launch {
        val user = userRepo.getUser()
        val name = _uiState.value.name

        val userWithName = user?.copy(name = name) ?: User(name = name)

        userRepo.saveUser(userWithName)
    }

}
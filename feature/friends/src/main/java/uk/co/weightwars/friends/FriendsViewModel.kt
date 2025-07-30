package uk.co.weightwars.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.User
import javax.inject.Inject

data class FriendsUiState(
    val name: String = "",
    val users: List<UserState> = emptyList()
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

    val uiState = combine(_uiState, userRepo.getAllUsers()) { uiState, user ->
        val users = uiState.users.toMutableList()

        users.add(
            UserState(
                id = user.id,
                name = user.name,
                isSelected = false
            )
        )

        uiState.copy(users = users)
    }.stateIn(
        viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = FriendsUiState()
    )

    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun saveUser() = viewModelScope.launch {
        val user = userRepo.getUser()
        val name = _uiState.value.name

        val userWithName = user?.copy(name = name) ?: User(name = name)

        userRepo.saveUser(userWithName)
    }

}
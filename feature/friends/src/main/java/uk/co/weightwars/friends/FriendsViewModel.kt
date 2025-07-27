package uk.co.weightwars.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.User
import javax.inject.Inject

data class FriendsState(
    val name: String = ""
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    var friendsState by mutableStateOf(FriendsState())
        private set

    fun onNameChange(newName: String) {
        friendsState = friendsState.copy(name = newName)
    }

    fun saveUser() = viewModelScope.launch {
        val user = userRepo.getUser()
        val name = friendsState.name

        val userWithName = user?.copy(name = name) ?: User(name = name)

        userRepo.saveUser(userWithName)
    }

}
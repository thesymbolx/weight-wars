package uk.co.weightwars.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.database.entities.Profile
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    var userCreated by mutableStateOf(false)

    fun saveCurrentUserName(newName: String) = viewModelScope.launch(Dispatchers.IO) {
        val newUser = CurrentUser(
            profile = Profile(
                profileId = "",
                name = newName
            ),
            friends = emptyList()
        )

        userRepo.createCurrentUser(newUser)
    }

    fun userCreated() {
        userCreated = false
    }
}
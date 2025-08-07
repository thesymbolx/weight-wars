package uk.co.weightwars.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.entities.CurrentUser
import uk.co.weightwars.database.entities.Friend
import uk.co.weightwars.database.entities.Profile
import javax.inject.Inject

data class FriendsUiState(
    val name: String = "",
    val users: Set<UserState> = emptySet(),
)

data class UserState(
    val id: String,
    val name: String,
    val isSelected: Boolean
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {

    var uiState = userRepo.getCurrentUserAsFlow()
        .flatMapLatest { currentUser ->
            if (currentUser == null) {
                return@flatMapLatest flowOf(FriendsUiState(name = "", users = emptySet()))
            }

            val currentUserName = currentUser.profile.name
            val currentUserId = currentUser.profile.profileId

            userRepo.getAllUsers()
                .filter { networkUser -> networkUser.id != currentUserId }
                .map { networkUser ->
                    val isFriend =
                        currentUser.friends.any { friend -> friend.friendId == networkUser.id }
                    UserState(
                        id = networkUser.id,
                        name = networkUser.name,
                        isSelected = isFriend
                    )
                }
                .scan(emptySet<UserState>()) { accumulatedUsers, newUserState ->
                    accumulatedUsers + newUserState
                }
                .map { setOfOtherUsers ->
                    FriendsUiState(name = currentUserName, users = setOfOtherUsers)
                }
        }.stateIn(
            viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = FriendsUiState()
        )

    fun saveCurrentUserName(newName: String) = viewModelScope.launch(Dispatchers.IO) {
        val currentUser = userRepo.getCurrentUser() ?: userRepo.createCurrentUser(newName)
        val profile = currentUser.profile
        val profileWithNewName = profile.copy(name = newName)
        val userWithNewName = currentUser.copy(profile = profileWithNewName)
        userRepo.saveCurrentUser(userWithNewName)
    }

    fun toggleFriend(newFriend: UserState) = viewModelScope.launch(Dispatchers.IO) {
        val friendId = newFriend.id
        val currentUser = userRepo.getCurrentUser() ?: return@launch
        val friends = currentUser.friends.toMutableList()

        val oldFriend = friends.firstOrNull { it.friendId == friendId }

        if (oldFriend != null) {
            friends.remove(oldFriend)
        } else {
            friends.add(
                Friend(
                    friendId = newFriend.id,
                    name = newFriend.name,
                    profileParentId = currentUser.profile.profileId
                )
            )
        }

        val currentUserWithFriend = currentUser.copy(
            friends = friends
        )

        userRepo.saveCurrentUser(currentUserWithFriend)
    }
}
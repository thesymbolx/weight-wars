package uk.co.weightwars.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.collections.forEach


@Composable
fun FriendsScreen(friendsViewModel: FriendsViewModel = hiltViewModel()) {
    val uiState by friendsViewModel.uiState.collectAsStateWithLifecycle()

    Column {
        CurrentUserName(uiState.name, friendsViewModel::saveCurrentUserName)

        Spacer(modifier = Modifier.height(50.dp))

        FriendsList(uiState.users) { friendId ->
            friendsViewModel.toggleFriend(friendId)
        }
    }
}

@Composable
private fun CurrentUserName(
    name: String,
    onSaveUserName: (String) -> Unit
) {
    var userName by remember(name) {
        mutableStateOf(name)
    }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Enter your name") }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = userName.isNotEmpty(),
            onClick = { onSaveUserName(userName) }
        ) {
            Text("Save")
        }
    }
}

@Composable
private fun FriendsList(
    friends: Set<UserState>,
    onFriendToggle: (UserState) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.add_friends),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            if (friends.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_friends_available),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(items = friends.toList(), key = { it.id }) { friend ->
            FriendCard(
                friend = friend,
                isSelected = friend.isSelected,
                onToggle = {
                    onFriendToggle(friend)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FriendCard(
    friend: UserState,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (isSelected) {
                Color(0xFFFFE082)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (isSelected) Color(0xFF3E2723) else MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = friend.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 12.dp),
                color = if (isSelected) Color(0xFF3E2723) else MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = isSelected,
                onCheckedChange = { onToggle() },
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFF57F17), // Darker gold for thumb
                    checkedTrackColor = Color(0xFFFFB74D), // Medium gold for track
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

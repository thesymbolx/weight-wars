package uk.co.weightwars.friends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.weightwars.network.model.NetworkUser
import kotlin.collections.forEach


@Composable
fun FriendsScreen(friendsViewModel: FriendsViewModel = hiltViewModel()) {
    val uiState by friendsViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        friendsViewModel.init()
    }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.name,
            onValueChange = { friendsViewModel.onNameChange(it) },
            label = { Text("Enter your name") }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = uiState.name.isNotEmpty(),
            onClick = { friendsViewModel.saveUser() }
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(50.dp))

        FriendsList(uiState.users) { friendId ->
            friendsViewModel.toggleFriend(friendId)
        }
    }
}

@Composable
private fun FriendsList(
    friends: Set<UserState>,
    onFriendToggle: (Long) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_friends),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (friends.isEmpty()) {
            Text(
                text = stringResource(R.string.no_friends_available),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            friends.forEach { friend ->
                FriendCard(
                    friend = friend,
                    isSelected = friend.isSelected,
                    onToggle = { 
                        onFriendToggle(friend.id)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
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
                Color(0xFFFFE082) // Deeper gold color
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

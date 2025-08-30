package uk.co.weightwars.challenges.creation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.weightwars.challenges.R
import uk.co.weightwars.designsystem.WeightWarsTheme
import uk.co.weightwars.ui.parallaxLayoutModifier


@Composable
fun ChallengeCreationScreen(
    challenge: Int?,
    viewModel: ChallengeCreationViewModel = hiltViewModel(),
    addChallengeClick: () -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(challenge) {
        if(challenge != null) {
            viewModel.addChallenge(challenge)
        }
    }

    LaunchedEffect(uiState.challengeSaved) {
        if(uiState.challengeSaved) {
            viewModel.clearSavedEvent()
            onBack()
        }
    }

    ChallengeCreationScreen(
        challengeCreationUiState = uiState,
        addChallengeClick = addChallengeClick,
        onSave = viewModel::saveActiveChallenge,
        onBack = onBack,
        onFriendToggle = viewModel::toggleFriend
    )
}

@Composable
fun ChallengeCreationScreen(
    challengeCreationUiState: ChallengeCreationUiState,
    addChallengeClick: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onFriendToggle: (FriendState) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Header(scrollState = scrollState, onBack = onBack)

            Spacer(modifier = Modifier.height(32.dp))

            // Improved Add Challenge Button
            Button(
                onClick = addChallengeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(R.string.add_challenge),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            challengeCreationUiState.selectedChallengeState.forEach {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "${it.title}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            FriendsList(
                friends = challengeCreationUiState.friends,
                onFriendToggle = onFriendToggle
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = onSave,
            enabled = challengeCreationUiState.saveEnabled
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(R.string.save),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun Header(
    scrollState: ScrollState,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.parallaxLayoutModifier(scrollState, 2)) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.create_challenge),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
private fun FriendsList(
    friends: List<FriendState>,
    onFriendToggle: (FriendState) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            Text(
                text = stringResource(R.string.invite_friends),
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

        items(items = friends, key = { it.id }) { friend ->
            FriendCard(
                friend = friend,
                isSelected = friend.isSelected,
                onToggle = onFriendToggle
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FriendCard(
    friend: FriendState,
    isSelected: Boolean,
    onToggle: (FriendState) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onToggle(friend)
            }),
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
                onCheckedChange = { onToggle(friend) },
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFF57F17),
                    checkedTrackColor = Color(0xFFFFB74D),
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview(backgroundColor = 0xFF212121, showBackground = true)
@Composable
private fun ChallengeCreationScreenPreview() {
    WeightWarsTheme {
        ChallengeCreationScreen(
            challengeCreationUiState = ChallengeCreationUiState(),
            addChallengeClick = {},
            onSave = {},
            onBack = {},
            onFriendToggle = {}
        )
    }

}
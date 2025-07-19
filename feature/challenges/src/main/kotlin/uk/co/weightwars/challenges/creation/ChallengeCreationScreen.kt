package uk.co.weightwars.challenges.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ChallengeCreationScreen(
    challenge: Long?,
    viewModel: ChallengeCreationViewModel = hiltViewModel(),
    addChallengeClick: () -> Unit,
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState

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
        onSave = viewModel::saveActiveChallenge
    )
}

@Composable
fun ChallengeCreationScreen(
    challengeCreationUiState: ChallengeCreationUiState,
    addChallengeClick: () -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = addChallengeClick)
        ) {
            AddChallenge(addChallengeClick)

            challengeCreationUiState.selectedChallengeState.forEach {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "${it.title}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = onSave
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
private fun AddChallenge(
    addChallengeClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = addChallengeClick)
    ) {
        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Add Challenge",
                style = MaterialTheme.typography.titleLarge
            )
        }

        HorizontalDivider()
    }
}
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.weightwars.challenges.R
import uk.co.weightwars.designsystem.WeightWarsTheme
import uk.co.weightwars.ui.parallaxLayoutModifier


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
        onSave = viewModel::saveActiveChallenge,
        onBack = onBack
    )
}

@Composable
fun ChallengeCreationScreen(
    challengeCreationUiState: ChallengeCreationUiState,
    addChallengeClick: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Header(scrollState = scrollState, onBack = onBack)

            Spacer(modifier = Modifier.height(32.dp))

            AddChallenge(addChallengeClick)

            challengeCreationUiState.selectedChallengeState.forEach {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "${it.title}",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = onSave
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
private fun AddChallenge(
    addChallengeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = addChallengeClick)
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
                style = MaterialTheme.typography.headlineSmall
            )
        }

        HorizontalDivider()
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
            onBack = {}
        )
    }

}
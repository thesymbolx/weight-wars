package uk.co.weightwars.overview.activeChallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ActiveChallengeScreen(
    id: Int,
    viewModel: ActiveChallengeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getChallenge(id)
    }

    ActiveChallengeScreen(
        activeChallengeState = viewModel.uiState,
        onDelete = {
            viewModel.deleteChallenge()
            onBack()
        }
    )
}

@Composable
fun ActiveChallengeScreen(
    activeChallengeState: ActiveChallengeState,
    onDelete: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(activeChallengeState.name)

        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}
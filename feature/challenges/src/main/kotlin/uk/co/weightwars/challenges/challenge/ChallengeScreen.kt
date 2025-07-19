package uk.co.weightwars.challenges.challenge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import uk.co.weightwars.challenges.R
import uk.co.weightwars.database.entities.Challenge

@Composable
fun ChallengeScreen(
    categoryId: Long,
    viewModel: ChallengeViewModel = hiltViewModel(),
    onChallengeClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.uiState

    LifecycleResumeEffect(Unit) {
        viewModel.getChallenges(categoryId)
        onPauseOrDispose {}
    }

    ChallengeScreen(
        state, {
            onChallengeClick(it.challengeId)
        },
        onBack
    )
}

@Composable
private fun ChallengeScreen(
    challengeUiState: ChallengeUiState,
    onChallengeClick: (Challenge) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.choose_challenge),
                style = MaterialTheme.typography.displaySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        challengeUiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clickable(true) {
                        onChallengeClick(it)
                    }
            ) {
                Text(it.title)
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
        }
    }
}
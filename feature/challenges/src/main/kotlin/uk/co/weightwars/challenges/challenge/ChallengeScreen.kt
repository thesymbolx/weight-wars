package uk.co.weightwars.challenges.challenge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect

@Composable
fun ChallengeScreen(
    viewModel: ChallengeViewModel = hiltViewModel(),
    onChallengeClick: (Int) -> Unit
) {
    val state = viewModel.uiState

    LifecycleResumeEffect(Unit) {
        viewModel.getChallenges()
        onPauseOrDispose {}
    }

    ChallengeScreen(state, {
        viewModel.saveChallenge(it)
        onChallengeClick(it)
    })
}

@Composable
private fun ChallengeScreen(
    challengeUiState: ChallengeUiState,
    onChallengeClick: (Int) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        challengeUiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clickable(true) {
                        onChallengeClick(it.id)
                    }
            ) {
                Text(it.title)
            }
            Spacer(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth())
        }
    }
}
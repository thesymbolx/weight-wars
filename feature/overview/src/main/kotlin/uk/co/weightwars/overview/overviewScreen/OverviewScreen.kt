package uk.co.weightwars.overview.overviewScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel(),
    onChallengeClick: (Int) -> Unit
) {
    LifecycleResumeEffect(Unit) {
        viewModel.getChallenges()
        onPauseOrDispose {}
    }

    OverviewScreen(viewModel.uiState, onChallengeClick)
}

@Composable
private fun OverviewScreen(
    uiState: OverviewUiState,
    onChallengeClick: (Int) -> Unit
) {
    Column {
        uiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onChallengeClick(it.id) }
            ) {
                Text(it.title)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
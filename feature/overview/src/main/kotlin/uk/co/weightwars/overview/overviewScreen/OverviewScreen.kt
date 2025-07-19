package uk.co.weightwars.overview.overviewScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel(),
    onChallengeClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OverviewScreen(uiState, onChallengeClick)
}

@Composable
private fun OverviewScreen(
    uiState: OverviewUiState,
    onChallengeClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(uiState.challenges.isEmpty())
            Text(
                text = "No Challenges set",
                style = MaterialTheme.typography.titleLarge
            )

        uiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onChallengeClick(it.challengeInfo.challengeInfoId) }
            ) {
                Text(text = it.challengeInfo.title, style = MaterialTheme.typography.headlineMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
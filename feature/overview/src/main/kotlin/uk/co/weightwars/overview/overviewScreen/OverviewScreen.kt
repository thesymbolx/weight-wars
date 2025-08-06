package uk.co.weightwars.overview.overviewScreen

import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.weightwars.overview.R
import uk.co.weightwars.ui.parallaxLayoutModifier

@Composable
fun OverviewScreen(
    viewModel: OverviewViewModel = hiltViewModel(),
    onChallengeClick: (String) -> Unit,
    onBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    OverviewScreen(uiState, onChallengeClick, onBack)
}

@Composable
private fun OverviewScreen(
    uiState: OverviewUiState,
    onChallengeClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState),){
        Header(scrollState, onBack)

        Spacer(modifier = Modifier.height(16.dp))

        if(uiState.challenges.isEmpty())
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.no_challenges_set),
                style = MaterialTheme.typography.titleLarge
            )

        uiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onChallengeClick(it.challengeInfoEntity.challengeInfoId) }
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = it.challengeInfoEntity.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
            text = stringResource(R.string.active_challenges),
            style = MaterialTheme.typography.displaySmall
        )
    }
}
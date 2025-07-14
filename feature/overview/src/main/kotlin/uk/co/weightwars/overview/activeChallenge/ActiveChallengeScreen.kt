package uk.co.weightwars.overview.activeChallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate

@Composable
fun ActiveChallengeScreen(
    viewModel: ActiveChallengeViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ActiveChallengeScreen(
        activeChallengeState = uiState,
        onDelete = {
            viewModel.deleteChallenge()
            onBack()
        },
        onDayClick = viewModel::score
    )
}

@Composable
fun ActiveChallengeScreen(
    activeChallengeState: ActiveChallengeState,
    onDelete: () -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Text(text = activeChallengeState.name)
            }

            item {
                Text(text = activeChallengeState.totalScore)
            }

            scoringRow(activeChallengeState.scoringDate, onDayClick)
        }
    }
}

private fun LazyListScope.scoringRow(
    scoringDateState: List<ScoringDateState>,
    onScoreClick: (LocalDate) -> Unit
) = items(items = scoringDateState, key = { it.consecutiveDay.date }) { scoringDateState ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "${scoringDateState.consecutiveDay.date} ${scoringDateState.consecutiveDay.dayName}"
        )

        IconButton(
            onClick = { onScoreClick(scoringDateState.consecutiveDay.localDate) }
        ) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = null)
        }

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
        }

        Text(text = "${scoringDateState.score}")
    }
}
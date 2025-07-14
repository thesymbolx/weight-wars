package uk.co.weightwars.overview.activeChallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.weightwars.domain.ConsecutiveDay
import java.time.LocalDate

@Composable
fun ActiveChallengeScreen(
    id: Int,
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
        onDayClick = viewModel::onDayClick
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

            scoringRow(activeChallengeState.dayState, onDayClick)

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDelete
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

private fun LazyListScope.scoringRow(
    consecutiveDay: List<DayState>,
    onDayClick: (LocalDate) -> Unit
) = items(items = consecutiveDay, key = { it.consecutiveDay.date }) { day ->
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(color = if (day.isSelected) Color.Green else Color.Red)
            .clickable { onDayClick(day.consecutiveDay.localDate) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "${day.consecutiveDay.date} ${day.consecutiveDay.dayName}"
        )

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Done, contentDescription = null)
        }

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
        }

        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
        }
    }
}
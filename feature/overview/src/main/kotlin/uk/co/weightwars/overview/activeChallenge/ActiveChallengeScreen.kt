package uk.co.weightwars.overview.activeChallenge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.co.weightwars.domain.ConsecutiveDay
import java.time.LocalDate

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
        Column(modifier = Modifier.weight(1f)) {
            Text(text = activeChallengeState.name)
            Spacer(modifier = Modifier.height(30.dp))
            Days(activeChallengeState.dayState, onDayClick)
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDelete) {
            Text("Delete")
        }
    }
}

@Composable
private fun Days(
    consecutiveDay: List<DayState>,
    onDayClick: (LocalDate) -> Unit
) {
    LazyColumn {
        items(items = consecutiveDay, key = {it.consecutiveDay.date}) { day ->
            Column(modifier = Modifier
                .background(color = if(day.isSelected) Color.Green else Color.Red)
                .clickable { onDayClick(day.consecutiveDay.localDate) }
            ) {
                Text("${day.consecutiveDay.date} ${day.consecutiveDay.dayName}")
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
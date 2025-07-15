package uk.co.weightwars.overview.activeChallenge

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate

@Composable
fun ActiveChallengeScreen(
    viewModel: ActiveChallengeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ActiveChallengeScreen(
        activeChallengeState = uiState,
        onDayClick = viewModel::score
    )
}

@Composable
fun ActiveChallengeScreen(
    activeChallengeState: ActiveChallengeState,
    onDayClick: (LocalDate) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ChallengeCard(activeChallengeState, onDayClick)
    }
}

@Composable
private fun ChallengeCard(
    activeChallengeState: ActiveChallengeState,
    onScoreClick: (LocalDate) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
    ) {
        Column {
            Row {
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                ) {
                    Text(text = activeChallengeState.name)

                    Text(text = "Total points: ${activeChallengeState.totalScore}")
                }

                IconButton(
                    onClick = {  expanded = !expanded }
                ) {
                    Icon(imageVector =
                        if(expanded)Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            if (expanded) {
                activeChallengeState.scoringDate.forEach {
                    HorizontalDivider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "${it.consecutiveDay.date} ${it.consecutiveDay.dayName}"
                        )

                        IconButton(
                            onClick = { onScoreClick(it.consecutiveDay.localDate) }
                        ) {
                            Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                        }

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "${it.score}"
                        )
                    }
                }
            }

        }
    }
}
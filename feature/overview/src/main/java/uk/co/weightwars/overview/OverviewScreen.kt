package uk.co.weightwars.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import java.time.LocalDate

@Composable
fun OverviewScreen(viewModel: OverviewViewModel = hiltViewModel()) {


    LifecycleResumeEffect(Unit) {
        viewModel.getChallenges()

        onPauseOrDispose {
            // do any needed clean up here
        }
    }


    OverviewScreen(viewModel.uiState)
}

@Composable
private fun OverviewScreen(uiState: OverviewUiState) {
    Column {
        uiState.challenges.forEach {
            Card(
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) {
                Text(it.title)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
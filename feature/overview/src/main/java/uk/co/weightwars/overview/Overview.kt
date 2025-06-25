package uk.co.weightwars.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import java.time.LocalDate

@Composable
fun OverviewScreen(viewModel: OverviewViewModel = hiltViewModel()) {

    LifecycleResumeEffect(Unit) {
        viewModel.getCategories()

        onPauseOrDispose {
            // do any needed clean up here
        }
    }


    OverviewScreen()
}

@Composable
private fun OverviewScreen(uiState: OverviewUiState) {
    val now = remember { mutableStateOf(LocalDate.now()) }
    val days = mutableListOf<LocalDate>()



    Column {
        Button(onClick = {

            for(x in 0..3) {
                val date = LocalDate.now().plusDays(x.toLong() + 1)
                days.add(date)
            }
        }) {
            Text("do somethign")
        }
    }
}
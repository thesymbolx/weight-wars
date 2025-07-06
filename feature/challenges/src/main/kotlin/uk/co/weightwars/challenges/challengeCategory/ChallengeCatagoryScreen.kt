package uk.co.weightwars.challenges.challengeCategory

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

@Composable
fun ChallengeCategoryScreen(
    viewModel: ChallengeCategoryViewModel = hiltViewModel(),
    onCategoryClick: (Int) -> Unit
) {
    val state = viewModel.uiState

    LifecycleResumeEffect(Unit) {
        viewModel.getChallengeCategories()

        onPauseOrDispose {
            // do any needed clean up here
        }
    }

    ChallengeCategoryScreen(state, onCategoryClick)
}

@Composable
private fun ChallengeCategoryScreen(
    challengeUiState: ChallengeCategoryUiState,
    onCategoryClick: (Int) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        challengeUiState.categories.forEach {
            Card(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clickable(true) {
                    onCategoryClick(it.id)
                }
            ) {
                Text(it.title)
            }
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())
        }
    }
}
package uk.co.weightwars.challenges.challengeCategory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect

@Composable
fun ChallengeCategoryScreen(
    viewModel: ChallengeCategoryViewModel = hiltViewModel(),
    onCategoryClick: (Long) -> Unit
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
    onCategoryClick: (Long) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        challengeUiState.categories.forEach {
            Card(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable(true) {
                    onCategoryClick(it.categoryId)
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }
            Spacer(modifier = Modifier.height(20.dp).fillMaxWidth())
        }
    }
}
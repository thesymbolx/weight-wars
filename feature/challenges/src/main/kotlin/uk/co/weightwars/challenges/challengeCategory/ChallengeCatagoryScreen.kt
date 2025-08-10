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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uk.co.weightwars.challenges.R

@Composable
fun ChallengeCategoryScreen(
    viewModel: ChallengeCategoryViewModel = hiltViewModel(),
    onCategoryClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChallengeCategoryScreen(
        challengeUiState = state,
        onCategoryClick = onCategoryClick,
        onBack = onBack
    )
}

@Composable
private fun ChallengeCategoryScreen(
    challengeUiState: ChallengeCategoryUiState,
    onCategoryClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.choose_category),
                style = MaterialTheme.typography.displaySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

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
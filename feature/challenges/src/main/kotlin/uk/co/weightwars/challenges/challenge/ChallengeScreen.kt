package uk.co.weightwars.challenges.challenge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import uk.co.weightwars.challenges.R
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.designsystem.WeightWarsTheme
import uk.co.weightwars.ui.parallaxLayoutModifier

@Composable
fun ChallengeScreen(
    categoryId: Long,
    viewModel: ChallengeViewModel = hiltViewModel(),
    onChallengeClick: (Long) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.uiState

    LifecycleResumeEffect(Unit) {
        viewModel.getChallenges(categoryId)
        onPauseOrDispose {}
    }

    ChallengeScreen(
        state, {
            onChallengeClick(it.challengeId)
        },
        onBack
    )
}

@Composable
private fun ChallengeScreen(
    challengeUiState: ChallengeUiState,
    onChallengeClick: (Challenge) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Column(
            modifier = Modifier.parallaxLayoutModifier(scrollState, 2)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }

            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.choose_challenge),
                style = MaterialTheme.typography.displaySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        challengeUiState.challenges.forEach {
            Card(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clickable(true) {
                        onChallengeClick(it)
                    }
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text(
                        text = it.title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    BasicText(
                        text = it.description,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 10.sp,
                            maxFontSize = MaterialTheme.typography.bodySmall.fontSize
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(backgroundColor = 0xFF212121, showBackground = true)
@Composable
private fun ChallengeScreenPreview() {
    WeightWarsTheme {
        ChallengeScreen(
            challengeUiState = ChallengeUiState(
                challenges = listOf(
                    Challenge(
                        challengeId = 1,
                        title = "Challenge 1",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                        days = 10,
                        categoryId = 1
                    ),
                    Challenge(
                        challengeId = 2,
                        title = "Challenge 2",
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                        days = 10,
                        categoryId = 1
                    )
                ),
            ),
            onChallengeClick = {},
            onBack = {}
        )
    }
}

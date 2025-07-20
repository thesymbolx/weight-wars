package uk.co.weightwars.overview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.co.weightwars.overview.activeChallenge.ActiveChallengeScreen
import uk.co.weightwars.overview.overviewScreen.OverviewScreen

@Serializable
object OverviewRoute

@Serializable
data class ActiveChallengeRoute(val activeChallengeId: Long)

fun NavGraphBuilder.overviewNavGraph(navController: NavHostController) {
    composable<OverviewRoute> {
        OverviewScreen(
            onChallengeClick = { navController.navigate(ActiveChallengeRoute(it)) },
            onBack = { navController.navigateUp() }
        )
    }

    composable<ActiveChallengeRoute> {
        ActiveChallengeScreen(onBack = { navController.navigateUp() })
    }
}
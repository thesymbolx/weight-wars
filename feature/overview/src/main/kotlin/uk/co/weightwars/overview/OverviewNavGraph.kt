package uk.co.weightwars.overview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import uk.co.weightwars.overview.activeChallenge.ActiveChallengeScreen
import uk.co.weightwars.overview.overviewScreen.OverviewScreen

@Serializable
object OverviewRoute

@Serializable
data class ActiveChallengeRoute(val activeChallengeId: Int)

fun NavGraphBuilder.overviewNavGraph(navController: NavHostController) {
    composable<OverviewRoute> {
        OverviewScreen {
            navController.navigate(ActiveChallengeRoute(it))
        }
    }

    composable<ActiveChallengeRoute> { navBackStack ->
        val route = navBackStack.toRoute<ActiveChallengeRoute>()
        ActiveChallengeScreen(route.activeChallengeId) {
            navController.navigateUp()
        }
    }
}
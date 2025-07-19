package uk.co.weightwars

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import uk.co.weightwars.challenges.challengeNavGraph
import uk.co.weightwars.overview.OverviewRoute
import uk.co.weightwars.overview.overviewNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    appState: AppState
) =
    NavHost(
        navController = navController,
        startDestination = OverviewRoute,
        modifier = Modifier.fillMaxSize()
    ) {
        overviewNavGraph(navController)
        challengeNavGraph(navController) {
            appState.navigateToTopLevelDestination(TOP_LEVEL_ROUTES[0])
        }
    }



package uk.co.weightwars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import uk.co.weightwars.challenges.ChallengeNavGraphRoute
import uk.co.weightwars.overview.OverviewRoute

@Composable
fun rememberAppState(navController: NavHostController): AppState =
    remember(navController) {
        AppState(navController = navController)
    }

@Stable
class AppState(
    private val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelRoute<out Any>) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination.route) {
            is OverviewRoute -> navController.navigate(OverviewRoute, topLevelNavOptions)
            is ChallengeNavGraphRoute -> navController.navigate(
                ChallengeNavGraphRoute,
                topLevelNavOptions
            )
        }
    }
}

data class TopLevelRoute<T : Any>(val route: T, val icon: ImageVector)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = OverviewRoute, icon = Icons.Filled.Home),
    TopLevelRoute(route = ChallengeNavGraphRoute, icon = Icons.Filled.Search)
)
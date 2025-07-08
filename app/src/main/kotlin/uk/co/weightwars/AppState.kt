package uk.co.weightwars

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import uk.co.weightwars.challenges.ChallengeCategoryRoute
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
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination.route) {
            is OverviewRoute -> navController.navigate(OverviewRoute, topLevelNavOptions)
            is ChallengeCategoryRoute -> navController.navigate(
                ChallengeCategoryRoute,
                topLevelNavOptions
            )
        }
    }
}

data class TopLevelRoute<T : Any>(val route: T, val icon: Int)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = OverviewRoute, icon = R.drawable.ic_launcher_foreground),
    TopLevelRoute(route = ChallengeCategoryRoute, icon = R.drawable.ic_launcher_foreground)
)
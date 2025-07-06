package uk.co.weightwars

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import uk.co.weightwars.challenges.ChallengeCategoryRoute
import uk.co.weightwars.ui.theme.WeightWarsTheme
import uk.co.weightwars.challenges.challengeNavGraph
import uk.co.weightwars.database.dao.ChallengeCategoryDao
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.overview.OverviewScreen
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var challengeCategoryDao: ChallengeCategoryDao
    @Inject
    lateinit var activeChallengeDao: ChallengeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeightWarsTheme {
                App()
            }
        }

       // populateCatDatabase()
       // populateChallengeList()
    }

    fun populateCatDatabase() {
        lifecycleScope.launch {
            challengeCategoryDao.insertAll(
                listOf(
                    ChallengeCategory(
                        id = 1,
                        title = "Cold Turkey"
                    )
                )
            )
        }
    }

    fun populateChallengeList() {
        lifecycleScope.launch {
            activeChallengeDao.insertAll(
                listOf(
                    Challenge(
                        id = 1,
                        title = "No Sugar 3 days",
                        hasHardCoreMode = true
                    ),
                    Challenge(
                        id = 2,
                        title = "No Sugar 7 days",
                        hasHardCoreMode = true
                    ),
                    Challenge(
                        id = 3,
                        title = "No Sugar 30 days",
                        hasHardCoreMode = true
                    )
                )
            )
        }
    }
}

@Serializable
data object RouteA

@Composable
fun App() {
    val navController = rememberNavController()
    val appState = rememberNiaAppState(navController)

    NasaAppBackground(modifier = Modifier) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NasaBottomNavigation(appState)
            },
            contentWindowInsets = WindowInsets.safeContent
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                NavHost(
                    navController = navController,
                    startDestination = RouteA,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable<RouteA> {
                        OverviewScreen()
                    }

                    challengeNavGraph(navController)

                }
            }
        }
    }
}

@Composable
fun NasaAppBackground(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}


@Composable
fun rememberNiaAppState(
    navController: NavHostController = rememberNavController(),
): NasaAppState {
    return remember(navController) {
        NasaAppState(
            navController = navController,
        )
    }
}

@Stable
class NasaAppState(
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
            is RouteA -> navController.navigate(RouteA, topLevelNavOptions)
            is ChallengeCategoryRoute -> navController.navigate(ChallengeCategoryRoute, topLevelNavOptions)
        }
    }
}

data class TopLevelRoute<T : Any>(val route: T, val icon: Int)

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(route = RouteA, icon = R.drawable.ic_launcher_background),
    TopLevelRoute(route = ChallengeCategoryRoute, icon = R.drawable.ic_launcher_foreground)
)

@SuppressLint("RestrictedApi")
@Composable
fun NasaBottomNavigation(
    appState: NasaAppState
) {
    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified
                ),
                selected = appState.currentDestination?.hierarchy?.any {
                    it.hasRoute(topLevelRoute.route::class)
                } == true,
                onClick = { appState.navigateToTopLevelDestination(topLevelRoute) },
                icon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(topLevelRoute.icon),
                        contentDescription = null
                    )
                },
            )
        }
    }
}

package uk.co.weightwars

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uk.co.weightwars.data.repository.ChallengeRepo
import uk.co.weightwars.data.repository.UserRepo
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeWithCategory
import uk.co.weightwars.designsystem.WeightWarsTheme
import uk.co.weightwars.user.UserScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var challengeRepo: ChallengeRepo

    @Inject
    lateinit var activeChallengeDao: ChallengeDao

    @Inject
    lateinit var userRepo: UserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val hasCurrentUser = userRepo.getCurrentUser()

            enableEdgeToEdge()
            setContent {
                App(hasCurrentUser != null)
            }
        }
    }
}

    @Composable
    fun App(currentUserSet: Boolean) {
        val navController = rememberNavController()
        val appState = rememberAppState(navController)
        var currentUserSet by remember {
            mutableStateOf(currentUserSet)
        }

        WeightWarsTheme {
            AppBackground(modifier = Modifier) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NasaBottomNavigation(appState)
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (currentUserSet) {
                            AppNavHost(navController, appState)
                        } else {
                            UserScreen {
                                currentUserSet = true
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun AppBackground(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) = Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }

    @SuppressLint("RestrictedApi")
    @Composable
    fun NasaBottomNavigation(appState: AppState) = NavigationBar(
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
                        imageVector = topLevelRoute.icon,
                        contentDescription = null
                    )
                },
            )
        }
    }


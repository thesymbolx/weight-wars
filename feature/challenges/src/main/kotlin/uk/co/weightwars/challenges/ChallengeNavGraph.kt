package uk.co.weightwars.challenges

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel // Added import
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import uk.co.weightwars.challenges.challenge.ChallengeScreen
import uk.co.weightwars.challenges.challengeCategory.ChallengeCategoryScreen
import uk.co.weightwars.challenges.creation.ChallengeCreationScreen

@Serializable
object ChallengeCategoryRoute

@Serializable
data class ChallengeRoute(val categoryId: Long)

@Serializable
object ChallengeCreationRoute

@Serializable
object ChallengeNavGraphRoute

fun NavGraphBuilder.challengeNavGraph(
    navController: NavHostController,
    activeChallengeSaved: () -> Unit
) =
    navigation<ChallengeNavGraphRoute>(startDestination = ChallengeCreationRoute) {
        composable<ChallengeCategoryRoute> {
            ChallengeCategoryScreen { categoryId ->
                navController.navigate(ChallengeRoute(categoryId))
            }
        }

        composable<ChallengeRoute> { entry ->
            val parentEntry = navController.getBackStackEntry<ChallengeNavGraphRoute>()
            val sharedViewModel: SharedChallengeViewModel = hiltViewModel(parentEntry)
            val route = entry.toRoute<ChallengeRoute>()

            ChallengeScreen(
                categoryId = route.categoryId
            ) {
                sharedViewModel.setChallenge(it)
                navController.popBackStack(ChallengeCreationRoute, false)
            }
        }

        composable<ChallengeCreationRoute> { entry ->
            val parentEntry = remember(entry) { navController.getBackStackEntry<ChallengeNavGraphRoute>() }
            val sharedViewModel: SharedChallengeViewModel = hiltViewModel(parentEntry)
            val challengeId = sharedViewModel.getChallenge()

            ChallengeCreationScreen(
                challenge = challengeId,
                addChallengeClick = { navController.navigate(ChallengeCategoryRoute) },
                onBack = activeChallengeSaved
            )
        }
    }

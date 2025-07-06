package uk.co.weightwars.challenges

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import uk.co.weightwars.challenges.challenge.ChallengeScreen
import uk.co.weightwars.challenges.challengeCategory.ChallengeCategoryScreen

@Serializable
object ChallengeCategoryRoute

@Serializable
object ChallengeRoute

fun NavGraphBuilder.challengeNavGraph(navController: NavHostController) {
     composable<ChallengeCategoryRoute> {
         ChallengeCategoryScreen {
             navController.navigate(ChallengeRoute)
         }
     }

    composable<ChallengeRoute> {
        ChallengeScreen {
            navController.popBackStack()
            navController.popBackStack()
        }
    }
}
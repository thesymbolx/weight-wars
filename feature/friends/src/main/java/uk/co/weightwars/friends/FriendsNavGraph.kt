package uk.co.weightwars.friends

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object FriendsNavGraphRoute

@Serializable
object FriendsRoute

fun NavGraphBuilder.friendsNavGraph(
    navController: NavHostController
) = navigation<FriendsNavGraphRoute>(startDestination = FriendsRoute) {
    composable<FriendsRoute> {
        FriendsScreen()
    }
}
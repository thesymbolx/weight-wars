package uk.co.weightwars.overview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object OverviewRoute

fun NavGraphBuilder.overviewNavGraph(navController: NavHostController) {
    composable<OverviewRoute> {
        OverviewScreen()
    }
}
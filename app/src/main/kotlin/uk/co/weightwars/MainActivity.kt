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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uk.co.weightwars.data.ChallengeRepo
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeWithCategory
import uk.co.weightwars.designsystem.WeightWarsTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var challengeRepo: ChallengeRepo

    @Inject
    lateinit var activeChallengeDao: ChallengeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }

        populateCatDatabase()
    }

    fun populateCatDatabase() {



        listOf(
            ChallengeWithCategory(
                challengeCategory = ChallengeCategory(
                    categoryId = 1,
                    title = "Cold Turkey"
                ),
                challenges = listOf(
                    Challenge(
                        title = "No Sugar 7 Days",
                        description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                        days = 7,
                    ),
                    Challenge(
                        title = "No Sugar 30 Days",
                        description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                        days = 30,
                    ),
                    Challenge(
                        title = "No Takeaway 7 Days",
                        description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                        days = 7,
                    ),
                    Challenge(
                        title = "No Takeaway 30 Days",
                        description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                        days = 30,
                    ),
                    Challenge(
                        title = "No Junk Food 7 Days",
                        description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                        days = 30,
                    ),
                    Challenge(
                        title = "No Junk Food 30 Days",
                        description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                        days = 30,
                    ),
                    Challenge(
                        title = "No Gluten 7 Days",
                        description = "This is a description",
                        days = 30,
                    ),
                    Challenge(
                        title = "No Gluten 30 Days",
                        description = "This is a description",
                        days = 30,
                    )
                )
            ),
            ChallengeWithCategory(
                challengeCategory = ChallengeCategory(
                    categoryId = 2,
                    title = "Exercise"
                ),
                challenges = listOf(
                    Challenge(
                        title = "Any Exercise 7 Days",
                        description = "Any kind of exercise.",
                        days = 30,
                    ),
                    Challenge(
                        title = "Any Exercise 30 Days",
                        description = "Any kind of exercise",
                        days = 30,
                    ),
                    Challenge(
                        title = "Cardio 7 Days",
                        description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                        days = 30,
                    ),
                    Challenge(
                        title = "Cardio 30 Days",
                        description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                        days = 30,
                    ),
                    Challenge(
                        title = "Strength 7 Days",
                        description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                        days = 30,
                    ),
                    Challenge(
                        title = "Strength 30 Days",
                        description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                        days = 30,
                    ),
                )
            )
        ).forEach {
            lifecycleScope.launch {
                challengeRepo.setChallengeCategory(it)

            }

        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val appState = rememberAppState(navController)

    WeightWarsTheme {
        AppBackground(modifier = Modifier) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NasaBottomNavigation(appState)
                },
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavHost(navController, appState)
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
                    imageVector =topLevelRoute.icon,
                    contentDescription = null
                )
            },
        )
    }
}


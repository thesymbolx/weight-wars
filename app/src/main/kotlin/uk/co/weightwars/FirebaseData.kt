package uk.co.weightwars

import com.google.firebase.database.FirebaseDatabase
import uk.co.weightwars.database.entities.Challenge

fun initChallengeData() {
    val ref = FirebaseDatabase.getInstance().reference.child("challenges")
    ref.setValue(challengeData)
}

data class FirebaseChallengeCategory(
    val categoryId: Int,
    val title: String,
    val challenges: List<FirebaseChallenge>
)

data class FirebaseChallenge(
    val title: String,
    val description: String,
    val days: Int
)

val challengeData: List<FirebaseChallengeCategory> =
    listOf(
        FirebaseChallengeCategory(
            categoryId = 1,
            title = "Cold Turkey",
            challenges = listOf(
                FirebaseChallenge(
                    title = "No Sugar 7 Days",
                    description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                    days = 7,
                ),
                FirebaseChallenge(
                    title = "No Sugar 30 Days",
                    description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "No Takeaway 7 Days",
                    description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                    days = 7,
                ),
                FirebaseChallenge(
                    title = "No Takeaway 30 Days",
                    description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "No Junk Food 7 Days",
                    description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "No Junk Food 30 Days",
                    description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "No Gluten 7 Days",
                    description = "This is a description",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "No Gluten 30 Days",
                    description = "This is a description",
                    days = 30,
                )
            )
        ),
        FirebaseChallengeCategory(
            categoryId = 2,
            title = "Exercise",
            challenges = listOf(
                FirebaseChallenge(
                    title = "Any Exercise 7 Days",
                    description = "Any kind of exercise.",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "Any Exercise 30 Days",
                    description = "Any kind of exercise",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "Cardio 7 Days",
                    description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "Cardio 30 Days",
                    description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "Strength 7 Days",
                    description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                    days = 30,
                ),
                FirebaseChallenge(
                    title = "Strength 30 Days",
                    description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                    days = 30,
                )
            )
        )
    )
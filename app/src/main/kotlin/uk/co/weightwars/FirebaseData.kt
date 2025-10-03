package uk.co.weightwars

import com.google.firebase.database.FirebaseDatabase
import uk.co.weightwars.network.model.FirebaseChallenge
import uk.co.weightwars.network.model.FirebaseChallengeCategory
import uk.co.weightwars.network.model.FirebaseUser

fun initUserData() {
    val ref = FirebaseDatabase.getInstance().reference.child("users")
    users.forEach { user ->
        ref.child(user.id).setValue(user)
    }
}

val users: List<FirebaseUser> =
    listOf(
        FirebaseUser(
            id = "1",
            name = "John Doe",
            // Add other user properties here
        ),
        FirebaseUser(
            id = "2",
            name = "Jane Smith",
            // Add other user properties here
        ),
        FirebaseUser(
            id = "3",
            name = "Michael Johnson",
            // Add other user properties here
        ),
        FirebaseUser(
            id = "4",
            name = "Emily Davis",
            // Add other user properties here
        ),
        FirebaseUser(
            id = "5",
            name = "David Wilson",
            // Add other user properties here
        ),
        FirebaseUser(
            id = "6",
            name = "Sophia Martinez",
        )
    )


fun initChallengeData() {
    val ref = FirebaseDatabase.getInstance().reference.child("challenges")
    ref.setValue(challengeData)
}

val challengeData: List<FirebaseChallengeCategory> =
    listOf(
        FirebaseChallengeCategory(
            categoryId = 1,
            title = "Cold Turkey",
            challenges = listOf(
                FirebaseChallenge(
                    challengeId = 1,
                    title = "No Sugar 7 Days",
                    description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                    days = 7,
                ),
                FirebaseChallenge(
                    challengeId = 2,
                    title = "No Sugar 30 Days",
                    description = "Added sugars (white, brown, cane, syrup, honey, agave, etc.), sweetened drinks (soda, energy drinks, flavored coffee/tea, sugary juices), desserts (cakes, cookies, ice cream, candy, pastries), processed snacks (sweetened cereals, bars, flavored oatmeal), condiments with sugar (ketchup, BBQ sauce, dressings), packaged foods with hidden sugars (soups, ready meals, breads, crackers), flavored dairy (sweetened yogurt, creamers), artificial and zero-calorie sweeteners (aspartame, sucralose, stevia, monk fruit etc.).",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 3,
                    title = "No Takeaway 7 Days",
                    description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                    days = 7,
                ),
                FirebaseChallenge(
                    challengeId = 4,
                    title = "No Takeaway 30 Days",
                    description = "Pizza, burgers, fries, fried chicken, kebabs, Chinese food, Indian curry, sushi, tacos, burritos, sandwiches, wraps, fish and chips, noodle dishes, spring rolls, dumplings, shawarma, falafel, BBQ ribs, pasta dishes, meat pies, calzones, pita bread with fillings, quesadillas, samosas, gyro, hot dogs.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 5,
                    title = "No Junk Food 7 Days",
                    description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 6,
                    title = "No Junk Food 30 Days",
                    description = "Fast food (burgers, fries, fried chicken), takeaway food (pizza, kebabs, Chinese takeout, Indian takeout), chips, crisps, soda, energy drinks, sweetened teas, processed meats (sausages, bacon, deli meats), instant noodles, microwave meals, white bread, deep-fried foods, packaged snack foods (cheese puffs, flavored pretzels), sugary condiments (ketchup with additives, sweet sauces).",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 7,
                    title = "No Gluten 7 Days",
                    description = "This is a description",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 8,
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
                    challengeId = 10,
                    title = "Any Exercise 7 Days",
                    description = "Any kind of exercise.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 11,
                    title = "Any Exercise 30 Days",
                    description = "Any kind of exercise",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 12,
                    title = "Cardio 7 Days",
                    description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 13,
                    title = "Cardio 30 Days",
                    description = "Walking, jogging, running, cycling, hiking, swimming, skating, rollerblading, rowing, jump rope, treadmill, stationary bike, rowing machine, elliptical trainer, stair climber, SkiErg, assault bike, jumping jacks, high knees, mountain climbers, burpees, butt kicks, skaters, step-ups, jump squats, jump lunges, dancing, shadowboxing, marching in place, water aerobics, low-impact dance, chair cardio, Zumba, spin classes, kickboxing, aerobics classes, circuit training, soccer, basketball, tennis.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 14,
                    title = "Strength 7 Days",
                    description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 15,
                    title = "Strength 30 Days",
                    description = "Bodyweight squats, lunges, push-ups, pull-ups, planks, glute bridges, dips, step-ups, burpees, deadlifts, bench press, overhead press, bicep curls, tricep extensions, rows, chest flyes, kettlebell swings, dumbbell snatches, shoulder raises, leg presses, calf raises, Bulgarian split squats, hammer curls, hip thrusts, cable pull-downs, Russian twists, medicine ball slams, farmer’s carry, chin-ups, chest dips.",
                    days = 30,
                )
            )
        ),
        FirebaseChallengeCategory(
            categoryId = 3,
            title = "High-Intensity Exercises",
            challenges = listOf(
                FirebaseChallenge(
                    challengeId = 16,
                    title = "Burpees",
                    description = "A full-body movement starting from standing → drop into a push-up position → perform a push-up → jump explosively back up. Works chest, arms, legs, and lungs.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 17,
                    title = "Mountain Climbers",
                    description = "In a plank position, drive knees toward the chest in quick alternating motions like running on the floor. Targets core, shoulders, and cardio endurance.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 18,
                    title = "Jump Squats",
                    description = "Perform a squat and explode upward into a jump, landing softly back into a squat. Builds leg power and elevates heart rate.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 19,
                    title = "Push-Up to Shoulder Tap",
                    description = "Do a push-up, then tap opposite shoulders alternately. Adds a stability and core challenge to the chest/arms workout.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 20,
                    title = "High Knees",
                    description = "Running in place while driving knees up to waist level at max speed. Great cardio and core exercise.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 21,
                    title = "Plank Jacks",
                    description = "Start in plank, then jump feet apart and back together quickly (like a jumping jack). Works core and cardiovascular system.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 22,
                    title = "Broad Jumps",
                    description = "From a squat position, explode forward into a long jump. Land softly, reset, and repeat. Builds lower-body power.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 23,
                    title = "Tuck Jumps",
                    description = "Jump straight up while pulling knees to chest, then land softly. High-intensity plyometric move for quads, hamstrings, and glutes.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 24,
                    title = "Sprint Intervals",
                    description = "Short bursts of all-out sprinting (10–30 sec) followed by rest or light jogging. Excellent fat burner and heart strengthener.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 25,
                    title = "Hill Sprints",
                    description = "Sprinting uphill at maximum effort. Builds explosive leg power and cardio capacity.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 26,
                    title = "Jump Rope Double-Unders",
                    description = "Rope passes under feet twice in one jump. Requires speed, coordination, and cardiovascular endurance.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 27,
                    title = "Shuttle Runs (Suicides)",
                    description = "Sprinting back and forth between set distances, touching the ground each time. Builds speed, agility, and endurance.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 28,
                    title = "Cycling Sprints",
                    description = "On a stationary or road bike, alternate between all-out pedaling and recovery pace. Burns calories and strengthens legs.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 29,
                    title = "Box Jumps",
                    description = "Jump explosively onto a sturdy platform or box, then step down and repeat. Builds explosive leg strength and agility.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 30,
                    title = "Lateral Bounds (Skater Jumps)",
                    description = "Jump side-to-side like a speed skater, landing on one leg each time. Improves balance and lateral power.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 31,
                    title = "Clap Push-Ups",
                    description = "Perform a push-up explosively so hands leave the ground and clap before landing. Builds chest power and fast-twitch muscle fibers.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 32,
                    title = "Split Jump Lunges",
                    description = "Jump explosively to switch legs mid-air, landing in a lunge. Intense for quads, glutes, and balance.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 33,
                    title = "Plyometric Push-Ups to Box",
                    description = "Push explosively from the floor up onto an elevated surface (like a low box). Advanced upper-body plyometric drill.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 34,
                    title = "Kettlebell Swings",
                    description = "Swing a kettlebell from between legs to shoulder height using explosive hip drive. Builds posterior chain, grip, and conditioning.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 35,
                    title = "Dumbbell Thrusters",
                    description = "A front squat into an overhead press using dumbbells. Full-body strength + cardio demand.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 36,
                    title = "Man Makers",
                    description = "Combination move with dumbbells: push-up → row → squat → overhead press. One of the hardest compound exercises.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 37,
                    title = "Battle Rope Slams",
                    description = "Slam heavy ropes into the ground repeatedly with maximum force. Works shoulders, arms, core, and cardio.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 38,
                    title = "Sledgehammer Tire Slams",
                    description = "Swing a sledgehammer overhead and slam it into a tire. Explosive full-body exercise.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 39,
                    title = "Barbell Complexes",
                    description = "Performing multiple barbell lifts (deadlift → row → clean → press → squat) in sequence without resting. Builds strength and conditioning.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 40,
                    title = "Medicine Ball Slams",
                    description = "Lift a medicine ball overhead and slam it forcefully into the ground, catching on the rebound. Full-body explosive exercise.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 41,
                    title = "Bear Crawls",
                    description = "Crawl forward on hands and feet with hips low. Builds shoulder stability, core, and conditioning.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 42,
                    title = "Crab Walks",
                    description = "Sit with hands and feet on the floor, lift hips, and walk backward/forward. Works triceps, shoulders, and core.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 43,
                    title = "Farmer’s Carries",
                    description = "Walk carrying heavy weights in each hand. Builds grip, traps, and overall work capacity.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 44,
                    title = "Turkish Get-Ups",
                    description = "Complex movement with a kettlebell, moving from lying down to standing while holding the weight overhead. Improves strength, stability, and coordination.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 45,
                    title = "Sandbag Shouldering & Carries",
                    description = "Lift a heavy sandbag onto one shoulder, carry or squat with it. Great for functional strength and conditioning.",
                    days = 30,
                )
            )
        ),
        FirebaseChallengeCategory(
            categoryId = 4,
            title = "Interval Training Types",
            challenges = listOf(
                FirebaseChallenge(
                    challengeId = 46,
                    title = "Tabata Training",
                    description = "A form of high-intensity interval training with 20 seconds of all-out effort followed by 10 seconds of rest, repeated for 4 minutes (8 rounds). Improves aerobic and anaerobic capacity.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 47,
                    title = "30-30 Intervals",
                    description = "Alternating 30 seconds of high-intensity exercise with 30 seconds of rest or light activity. A balanced approach to build speed, endurance, and recovery ability.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 48,
                    title = "40-20 Intervals",
                    description = "Perform 40 seconds of hard effort followed by 20 seconds of recovery. Pushes endurance and anaerobic threshold with slightly longer work periods.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 49,
                    title = "1:1 Work-to-Rest",
                    description = "Work and rest times are equal (e.g., 60 seconds sprint, 60 seconds rest). Builds strong cardiovascular conditioning while allowing structured recovery.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 50,
                    title = "1:2 Work-to-Rest",
                    description = "Work time is half the rest time (e.g., 30 seconds max effort, 60 seconds recovery). Ideal for developing maximum power output and recovery ability.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 51,
                    title = "Sprint Interval Training (SIT)",
                    description = "Extremely short bursts (5–30 seconds) of all-out sprints followed by long recovery periods (1–4 minutes). Maximizes anaerobic capacity and calorie burn.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 52,
                    title = "Fartlek Training",
                    description = "Swedish for 'speed play.' A mix of continuous running with random bursts of high speed. Less structured than HIIT, improves both aerobic and anaerobic fitness.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 53,
                    title = "Pyramid Intervals",
                    description = "Interval times increase and then decrease (e.g., 30s, 60s, 90s, 60s, 30s). Challenges both endurance and recovery at varying intensities.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 54,
                    title = "Ladder Intervals",
                    description = "Work intervals gradually get longer each round (e.g., 20s, 40s, 60s, 80s), often paired with equal rest. Builds endurance and mental toughness.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 55,
                    title = "Reverse Ladder Intervals",
                    description = "Start with a long work period and decrease each round (e.g., 2 min, 90s, 1 min, 30s). Trains pacing and finishing with high intensity.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 56,
                    title = "EMOM (Every Minute On the Minute)",
                    description = "Perform a set number of reps or effort at the start of each minute, then rest for the remaining time. Great for strength, conditioning, and pacing.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 57,
                    title = "AMRAP (As Many Rounds As Possible)",
                    description = "Complete as many rounds or reps of a circuit as possible in a set time (e.g., 10 min). Builds muscular endurance and conditioning.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 58,
                    title = "High-Low Intervals",
                    description = "Alternate between a high-intensity movement (e.g., sprints) and a lower-intensity active recovery (e.g., jogging). Keeps heart rate up while managing fatigue.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 59,
                    title = "Threshold Training",
                    description = "Perform intervals at or just above lactate threshold pace with short rests. Improves endurance and the ability to sustain high-intensity work.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 60,
                    title = "VO₂ Max Intervals",
                    description = "High-intensity efforts lasting 2–6 minutes at near-maximal pace with equal or slightly shorter rests. Targets aerobic power and oxygen uptake.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 61,
                    title = "Norwegian 4x4 Intervals",
                    description = "A structured interval method of 4 rounds of 4 minutes at ~90–95% maximum heart rate, with 3 minutes of active recovery between rounds. Proven to boost aerobic fitness and endurance capacity.",
                    days = 30,
                )
            )
        ),
        FirebaseChallengeCategory(
            categoryId = 5,
            title = "Intermittent Fasting Types",
            challenges = listOf(
                FirebaseChallenge(
                    challengeId = 71,
                    title = "16:8 Method",
                    description = "Fast for 16 hours, eat within an 8-hour window (e.g., 12 pm–8 pm). One of the most popular and sustainable methods.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 72,
                    title = "14:10 Method",
                    description = "Fast for 14 hours, eat within a 10-hour window (e.g., 10 am–8 pm). Easier for beginners.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 62,
                    title = "18:6 Method",
                    description = "Fast for 18 hours, eat within a 6-hour window. Stricter, often used for weight loss or metabolic benefits.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 63,
                    title = "20:4 Method (Warrior Diet)",
                    description = "Fast for 20 hours, eat within a 4-hour window. Inspired by ancient warrior eating patterns.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 64,
                    title = "23:1 (OMAD)",
                    description = "Fast for 23 hours, eat one large meal in a 1-hour window. Very restrictive, often chosen for simplicity.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 65,
                    title = "12:12 Method",
                    description = "Fast for 12 hours, eat within 12 hours. Beginner-friendly and aligns with circadian rhythm.",
                    days = 30,
                ),

                // Alternate-Day Fasting
                FirebaseChallenge(
                    challengeId = 66,
                    title = "Full Alternate-Day Fasting",
                    description = "Eat normally one day, consume no calories the next. Repeated every other day.",
                    days = 2,
                ),
                FirebaseChallenge(
                    challengeId = 67,
                    title = "Modified ADF (5:2 Diet)",
                    description = "Eat normally 5 days per week, eat ~500–600 calories on 2 non-consecutive days.",
                    days = 7,
                ),
                FirebaseChallenge(
                    challengeId = 68,
                    title = "36-Hour Fast",
                    description = "Eat dinner, then don’t eat until breakfast two days later (~36 hours). Often done 1–2x weekly.",
                    days = 2,
                ),
                FirebaseChallenge(
                    challengeId = 69,
                    title = "Every-Other-Day OMAD",
                    description = "Alternate between regular eating and one meal per day (OMAD).",
                    days = 2,
                ),

                // Extended Fasting
                FirebaseChallenge(
                    challengeId = 70,
                    title = "48-Hour Fast",
                    description = "No food for 48 hours straight. Used for deeper autophagy and metabolic reset.",
                    days = 2,
                ),
                FirebaseChallenge(
                    challengeId = 71,
                    title = "72-Hour Fast",
                    description = "Consume only water, tea, or black coffee for 3 days. Deep ketosis and immune system benefits.",
                    days = 3,
                ),
                FirebaseChallenge(
                    challengeId = 72,
                    title = "Prolonged Fast (4–7 Days)",
                    description = "Extended fast under medical supervision. Used for therapeutic and longevity benefits.",
                    days = 5,
                ),
                FirebaseChallenge(
                    challengeId = 73,
                    title = "Fasting-Mimicking Diet (FMD)",
                    description = "Low-calorie (~700–1100/day), plant-based 5-day protocol designed to mimic fasting benefits.",
                    days = 5,
                ),

                // Cultural & Religious
                FirebaseChallenge(
                    challengeId = 74,
                    title = "Ramadan Fasting",
                    description = "No food or drink from sunrise to sunset, daily for a month. Nighttime eating window varies.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 75,
                    title = "Dry Fasting",
                    description = "No food and no water for a set period (e.g., 12–24 hours). Often religious or spiritual.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 76,
                    title = "Ayurvedic Fasting (Ekasana/Dwasana)",
                    description = "Eating only once (Ekasana) or twice (Dwasana) daily, often aligned with daylight cycles.",
                    days = 30,
                ),

                // Specialized & Flexible
                FirebaseChallenge(
                    challengeId = 77,
                    title = "Circadian Rhythm Fasting",
                    description = "Eat only during daylight hours (e.g., 8 am–6 pm) to align with body’s natural clock.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 78,
                    title = "Crescendo Fasting",
                    description = "Fast only on non-consecutive days (e.g., 3 days of 16:8 each week). Great for beginners.",
                    days = 3,
                ),
                FirebaseChallenge(
                    challengeId = 79,
                    title = "Spontaneous Meal Skipping",
                    description = "No strict schedule — simply skip meals when not hungry. Flexible and intuitive approach.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 80,
                    title = "4:3 Diet",
                    description = "Eat normally 4 days per week, restrict calories (~500–600) on 3 days. Variant of 5:2 diet.",
                    days = 7,
                ),
                FirebaseChallenge(
                    challengeId = 81,
                    title = "Up-Day Down-Day Diet",
                    description = "Alternate feast days (normal eating) with famine days (~500–600 calories).",
                    days = 2,
                ),
                FirebaseChallenge(
                    challengeId = 82,
                    title = "Reverse Fasting",
                    description = "Early eating window (e.g., 7 am–3 pm), then fasting until next morning. Supports sleep & digestion.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 83,
                    title = "One Weekend Fast",
                    description = "Eat normally on weekdays, fast for 24–36 hours on weekends. Simple structured approach.",
                    days = 2,
                ),
                FirebaseChallenge(
                    challengeId = 84,
                    title = "Alternate Meal Fasting",
                    description = "Instead of fasting whole days, skip every second meal. Less rigid but still reduces calories.",
                    days = 30,
                ),
                FirebaseChallenge(
                    challengeId = 85,
                    title = "Meal Skipping",
                    description = "A flexible fasting method where you skip meals (such as breakfast, lunch, or dinner) when not hungry or when convenient. Helps reduce calories without strict time windows.",
                    days = 30,
                )
            )
        )


    )
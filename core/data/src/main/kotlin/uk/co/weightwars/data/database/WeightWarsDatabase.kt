package uk.co.weightwars.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.weightwars.data.database.dao.ChallengeCategoryDao
import uk.co.weightwars.data.database.dao.ChallengeDao
import uk.co.weightwars.data.database.entities.Challenge
import uk.co.weightwars.data.database.entities.ChallengeCategory

@Database(entities = [Challenge::class, ChallengeCategory::class], version = 1)
abstract class WeightWarsDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
    abstract fun challengeCategoryDao(): ChallengeCategoryDao
}
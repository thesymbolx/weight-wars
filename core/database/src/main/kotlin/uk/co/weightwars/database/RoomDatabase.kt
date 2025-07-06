package uk.co.weightwars.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.ChallengeCategoryDao
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.entities.ActiveChallenge
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory

@Database(entities = [Challenge::class, ChallengeCategory::class, ActiveChallenge::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
    abstract fun challengeCategoryDao(): ChallengeCategoryDao
    abstract fun activeChallengeDao(): ActiveChallengeDao
}
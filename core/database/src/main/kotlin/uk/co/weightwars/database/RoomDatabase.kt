package uk.co.weightwars.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.ActiveChallengeItem
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeInfo
import uk.co.weightwars.database.entities.User

@Database(
    entities = [
        Challenge::class,
        ChallengeCategory::class,
        ChallengeInfo::class,
        ActiveChallengeItem::class,
        User::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
    abstract fun activeChallengeDao(): ActiveChallengeDao
    abstract fun userDao(): UserDao
}
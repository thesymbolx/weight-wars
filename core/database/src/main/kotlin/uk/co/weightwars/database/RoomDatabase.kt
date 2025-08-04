package uk.co.weightwars.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.co.weightwars.database.dao.ActiveChallengeDao
import uk.co.weightwars.database.dao.ChallengeDao
import uk.co.weightwars.database.dao.UserDao
import uk.co.weightwars.database.entities.SubChallengeEntity
import uk.co.weightwars.database.entities.Challenge
import uk.co.weightwars.database.entities.ChallengeCategory
import uk.co.weightwars.database.entities.ChallengeInfoEntity
import uk.co.weightwars.database.entities.Friend
import uk.co.weightwars.database.entities.ParticipantEntity
import uk.co.weightwars.database.entities.Profile

@Database(
    entities = [
        Challenge::class,
        ChallengeCategory::class,
        ChallengeInfoEntity::class,
        SubChallengeEntity::class,
        Profile::class,
        Friend::class,
        ParticipantEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun challengeDao(): ChallengeDao
    abstract fun activeChallengeDao(): ActiveChallengeDao
    abstract fun userDao(): UserDao
}
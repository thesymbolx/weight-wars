package uk.co.weightwars.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.weightwars.database.RoomDatabase
import uk.co.weightwars.database.dao.ChallengeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class
DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): RoomDatabase =
        Room.databaseBuilder(
            context = context,
            klass = RoomDatabase::class.java,
            name = "database-weight-wars"
        ).build()

    @Provides
    fun challengeDaoBinding(roomDatabase: RoomDatabase): ChallengeDao =
        roomDatabase.challengeDao()

    @Provides
    fun activeChallengeBinding(roomDatabase: RoomDatabase) =
        roomDatabase.activeChallengeDao()

    @Provides
    fun userDaoBinding(roomDatabase: RoomDatabase) =
        roomDatabase.userDao()
}
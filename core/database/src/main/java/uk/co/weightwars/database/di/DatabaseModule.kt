package uk.co.weightwars.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.weightwars.database.WeightWarsDatabase
import uk.co.weightwars.database.dao.ChallengeCategoryDao
import uk.co.weightwars.database.dao.ChallengeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class
DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): WeightWarsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = WeightWarsDatabase::class.java,
            name = "database-weight-wars"
        ).build()

    @Provides
    fun challengeDaoBinding(roomDatabase: WeightWarsDatabase): ChallengeDao =
        roomDatabase.challengeDao()

    @Provides
    fun challengeCategoryBinding(roomDatabase: WeightWarsDatabase): ChallengeCategoryDao =
        roomDatabase.challengeCategoryDao()

    @Provides
    fun activeChallengeBinding(roomDatabase: WeightWarsDatabase) =
        roomDatabase.activeChallengeDao()
}
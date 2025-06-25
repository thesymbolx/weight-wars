package uk.co.weightwars.data.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.weightwars.data.database.WeightWarsDatabase
import uk.co.weightwars.data.database.dao.ChallengeCategoryDao
import uk.co.weightwars.data.database.dao.ChallengeDao
import uk.co.weightwars.data.database.entities.ChallengeCategory
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
            name = "database-nasa"
        ).build()

    @Provides
    fun challengeDaoBinding(roomDatabase: WeightWarsDatabase): ChallengeDao =
        roomDatabase.challengeDao()

    @Provides
    fun challegeCatagoryBinding(roomDatabase: WeightWarsDatabase): ChallengeCategoryDao =
        roomDatabase.challengeCategoryDao()
}
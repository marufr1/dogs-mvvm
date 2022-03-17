package net.dogs.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.dogs.data.local.DogDao
import net.dogs.data.local.DogsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) : DogsDatabase {
        return Room.databaseBuilder(
            application,
            DogsDatabase::class.java,
            "dogs.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDogDao(dogsDatabase: DogsDatabase) : DogDao {
        return dogsDatabase.dog()
    }

}
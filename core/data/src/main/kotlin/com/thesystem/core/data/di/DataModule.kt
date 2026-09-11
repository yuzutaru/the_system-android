package com.thesystem.core.data.di

import android.content.Context
import androidx.room.Room
import com.thesystem.core.data.CharacterRepositoryImpl
import com.thesystem.core.data.WorkoutRepositoryImpl
import com.thesystem.core.database.TheSystemDatabase
import com.thesystem.core.database.dao.CharacterDao
import com.thesystem.core.database.dao.WorkoutSessionDao
import com.thesystem.core.domain.CharacterRepository
import com.thesystem.core.domain.WorkoutRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TheSystemDatabase =
        Room.databaseBuilder(context, TheSystemDatabase::class.java, "the_system.db").build()

    @Provides
    fun provideWorkoutSessionDao(database: TheSystemDatabase): WorkoutSessionDao =
        database.workoutSessionDao()

    @Provides
    fun provideCharacterDao(database: TheSystemDatabase): CharacterDao =
        database.characterDao()

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(impl: WorkoutRepositoryImpl): WorkoutRepository

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}

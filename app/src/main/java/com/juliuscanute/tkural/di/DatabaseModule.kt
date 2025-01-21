package com.juliuscanute.tkural.di

import android.content.Context
import androidx.room.Room
import com.juliuscanute.tkural.data.ThirukuralDao
import com.juliuscanute.tkural.data.ThirukuralDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ThirukuralDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ThirukuralDatabase::class.java,
            "kural.sqlite"
        ).createFromAsset("kural.sqlite")
            .build()
    }

    @Provides
    fun provideThirukuralDao(database: ThirukuralDatabase): ThirukuralDao {
        return database.thirukuralDao()
    }
}
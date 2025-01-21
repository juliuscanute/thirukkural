package com.juliuscanute.tkural.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Thirukural::class, WordMeaning::class, Theme::class], version = 1)
abstract class ThirukuralDatabase : RoomDatabase() {
    abstract fun thirukuralDao(): ThirukuralDao
}
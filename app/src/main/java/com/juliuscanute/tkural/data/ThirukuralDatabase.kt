package com.juliuscanute.tkural.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Thirukural::class, WordMeaning::class, Theme::class], version = 2)
abstract class ThirukuralDatabase : RoomDatabase() {
    abstract fun thirukuralDao(): ThirukuralDao
}
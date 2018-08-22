package com.example.harshitjain.wynkbasicpoc.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.harshitjain.wynkbasicpoc.model.Item

@Database(entities = [Item::class, Collection::class], version = 3)
@TypeConverters(DbTypeConverters.JsonObjectTypeConverter::class)
abstract class WynkDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun collectionDao(): CollectionDao
}
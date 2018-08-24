package com.example.wynkbasic.content.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.wynkbasic.content.db.dao.CollectionDao
import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.db.entities.Collection
import com.example.wynkbasic.content.db.entities.Item

@Database(entities = [Item::class, Collection::class], version = 1, exportSchema = true)
@TypeConverters(DbTypeConverters.JsonObjectTypeConverter::class)
abstract class WynkDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun collectionDao(): CollectionDao
}
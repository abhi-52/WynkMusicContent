package com.example.wynkbasic.content.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.wynkbasic.content.db.dao.CollectionDao
import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.db.entities.ItemRelation
import com.example.wynkbasic.content.db.entities.Item

@Database(entities = [Item::class, ItemRelation::class], version = 1, exportSchema = true)
@TypeConverters(DbTypeConverters.JsonObjectTypeConverter::class)
abstract class WynkDB : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}
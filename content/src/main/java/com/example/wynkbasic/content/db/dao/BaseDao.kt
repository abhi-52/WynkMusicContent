package com.example.wynkbasic.content.db.dao

import android.arch.persistence.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItem(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(tList: List<T>)

    @Update
    abstract fun updateItem(t: T)

    @Update
    abstract fun updateAll(tList: List<T>)

    @Delete
    abstract fun deleteItem(t: T)

    @Delete
    abstract fun deleteAll(tList: List<T>)
}
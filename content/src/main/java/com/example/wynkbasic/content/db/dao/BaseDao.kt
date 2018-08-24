package com.example.wynkbasic.content.db.dao

import android.arch.persistence.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplaceItem(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplaceAll(tList: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnoreItem(t: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertOrIgnoreAll(tList: List<T>)

    @Update
    abstract fun updateItem(t: T)

    @Update
    abstract fun updateAll(tList: List<T>)

    @Delete
    abstract fun deleteItem(t: T)

    @Delete
    abstract fun deleteAll(tList: List<T>)
}
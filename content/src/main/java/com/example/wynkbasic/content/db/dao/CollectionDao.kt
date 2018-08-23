package com.example.wynkbasic.content.db.dao

import android.arch.persistence.room.*
import com.example.wynkbasic.content.db.entities.Collection

@Dao
abstract class CollectionDao : BaseDao<Collection>(){

    @Query("delete from Collection")
    abstract fun clearCollections()
}
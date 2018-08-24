package com.example.wynkbasic.content.db.dao

import android.arch.persistence.room.*

abstract class CollectionDao{

    @Query("delete from ItemRelation")
    abstract fun clearCollections()
}
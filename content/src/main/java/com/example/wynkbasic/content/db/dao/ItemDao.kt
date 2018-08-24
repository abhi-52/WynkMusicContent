package com.example.wynkbasic.content.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.wynkbasic.content.db.entities.Item

@Dao
abstract class ItemDao : BaseDao<Item>(){

    @Query("select * from Item where type = :type")
    abstract fun loadItemList(type: String): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    abstract fun loadItemById(id: String): LiveData<Item>

    @Query("delete from Item")
    abstract fun clearItemList()

    @Query("SELECT A.* FROM Collection B LEFT JOIN Item A ON A.id=B.item_id WHERE  B.collection_id=:collectionId ORDER BY B.rank ASC LIMIT 50")
    abstract fun loadSongItems(collectionId: String): LiveData<List<Item>>
}
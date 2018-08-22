package com.example.harshitjain.wynkbasicpoc.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.harshitjain.wynkbasicpoc.model.Item

@Dao
abstract class ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItemList(items: List<Item>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertItem(item: Item?)

    @Delete
    abstract fun deleteItem(item: Item?)

    @Delete
    abstract fun deleteItemList(items: List<Item>?)

    @Query("select * from Item where type = :type")
    abstract fun loadItemList(type: String): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    abstract fun loadItemById(id: String): LiveData<Item>

    @Query("delete from Item")
    abstract fun clearItemList()

    @Query("SELECT B.item_id AS collection_item_id, * FROM Collection B LEFT JOIN Item A ON A.id=B.item_id WHERE  B.collection_id=:collectionId ORDER BY B.rank ASC LIMIT 50")
    abstract fun loadSongItems(collectionId: String): LiveData<List<Item>>
}
package com.example.wynkbasic.content.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.db.entities.ItemRelation

@Dao
abstract class ItemDao : BaseDao<Item>() {

    @Query("select * from Item where type = :type")
    abstract fun loadItemList(type: String): LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    abstract fun loadItemById(id: String): LiveData<Item>

    @Query("delete from Item")
    abstract fun clearItemList()

    @Transaction
    @Query("SELECT A.* FROM ItemRelation B LEFT JOIN Item A ON A.id=B.child_id WHERE  B.parent_id=:parentId ORDER BY B.rank ASC LIMIT 50")
    abstract fun loadSongItems(parentId: String): LiveData<List<Item>>

    @Transaction
    open fun insertData(item: Item) {
        insertOrReplaceItem(item)
        item.items?.let { insertOrReplaceAll(it) }

        var rank = item.offset
        val collections = mutableListOf<ItemRelation>()

        item.items?.forEach {
            val collection = ItemRelation(item.id, it.id, it.title ?: "", rank)
            collections.add(collection)
            rank++
        }

        if (collections.size > 0) {
            insertOrReplaceAllItemRelation(collections)
        }
    }

    //Insertion into ItemRelation table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplaceAllItemRelation(itemRelation: List<ItemRelation>)
}
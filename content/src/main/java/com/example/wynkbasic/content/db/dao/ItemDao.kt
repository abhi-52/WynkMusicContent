package com.example.wynkbasic.content.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.db.entities.ItemRelation

@Dao
abstract class ItemDao : BaseDao<Item>() {

    @Query("SELECT * FROM Item WHERE type = :type")
    abstract fun loadItemList(type: String): LiveData<List<Item>>

    @Query("SELECT * FROM Item WHERE id = :id")
    abstract fun loadItemById(id: String): LiveData<Item>

    @Query("SELECT * FROM Item WHERE id in (:ids)")
    abstract fun loadItemByIds(ids: List<String>): LiveData<Item>

    @Query("DELETE FROM Item")
    abstract fun clearItemList()

    @Query("DELETE FROM Item where id = :id")
    abstract fun deleteItemById(id: String)

    @Transaction
    @Query("SELECT A.* FROM ItemRelation B LEFT JOIN Item A ON A.id=B.child_id WHERE B.parent_id=:parentId ORDER BY B.rank ASC")
    abstract fun loadItemsForParentId(parentId: String): LiveData<List<Item>>

    @Transaction
    @Query("SELECT A.* FROM ItemRelation B LEFT JOIN Item A ON A.id=B.child_id WHERE  B.parent_id=:parentId ORDER BY B.rank ASC")
    abstract fun getItemsForParentId(parentId: String): List<Item>?

    @Query("SELECT * FROM Item WHERE type = :type")
    abstract fun loadItemsByType(type: String): LiveData<List<Item>>

    @Transaction
    open fun insertData(item: Item) {
        insertOrReplaceItem(item)
        item.items?.also { insertOrReplaceAll(it) }

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

    @Transaction
    open fun deleteItem(id: String) {
        removeAllRelationsForId(id)
        deleteItemById(id)
    }

    fun deleteItemRecursive(id: String) {
        getItemsForParentId(id)?.let {
            for (item in it) {
                deleteItemRecursive(item.id)
            }
        }
        deleteItem(id)
    }

    fun deleteItemRecursive(item: Item) {
        deleteItemRecursive(item.id)
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun deleteItem(item: Item) {
        deleteItem(item.id)
    }

    //Insertion into ItemRelation table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    internal abstract fun insertOrReplaceAllItemRelation(itemRelation: List<ItemRelation>)

    @Query("DELETE from ItemRelation where parent_id = :parentId AND child_id = :childId")
    abstract fun removeItemRelation(childId: String, parentId: String)

    @Query("DELETE from ItemRelation where parent_id=:itemId OR child_id = :itemId")
    abstract fun removeAllRelationsForId(itemId: String)
}
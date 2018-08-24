package com.example.wynkbasic.content.repo

import android.arch.lifecycle.LiveData

import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.network.ApiService
import com.example.wynkbasic.content.db.entities.Collection
import com.example.wynkbasic.content.db.dao.CollectionDao
import javax.inject.Inject

class ItemRepository @Inject
internal constructor(val appExecutors: AppExecutors, val apiService: ApiService,
                     val itemDao: ItemDao, private val collectionDao: CollectionDao) {

    fun loadItem(id: String, type: String, count: Int, offset: Int, childType: String): LiveData<Resource<List<Item>>> {
        return object : NetworkBoundResource<List<Item>, Item>(appExecutors) {
            override fun saveCallResult(entity: Item) {
                itemDao.insertItem(entity)
                saveChildItems(entity.items)
                updateCollection(entity)
            }

            override fun shouldFetch(data: List<Item>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<Item>> {
                return getItem(id, type)
            }

            override fun createCall(): LiveData<ApiResponse<Item>> {
                return apiService.getItem(id, type, count, offset)
            }

            override fun onFetchFailed() {

            }
        }.asLiveData()
    }

    private fun saveChildItems(items: List<Item>?) {
        items?.let { itemDao.insertAll(it) }
    }

    private fun updateCollection(entity: Item) {

        var rank = entity.offset
        val collections = mutableListOf<Collection>()

        entity.items?.forEach {
            val collection = Collection(entity.id, it.id, it.title ?: "", rank)
            collections.add(collection)
            rank++
        }

        if (collections.size > 0) {
            collectionDao.insertAll(collections)
        }
    }

    private fun getItem(id: String, type: String): LiveData<List<Item>> {
        return itemDao.loadSongItems(id)
    }
}
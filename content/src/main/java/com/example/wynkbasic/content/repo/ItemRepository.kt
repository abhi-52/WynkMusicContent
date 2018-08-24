package com.example.wynkbasic.content.repo

import android.arch.lifecycle.LiveData

import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.network.ApiService
import com.example.wynkbasic.content.db.entities.ItemRelation
import com.example.wynkbasic.content.db.dao.CollectionDao
import javax.inject.Inject

class ItemRepository @Inject
internal constructor(val appExecutors: AppExecutors, val apiService: ApiService,
                     val itemDao: ItemDao) {

    fun loadItem(id: String, type: String, count: Int, offset: Int, childType: String): LiveData<Resource<List<Item>>> {
        return object : NetworkBoundResource<List<Item>, Item>(appExecutors) {
            override fun saveCallResult(entity: Item) {
                itemDao.insertData(entity)
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

    private fun getItem(id: String, type: String): LiveData<List<Item>> {
        return itemDao.loadSongItems(id)
    }
}
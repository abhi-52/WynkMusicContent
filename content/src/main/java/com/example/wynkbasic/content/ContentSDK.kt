package com.example.wynkbasic.content

import android.app.Application
import android.arch.lifecycle.LiveData
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.di.DaggerDBAppComponent
import com.example.wynkbasic.content.repo.ItemRepository
import com.example.wynkbasic.content.repo.Resource
import com.example.wynkbasic.content.utils.SingletonHolder
import javax.inject.Inject

class ContentSDK private constructor(val application: Application) {

    @Inject
    lateinit var itemRepository: ItemRepository

    init {
        DaggerDBAppComponent.builder().application(application).build().inject(this)
    }

    companion object : SingletonHolder<ContentSDK, Application>(::ContentSDK)

    fun getContent(id: String, type: String, count: Int, offset: Int, childType: String): LiveData<Resource<List<Item>>> =
            itemRepository.loadItem(id, type, count, offset, childType)

}
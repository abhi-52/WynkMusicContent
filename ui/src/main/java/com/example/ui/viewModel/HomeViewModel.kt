package com.example.ui.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.repo.Resource
import com.example.ui.WynkApp

class HomeViewModel : ViewModel() {

    fun getItem(itemId: String?, type: String?, childType: String): LiveData<Resource<List<Item>>>? {
        if (itemId == null || type == null)
            return null
        return WynkApp.instance.contentSDK.getContent(itemId, type, 50, 0, childType)
    }
}
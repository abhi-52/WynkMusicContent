package com.example.ui.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.repo.Resource
import com.example.ui.WynkApp
import com.example.ui.utils.AbsentLiveData

class HomeViewModel : ViewModel() {

    private var itemId = MutableLiveData<String>()
    private lateinit var type : String
    private lateinit var childType : String

    var items : LiveData<Resource<List<Item>>>? = Transformations.switchMap(itemId) {
        if (it.isNullOrBlank())
            AbsentLiveData.create()
        else
            WynkApp.instance.contentSDK.getContent(it, type, 50, 0, childType)
    }

    fun getItem(itemId: String?, type: String?, childType: String) {
        if (itemId != null && type != null) {
            this.itemId.value = itemId
            this.type = type
            this.childType = childType
        }
    }
}
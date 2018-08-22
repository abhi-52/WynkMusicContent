package com.example.harshitjain.wynkbasicpoc.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import org.json.JSONObject

@Entity
open class Item {

    @PrimaryKey
    lateinit var id: String

    var mappedId: String? = null

    var title: String? = null

    var itemContentLang: String? = null

    var mappedState: Int = 0

    var offset: Int = 0

    var type: String? = null

    var keywords: String? = null

    lateinit var meta: JSONObject

    @Ignore
    var items: MutableList<Item>? = null
}
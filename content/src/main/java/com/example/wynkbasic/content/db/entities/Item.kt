package com.example.wynkbasic.content.db.entities

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

    internal lateinit var meta: JSONObject

    @Ignore
    var items: MutableList<Item>? = null

    fun getSmallImage(): String = meta.getString("smallImage")

    fun getSubTitle(): String = meta.getString("subtitle")

}

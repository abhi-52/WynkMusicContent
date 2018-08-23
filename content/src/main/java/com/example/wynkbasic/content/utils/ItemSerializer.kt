package com.example.wynkbasic.content.utils

import com.example.wynkbasic.content.db.entities.Item
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.lang.reflect.Type

class ItemSerializer : JsonDeserializer<Item> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Item {
        json?.asJsonObject?.let {
            val jsonObject = JSONObject(it.toString())
            return jsonToItem(jsonObject)
        }
        return Item()
    }

    private fun jsonToItem(jsonObject: JSONObject): Item {
        val itemModel = Item()
        itemModel.id = jsonObject.optString("id", "")
        jsonObject.remove("id")
        itemModel.title = jsonObject.optString("title", "")
        jsonObject.remove("title")
        itemModel.itemContentLang = jsonObject.optString("itemContentLang", "")
        jsonObject.remove("itemContentLang")
        itemModel.type = jsonObject.optString("type", "")
        jsonObject.remove("type")
        itemModel.offset = jsonObject.optInt("offset", 0)
        jsonObject.remove("offset")
        itemModel.keywords = jsonObject.optString("keywords")
        jsonObject.remove("keywords")
        itemModel.items = mutableListOf()
        if (jsonObject.has("items")) {
            val items = jsonObject.getJSONArray("items")
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                itemModel.items?.add(jsonToItem(item))
            }
            jsonObject.remove("items")
        }
        itemModel.meta = jsonObject
        return itemModel
    }
}
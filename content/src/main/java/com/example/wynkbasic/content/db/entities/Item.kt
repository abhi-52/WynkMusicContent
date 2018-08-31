package com.example.wynkbasic.content.db.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.example.wynkbasic.content.model.AlbumRef
import com.example.wynkbasic.content.model.Artist
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type

@Entity
class Item {

    @PrimaryKey
    lateinit var id: String

    var mappedId: String? = null

    var title: String? = null

    var itemContentLang: String? = null

    var mappedState: Int = 0

    var offset: Int = 0

    var type: String? = null

    var keywords: String? = null

    var smallImage: String? = null

    var subtitle: String? = null

    var ostreamingUrl: String? = null

    internal lateinit var meta: JSONObject

    @Ignore
    internal var items: MutableList<Item>? = null

    fun getActualTotal(): Int = meta.optInt("actualTotal", 0)

    fun getOwner(): String? = meta.optString("owner")

    fun getItemTypes(): List<String>? = getObjectList("itemTypes")

    fun getLargeImage(): String = meta.optString("largeImage", "")

    fun getFollowCount(): String = meta.optString("followCount", "")

    fun getRailType(): String? = meta.optString("railType")

    fun getShortUrl(): String? = meta.optString("shortUrl")

    fun getCount(): Int = meta.optInt("count", 0)

    fun getDuration(): Int = meta.optInt("duration", 0)

    fun getTotal(): Int = meta.optInt("total", 0)

    fun getSubtype(): String? = meta.optString("subtype")

    fun getExclusive(): Boolean = meta.optBoolean("exclusive", false)

    fun getServerEtag(): String? = meta.optString("serverEtag")

    fun getNewRedesignFeaturedSubTitle(): String? = meta.optString("newRedesignFeaturedSubTitle")

    fun getPlaylistImage(): String? = meta.optString("playlistImage")

    fun getNewRedesignFeaturedTitle(): String? = meta.optString("newRedesignFeaturedTitle")

    fun getPlayIcon(): Boolean = meta.optBoolean("playIcon", false)

    fun getRedesignFeaturedImage(): String? = meta.optString("redesignFeaturedImage")

    fun getItemIds(): List<String>? = getObjectList("itemIds")

    fun isFollowable(): Boolean = meta.optBoolean("isFollowable", false)

    fun getLastUpdate(): Long = meta.optLong("lastUpdated", 0)

    fun getPublishedYear(): String? = meta.optString("publishedYear")

    fun isCurated(): Boolean = meta.optBoolean("isCurated", false)

    fun getFormats(): List<String>? = getObjectList("formats")

    fun getAlbum(): String? = meta.optString("album")

    fun getDownloadUrl(): String? = meta.optString("downloadUrl")

    fun getPurchaseUrl(): String? = meta.optString("purchaseUrl")

    fun getDownloadPrice(): String? = meta.getString("downloadPrice")

    fun getContentState(): String? = meta.optString("contentState")

    fun getRentUrl(): String? = meta.optString("rentUrl")

    fun getSubtitleId(): String? = meta.optString("subtitleId")

    fun getSingers(): List<Artist>? = getObjectList("singers")

    fun getAlbumRef(): AlbumRef? = getObject("albumRef")

    fun getArtist(): Artist? = getObject("artist")

    fun getLyricists(): List<Artist>? = getObjectList("lyricists")

    fun getActors(): List<Artist>? = getObjectList("actors")

    fun getPublisher(): String? = meta.getString("publisher")

    private fun <T> getObjectList(key: String): List<T>? =
        meta.optJSONArray(key)?.let {
            val gson = Gson()
            val type = object : TypeToken<List<T>>() {}.type
            gson.fromJson(it.toString(), type)
        }

    private fun <T> getObject(key: String): T? =
        meta.getJSONObject(key)?.let {
            val gson = Gson()
            val type = object : TypeToken<T>() {}.type
            gson.fromJson(it.toString(), type)
        }
}

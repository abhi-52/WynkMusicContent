package com.example.harshitjain.wynkbasicpoc

import android.app.Application
import android.arch.persistence.room.Room
import com.example.harshitjain.wynkbasicpoc.db.CollectionDao
import com.example.harshitjain.wynkbasicpoc.db.ItemDao
import com.example.harshitjain.wynkbasicpoc.db.WynkDB
import com.example.harshitjain.wynkbasicpoc.liveData.LiveDataCallAdapterFactory
import com.example.harshitjain.wynkbasicpoc.model.Item
import com.example.harshitjain.wynkbasicpoc.model.ItemSerializer
import com.example.harshitjain.wynkbasicpoc.network.ApiService
import com.example.harshitjain.wynkbasicpoc.repo.AppExecutors
import com.example.harshitjain.wynkbasicpoc.repo.ItemRepository
import com.facebook.stetho.Stetho
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WynkApp : Application() {

    val appExecuter = AppExecutors()
    lateinit var apiService: ApiService
    private lateinit var roomDatabase: WynkDB
    lateinit var itemDao: ItemDao
    lateinit var collectionDao: CollectionDao
    lateinit var itemRepository: ItemRepository

    companion object {
        lateinit var instance: WynkApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        Stetho.initializeWithDefaults(this);
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        val type = object : TypeToken<Item>() {}.type
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, ItemSerializer())
        val retrofit = Retrofit.Builder()
            .baseUrl("https://content.wynk.in/")
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()

        apiService = retrofit.create<ApiService>(ApiService::class.java)

        roomDatabase = Room.databaseBuilder(this, WynkDB::class.java, "wynkMusic.db")
            .fallbackToDestructiveMigration().build()
        itemDao = roomDatabase.itemDao()
        collectionDao = roomDatabase.collectionDao()
        itemRepository = ItemRepository(
            instance.appExecuter,
            instance.apiService,
            instance.itemDao,
            collectionDao
        )
    }
}
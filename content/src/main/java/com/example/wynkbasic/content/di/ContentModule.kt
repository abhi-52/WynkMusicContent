package com.example.wynkbasic.content.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.wynkbasic.content.BuildConfig
import com.example.wynkbasic.content.db.WynkDB
import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.network.ApiService
import com.example.wynkbasic.content.utils.Constants
import com.example.wynkbasic.content.utils.ItemSerializer
import com.example.wynkbasic.content.utils.LiveDataCallAdapterFactory
import com.example.wynkbasic.content.utils.NetworkConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ContentModule {

    @Provides
    @Singleton
    fun provideWynkDB(application: Application): WynkDB {
        return Room
                .databaseBuilder(application, WynkDB::class.java, Constants.dbName)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl(NetworkConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val type = object : TypeToken<Item>() {}.type
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, ItemSerializer())
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG)
            builder.addInterceptor(interceptor)

        builder.cache(cache)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = 20 * 1024 * 1024L // 20 MB
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideItemDao(wynkDB: WynkDB): ItemDao {
        return wynkDB.itemDao()
    }
}
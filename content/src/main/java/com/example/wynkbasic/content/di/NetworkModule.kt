package com.example.wynkbasic.content.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.wynkbasic.content.db.WynkDB
import com.example.wynkbasic.content.db.dao.CollectionDao
import com.example.wynkbasic.content.db.dao.ItemDao
import com.example.wynkbasic.content.network.ApiService
import com.example.wynkbasic.content.network.RetrofitProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWynkDB(application: Application): WynkDB {
        return Room.databaseBuilder(application, WynkDB::class.java, "WynkDB")
                .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(application: Application): ApiService {
        return RetrofitProvider.provideDefaultRetrofit(application).create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideItemDao(wynkDB: WynkDB): ItemDao {
        return wynkDB.itemDao()
    }
}
package com.example.wynkbasic.content.network

import android.app.Application
import com.example.wynkbasic.content.BuildConfig
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.utils.ItemSerializer
import com.example.wynkbasic.content.utils.LiveDataCallAdapterFactory
import com.example.wynkbasic.content.utils.NetworkConstants
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {
    fun provideDefaultRetrofit(context: Application): Retrofit {
        val type = object : TypeToken<Item>() {}.type
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(type, ItemSerializer())

        return Retrofit.Builder()
                .baseUrl(NetworkConstants.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .client(provideOkHttpClient(context))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    private fun provideOkHttpClient(context: Application): OkHttpClient {
        val cacheSize = 20 * 1024 * 1024L // 20 MB
        val cache = Cache(context.cacheDir, cacheSize)

        val builder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.cache(cache)
        return builder.build()
    }
}

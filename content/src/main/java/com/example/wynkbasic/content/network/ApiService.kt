package com.example.wynkbasic.content.network

import android.arch.lifecycle.LiveData
import com.example.wynkbasic.content.db.entities.Item
import com.example.wynkbasic.content.repo.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("music/v4/content?lang=en")
    fun getItem(@Query("id") id: String, @Query("type") type: String, @Query("count") count: Int, @Query("offset") offset: Int): LiveData<ApiResponse<Item>>
}
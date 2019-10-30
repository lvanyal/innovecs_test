package com.example.myapplication.data.retrofit

import com.example.myapplication.data.response.ButtonActionResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/androidexam/butto_to_action_config.json")
    fun getActions(): Single<List<ButtonActionResponse>>
}
package com.example.demoapp.network

import com.example.demoapp.data.apiresponses.UserData
import retrofit2.Response
import retrofit2.http.GET

interface NetworkEndpoints {

    @GET("users")
    suspend fun getDemo(): Response<UserData>

}
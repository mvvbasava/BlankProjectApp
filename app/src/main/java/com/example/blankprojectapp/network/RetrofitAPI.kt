package com.example.blankprojectapp.network

import com.example.blankprojectapp.data.MessageApiJsonData
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("breeds/list/all")
    suspend fun getBreeds(): Response<MessageApiJsonData?>?
}

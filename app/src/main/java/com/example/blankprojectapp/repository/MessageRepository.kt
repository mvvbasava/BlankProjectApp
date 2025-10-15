package com.example.blankprojectapp.repository

import com.example.blankprojectapp.data.MessageApiJsonData
import com.example.blankprojectapp.network.RetrofitAPI
import com.example.blankprojectapp.network.RetrofitHelper
import retrofit2.Response
import retrofit2.create

class MessageRepository {
    private val retrofitAPI = RetrofitHelper.getInstance()?.create(RetrofitAPI::class.java)
    suspend fun fetchMessages(): Response<MessageApiJsonData?>? {
        return retrofitAPI?.getBreeds()
    }
}
package com.example.id_tix.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import kotlin.getValue

interface LaravelApiService {
    @POST("input_user")
    suspend fun createUser(@Body user: LaravelUser): Response<LaravelUser>

    @POST("user_check")
    suspend fun checkUser(@Body emailRequest: EmailRequest): Response<LaravelUser>

    @POST("user_topup")
    suspend fun topUpSaldo(@Body topUpRequest: TopUpRequest): Response<LaravelUser>
}

data class LaravelUser(
    val name: String,
    val email: String,
    val saldo: Int
)

data class TopUpRequest(
    val email: String,
    val amount: Int
)

data class EmailRequest(val email: String)

object LaravelApi {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: LaravelApiService by lazy {
        retrofit.create(LaravelApiService::class.java)
    }
}
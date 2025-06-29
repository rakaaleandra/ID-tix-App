package com.example.id_tix.api

import com.google.gson.annotations.SerializedName
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

    @POST("input_pemesanan")
    suspend fun inputPemesanan(@Body pemesananRequest: PemesananRequest): Response<PemesananResponse>

    @POST("pemesanan_check")
    suspend fun getUserBookings(@Body emailRequest: EmailRequest): Response<List<PemesananData>>
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

data class PemesananResponse(
    val success: Boolean,
    val message: String,
    val data: PemesananData?
)

data class PemesananData(
    val email: String,
    val filmId: Int,
    val namaFilm: String,
    val filmPoster: Int,
    val namaBioskop: String,
    val jadwalTayang: String,
    val kursi: String,
    val jumlahKursi: Int,
    val codePemesanan: String,
    val tanggalPemesanan: String,
    val statusPemesanan: String,
    val feedback: String?,
    val totalBayar: Int
)

data class PemesananRequest(
    val email: String,
    val filmId: Int,
    val namaFilm: String,
    val filmPoster: Int,
    val namaBioskop: String,
    val jadwalTayang: String,
    val kursi: String,
    val jumlahKursi: Int,
    val tanggalPemesanan: String,
    val statusPemesanan: String,
    val feedback: String? = null,
    val totalBayar: Int
)

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
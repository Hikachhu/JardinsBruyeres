package com.example.gauche


import android.database.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {


    @GET("api/capteur")
    fun search(): Call<Any>

    companion object Factory {
        fun create(): ApiHelper {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://192.20.55.3:5000/")
                .build()
            return retrofit.create(ApiHelper::class.java);
        }
    }
}
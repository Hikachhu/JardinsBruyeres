package com.example.gauche


import android.database.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {


    @GET("search/users")
    fun search(@Query("q") query: String,
               @Query("page") page: Int = 1,
               @Query("per_page") perPage: Int = 20): Call<Result>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): ApiHelper {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()
            return retrofit.create(ApiHelper::class.java);
        }
    }
}
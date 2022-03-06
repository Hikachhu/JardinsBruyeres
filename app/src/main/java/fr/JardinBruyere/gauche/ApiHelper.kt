package fr.JardinBruyere.gauche


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {


    @GET("api/capteur?numTable=3&amount=10")
    fun search(@Query("sensorid") sensorid : Int): Call<Any>

    @GET("api/capteur?numTable=0")
    fun getSensor(): Call<Any>

    @GET("api/capteur?numTable=1")
    fun getSensorTypes(): Call<Any>

    @GET("api/capteur?numTable=2")
    fun getStation(): Call<Any>

    companion object Factory {
        fun create(ip: String): fr.JardinBruyere.gauche.ApiHelper {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://$ip:5001/")
                .build()
            return retrofit.create(fr.JardinBruyere.gauche.ApiHelper::class.java);
        }
    }
}
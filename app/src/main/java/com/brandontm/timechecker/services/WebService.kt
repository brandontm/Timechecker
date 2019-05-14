package com.brandontm.timechecker.services

import com.brandontm.timechecker.BuildConfig
import com.brandontm.timechecker.entities.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WebService {
    companion object {
        // Obtener Webservice URL de gradle.properties
        private val BASE_URL = BuildConfig.webserviceUrl

        fun create(): WebService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(WebService::class.java)
        }
    }

    @GET("/users")
    fun getUsers(): Call<List<User>>

    @POST("/timecheck")
    fun timecheck(@Body data: JsonObject): Call<JsonObject>
}
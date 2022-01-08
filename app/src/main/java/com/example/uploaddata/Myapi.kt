package com.example.uploaddata

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface Myapi {

    @Multipart
    @POST("")
    fun uploadimage(@Part image:MultipartBody.Part,@Part("desc")desc:RequestBody)
    companion object{
        operator fun invoke():Myapi{
            return Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Myapi::class.java)
        }
    }
}
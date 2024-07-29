package com.bidwinko.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

object Retrofit {
    //  public static final String BASE_URL = "https://bidwinko.app/api/v1/";
    //    public static final String BASE_URL1 = "https://bidwinko.app/";
    const val BASE_URL = " http://192.168.1.13:3000/api/v1/"

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(60, TimeUnit.SECONDS)    // Read timeout
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // Create Retrofit instance using the OkHttpClient with logging interceptor
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())

            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}
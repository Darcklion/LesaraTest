package com.lesaratest.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lesaratest.models.TrendProductsResponse
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    private val serverUrl = "https://test4.lesara.de/restapi/v1/"

    private var api = Retrofit.Builder().baseUrl(serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())!!
            .client(getHttpClient())
            .build()
            .create(API::class.java)!!

    private fun getHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        httpClient.addNetworkInterceptor(StethoInterceptor())!!
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request = original.newBuilder()
                    .header("User-Agent", "Lesara (com.lesara.app; Android)")
                    .method(original.method(), original.body())
                    .build()
            chain.proceed(request)
        }

        return httpClient.build()
    }

    fun getProducts(pageNumber: Int): Observable<TrendProductsResponse>{
        return api.getProducts(page_number = pageNumber)
    }
}
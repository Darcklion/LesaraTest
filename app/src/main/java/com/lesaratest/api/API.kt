package com.lesaratest.api

import com.lesaratest.models.TrendProductsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("trendproducts/?")
    fun getProducts(
            @Query("app_token") app_token: String = "this_is_an_app_token",
            @Query("user_token") user_token: String = "63a12a8116814a9574842515378c93c64846fc3d0858def78388be37e127cd17",
            @Query("store_id") store_id: Int = 1,
            @Query("page_number") page_number: Int = 1
    ): Observable<TrendProductsResponse>
}
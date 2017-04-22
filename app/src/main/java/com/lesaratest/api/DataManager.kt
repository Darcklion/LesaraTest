package com.lesaratest.api

import com.lesaratest.models.TrendProductsResponse

object DataManager {

    private var apiManager = ApiManager()

    fun getTrendProducts(pageNumber: Int) =
            apiManager.getProducts(pageNumber)
                    .map(TrendProductsResponse::trend_products)
}
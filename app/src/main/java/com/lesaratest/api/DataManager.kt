package com.lesaratest.api

import com.lesaratest.models.TrendProductsResponse

object DataManager {

    private var apiManager = ApiManager()
    var trendProducts: TrendProductsResponse.ProductsResponse? = null
        private set

    fun getTrendProducts(pageNumber: Int) =
            apiManager.getProducts(pageNumber)
                    .map{ trendProducts ->
                        this.trendProducts = trendProducts.trend_products
                        trendProducts.trend_products
                    }!!
}
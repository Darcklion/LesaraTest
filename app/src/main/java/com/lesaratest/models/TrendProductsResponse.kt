package com.lesaratest.models

data class TrendProductsResponse (
        var trend_products: ProductsResponse
) {
    data class ProductsResponse (
            var products: List<Product>,
            val number_products: Int,
            val number_pages: Int,
            val current_page: Int
    )
}
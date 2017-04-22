package com.lesaratest.views

import com.lesaratest.models.Product

interface ProductsView {
    fun onLoadProducts(products: List<Product>)
    fun onLoadMore(products: List<Product>)
}
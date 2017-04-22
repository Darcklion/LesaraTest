package com.lesaratest.presenter

import com.lesaratest.api.DataManager
import com.lesaratest.views.ProductsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class ProductManager {
    lateinit var productsView: WeakReference<ProductsView>
    var currentPage = 1
    var pagesNumber = 1

    fun bindView(view: ProductsView) {
        productsView = WeakReference(view)
        loadProducts()
    }

    fun loadProducts() {
        DataManager.getTrendProducts(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { products ->
                            currentPage = products.current_page
                            pagesNumber = products.number_pages
                            productsView.get()?.onLoadProducts(products.products)
                        },
                        Throwable::printStackTrace
                )
    }

    fun loadMore() {
        DataManager.getTrendProducts(currentPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { products ->
                            currentPage = products.current_page
                            pagesNumber = products.number_pages
                            productsView.get()?.onLoadMore(products.products)
                        },
                        Throwable::printStackTrace
                )
    }

    fun canLoadMore() = currentPage == pagesNumber

}

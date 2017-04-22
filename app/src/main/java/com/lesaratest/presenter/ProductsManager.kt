package com.lesaratest.presenter

import com.lesaratest.api.DataManager
import com.lesaratest.models.Product
import com.lesaratest.views.ProductsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class ProductManager {
    lateinit var productsView: WeakReference<ProductsView>
    private var products: ArrayList<Product> = ArrayList()
    var isLoading = false
    var currentPage = 1
    var pagesNumber = 1
    var scrollPosition: Int = 0

    fun bindView(view: ProductsView) {
        productsView = WeakReference(view)
    }

    fun getLoadedProductsList() = products

    fun loadProducts() {
        isLoading = true
        DataManager.getTrendProducts(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isLoading = false }
                .subscribe(
                        { products ->
                            currentPage = products.current_page
                            pagesNumber = products.number_pages
                            this.products = ArrayList(products.products)
                            productsView.get()?.onLoadProducts(products.products)
                        },
                        Throwable::printStackTrace
                )
    }

    fun loadMore() {
        isLoading = true
        DataManager.getTrendProducts(currentPage + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { isLoading = false }
                .subscribe(
                        { products ->
                            currentPage = products.current_page
                            pagesNumber = products.number_pages
                            this.products.addAll(ArrayList(products.products))
                            productsView.get()?.onLoadMore(products.products)
                        },
                        Throwable::printStackTrace
                )
    }

    fun canLoadMore() = currentPage != pagesNumber

}

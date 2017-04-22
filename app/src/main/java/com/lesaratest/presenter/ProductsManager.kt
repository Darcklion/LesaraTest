package com.lesaratest.presenter

import com.lesaratest.api.DataManager
import com.lesaratest.models.Product
import com.lesaratest.views.ProductsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class ProductManager {
    lateinit var productsView: WeakReference<ProductsView>
    private var products: ArrayList<Product?> = ArrayList()
    var currentPage = 1
    var pagesNumber = 1

    fun bindView(view: ProductsView) {
        productsView = WeakReference(view)
    }

    fun getLoadedProductsList() = products

    fun loadProducts() {
        DataManager.getTrendProducts(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { products ->
                            currentPage = products.current_page
                            pagesNumber = products.number_pages
                            this.products = ArrayList(products.products)
                            productsView.get()?.onLoadProducts(products.products)
                        },
                        { e ->
                            productsView.get()?.onError(e)
                        }
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
                            this.products.addAll(ArrayList(products.products))
                            productsView.get()?.onLoadMore(products.products)
                        },
                        { e ->
                            productsView.get()?.onError(e)
                        }
                )
    }

    fun canLoadMore() = currentPage != pagesNumber

}

package com.lesaratest.views

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lesaratest.App
import com.lesaratest.R
import com.lesaratest.models.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ProductsView {

    val SCROLL_POSITION = "scroll_position"
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        val presenter = App.productsManager
        presenter.bindView(this)

        var adapter = ProductsAdapter(this)
        val mLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!presenter.canLoadMore() || isLoading)
                    return
                val firstVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total = recyclerView.adapter.itemCount
                if (total - firstVisible < 20) {
                    presenter.loadMore()
                    isLoading = true
                    (recyclerView.adapter as ProductsAdapter).products.add(null)
                    recyclerView.adapter.notifyItemInserted(recyclerView.adapter.itemCount - 1)
                }
            }
        })

        mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (adapter.getItemViewType(position)) {
                    ProductsAdapter.VIEW_PROGRESS -> return 2
                    ProductsAdapter.VIEW_PRODUCT -> return 1
                    else -> return 1
                }
            }
        }

        if (savedInstanceState == null) {
            presenter.loadProducts()
            isLoading = true
        } else {
            (recyclerView.adapter as ProductsAdapter).products = presenter.getLoadedProductsList()
            recyclerView.adapter.notifyDataSetChanged()
            recyclerView.scrollToPosition(savedInstanceState.getInt(SCROLL_POSITION))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(SCROLL_POSITION, (recyclerView.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    override fun onLoadProducts(products: List<Product>) {
        (recyclerView.adapter as ProductsAdapter).products = ArrayList(products)
        recyclerView.adapter.notifyDataSetChanged()
        isLoading = false
    }

    override fun onLoadMore(products: List<Product>) {
        val adapter = (recyclerView.adapter as ProductsAdapter)
        removeProgressItem(adapter)
        adapter.products.addAll(ArrayList(products))
        adapter.notifyDataSetChanged()
        isLoading = false
    }

    override fun onError(e: Throwable) {
        isLoading = false
        val adapter = (recyclerView.adapter as ProductsAdapter)
        if (adapter.itemCount > 0 && adapter.products[adapter.itemCount - 1] == null)
            removeProgressItem(adapter)
        Snackbar.make(recyclerView, getString(R.string.error_message), Snackbar.LENGTH_LONG).show()
    }

    fun removeProgressItem(adapter: ProductsAdapter) {
        adapter.products.removeAt(adapter.itemCount - 1)
        adapter.notifyItemRemoved(adapter.itemCount)
    }
}

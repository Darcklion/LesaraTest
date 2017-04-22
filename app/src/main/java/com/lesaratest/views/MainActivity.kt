package com.lesaratest.views

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lesaratest.App
import com.lesaratest.R
import com.lesaratest.models.Product
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), ProductsView {

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
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!presenter.canLoadMore() || presenter.isLoading)
                    return
                val firstVisible = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val total = recyclerView.adapter.itemCount
                if (total - firstVisible < 20)
                    presenter.loadMore()
            }
        })
    }

    override fun onLoadProducts(products: List<Product>) {
        (recyclerView.adapter as ProductsAdapter).products = ArrayList(products)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onLoadMore(products: List<Product>) {
        (recyclerView.adapter as ProductsAdapter).products.addAll(ArrayList(products))
        recyclerView.adapter.notifyDataSetChanged()
    }
}

package com.lesaratest.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.lesaratest.App
import com.lesaratest.R
import com.lesaratest.models.Product
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ProductsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        App.productsManager.bindView(this)
    }

    override fun onLoadProducts(products: List<Product>) {
        Toast.makeText(this, "Loaded", Toast.LENGTH_LONG).show()
    }

    override fun onLoadMore(products: List<Product>) {

    }
}

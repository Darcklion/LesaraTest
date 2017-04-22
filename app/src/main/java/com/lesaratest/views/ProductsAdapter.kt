package com.lesaratest.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lesaratest.R
import com.lesaratest.models.Product
import java.util.*

class ProductsAdapter(context: MainActivity) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)
    var products: ArrayList<Product> = ArrayList(0)
    var canLoadMore = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(inflater.inflate(R.layout.product_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val thumbnail: GlideImageView = view.findViewById(R.id.thumbnail) as GlideImageView
        val title: TextView = view.findViewById(R.id.title) as TextView
        val price: TextView = view.findViewById(R.id.price) as TextView

        fun bind(position: Int){
            val product: Product = products[position]

            thumbnail.setImageUrl(product.thumbnail_path)
            title.text = product.name
            price.text = product.price.toString()
        }
    }
}

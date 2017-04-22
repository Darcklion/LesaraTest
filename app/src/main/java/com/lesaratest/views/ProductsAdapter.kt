package com.lesaratest.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.lesaratest.R
import com.lesaratest.models.Product
import java.util.*

class ProductsAdapter(context: MainActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_PRODUCT = 1
        val VIEW_PROGRESS = 0
    }

    var inflater: LayoutInflater = LayoutInflater.from(context)
    var products: ArrayList<Product?> = ArrayList(0)

    override fun getItemViewType(position: Int): Int {
        return if (products[position] == null) VIEW_PROGRESS else VIEW_PRODUCT
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_PROGRESS -> return ProgressViewHolder(inflater.inflate(R.layout.progress_layout, parent, false))
            else -> return ProductsViewHolder(inflater.inflate(R.layout.product_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductsViewHolder)
            holder.bind(position)
        else
            (holder as ProgressViewHolder).progress.isIndeterminate = true
    }

    override fun getItemCount(): Int = products.size

    inner class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val thumbnail: GlideImageView = view.findViewById(R.id.thumbnail) as GlideImageView
        val title: TextView = view.findViewById(R.id.title) as TextView
        val price: TextView = view.findViewById(R.id.price) as TextView

        fun bind(position: Int){
            val product: Product? = products[position]

            thumbnail.setImageUrl(product!!.thumbnail_path)
            title.text = product.name
            price.text = product.price.toString()
        }
    }

    inner class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progress: ProgressBar = view.findViewById(R.id.progressBar) as ProgressBar
    }
}

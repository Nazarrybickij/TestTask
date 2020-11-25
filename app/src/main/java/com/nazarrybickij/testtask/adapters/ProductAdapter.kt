package com.nazarrybickij.testtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.testtask.ProductEntity
import com.nazarrybickij.testtask.R
import com.squareup.picasso.Picasso
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class ProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var callback: AdapterCallback
    var listProducts = mutableListOf<ProductEntity>()

    interface AdapterCallback {
        fun onItemClick(productEntity: ProductEntity)
        fun onFavClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).onBind(position)
    }

    override fun getItemCount() = listProducts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val name: TextView = itemView.findViewById(R.id.name_item)
        private val imageItem: ImageView = itemView.findViewById(R.id.image_item)
        private val ratingBar: MaterialRatingBar = itemView.findViewById(R.id.decimal_rating_bar_item)
        private val ratingTextView: TextView = itemView.findViewById(R.id.rating_text_item)
        private val priceTextView: TextView = itemView.findViewById(R.id.price_item)

        init {
            itemView.setOnClickListener(this)
//            itemView.fav_in_item.setOnClickListener(this)
        }

        fun onBind(position: Int) {
//            if (DBRepository.getInstance().isContain()) {
//                itemView.fav_in_item.setImageResource(R.drawable.star_selected_icon)
//            } else {
//                itemView.fav_in_item.setImageResource(R.drawable.star_icon)
//            }
            Picasso.with(imageItem.context).load(listProducts[position].image).into(imageItem)
            name.text = listProducts[position].name
            ratingBar.rating = listProducts[position].reviews_rating.toFloat()
            ratingTextView.text = "(${listProducts[position].reviews})"
            priceTextView.text = "${'$'} ${listProducts[position].price}"

        }

        @Suppress("SENSELESS_COMPARISON")
        override fun onClick(v: View?) {
            if (callback != null) {
                when (v) {
//                    itemView.fav_in_item -> callback.onFavClick()
                    itemView -> callback.onItemClick(listProducts[layoutPosition])
                }
            }
        }

    }


}
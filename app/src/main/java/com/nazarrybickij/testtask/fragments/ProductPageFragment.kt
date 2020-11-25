package com.nazarrybickij.testtask.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.nazarrybickij.testtask.ProductEntity
import com.nazarrybickij.testtask.R
import com.squareup.picasso.Picasso
import me.zhanghai.android.materialratingbar.MaterialRatingBar


class ProductPageFragment : Fragment() {
    private lateinit var productEntity: ProductEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            productEntity = arguments?.getParcelable("product")!!
        } catch (e: Exception) {
        }
        return inflater.inflate(R.layout.fragment_product_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData(view)
        view.findViewById<ImageView>(R.id.cross_image).setOnClickListener {
            activity?.onBackPressed()
        }
        view.findViewById<FrameLayout>(R.id.drop_desc_frame).setOnClickListener {
            val descLayout = view.findViewById<LinearLayout>(R.id.description_layout)
            val dropImage = view.findViewById<ImageView>(R.id.drop_desc_image)
            if (descLayout.visibility == LinearLayout.GONE){
                descLayout.visibility = LinearLayout.VISIBLE
                dropImage.animate().rotation(180F)
                return@setOnClickListener
            }
            descLayout.visibility = LinearLayout.GONE
            dropImage.animate().rotation(0F)
        }
    }

    private fun setData(view: View) {
        val name: TextView = view.findViewById(R.id.name_page)
        val image: ImageView = view.findViewById(R.id.image_in_page)
        val ratingBar: MaterialRatingBar = view.findViewById(R.id.decimal_rating_bar_page)
        val ratingText: TextView = view.findViewById(R.id.rating_text_page)
        val sizeSpinner: Spinner = view.findViewById(R.id.size_spinner)
        val colorSpinner: Spinner = view.findViewById(R.id.color_spinner)
        val description: TextView = view.findViewById(R.id.description_text)
        val productCode: TextView = view.findViewById(R.id.product_code_text)
        val category: TextView = view.findViewById(R.id.category_text)
        val material: TextView = view.findViewById(R.id.material_text)
        val country: TextView = view.findViewById(R.id.country_text)
        val colorFrame: FrameLayout = view.findViewById(R.id.color_preview_frame)
        name.text = productEntity.name
        Picasso.with(context).load(productEntity.image).into(image)
        ratingBar.rating = productEntity.reviews_rating.toFloat()
        ratingText.text = "(${productEntity.reviews})"
        sizeSpinner.adapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_list_item_1,
            productEntity.size
        )
        colorSpinner.adapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_list_item_1,
            productEntity.color.keys.toList()
        )
        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    colorFrame.setBackgroundColor(
                        Color.parseColor(
                            productEntity.color[parent.getItemAtPosition(
                                position
                            ).toString()]
                        )
                    )
                }
            }
        }
        description.text = productEntity.description
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            productCode.text = Html.fromHtml(
                "Product Code: "
                        +"<strong>"
                        + productEntity.product_code
                        +"</strong>"
                , HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            category.text = Html.fromHtml(
                "Category: "
                        +"<font color=\"#5ECE7B\"><u>"
                        + productEntity.category
                        +"</u></font>"
                , HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            material.text = Html.fromHtml(
                    "Material: "
                            +"<strong>"
                            + productEntity.material
                            +"</strong>"
                    , HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            country.text = Html.fromHtml(
                "Country: "
                        +"<strong>"
                        + productEntity.country
                        +"</strong>"
                , HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }

    }
}
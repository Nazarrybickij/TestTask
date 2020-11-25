package com.nazarrybickij.testtask

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val name:String,
    val image:String,
    val country:String,
    val category:String,
    val color:Map<String,String>,
    val description:String,
    val material:String,
    val price:Float,
    val old_price:Float,
    val product_code:String,
    val reviews:Int,
    val reviews_rating: Int,
    val size:List<String>
): Parcelable {
    constructor(): this("","","", "", mapOf(),"","",0F,0F,"",0,0, mutableListOf() )}
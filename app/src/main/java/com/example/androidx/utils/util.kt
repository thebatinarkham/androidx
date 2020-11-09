package com.example.androidx.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.androidx.R

fun getProgressDrawable(context:Context):CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

//Extension function
fun ImageView.loadImage(uri:String?,progressDrawable: CircularProgressDrawable){
    val options = RequestOptions().placeholder(progressDrawable)
        .error(R.drawable.vector)

    Glide.with(context).setDefaultRequestOptions(options)
        .load(uri).into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView,uri: String?){
    view.loadImage(uri, getProgressDrawable(view.context))
}
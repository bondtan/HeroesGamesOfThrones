package com.tbondarenko.heroesgamesofthrones.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tbondarenko.heroesgamesofthrones.R

fun ImageView.load(imageAddress: String) {
    Glide.with(this)
        .load(imageAddress)
        .centerInside()
        .placeholder(R.drawable.plaseholder)
        .error(R.drawable.image_error)
        .into(this)
}

fun ImageView.loadCenterInside(imageAddress: String) {
        Glide.with(this)
        .load(imageAddress)
        .centerInside()
        .error(R.drawable.image_error)
        .into(this)
}
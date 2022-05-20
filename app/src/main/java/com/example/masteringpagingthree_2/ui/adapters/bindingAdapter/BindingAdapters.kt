package com.example.masteringpagingthree_2.ui.adapters.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("setPhoto")
fun ImageView.setPhoto(url: String) {
    load(url)
}
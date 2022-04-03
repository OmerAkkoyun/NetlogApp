package com.example.xooicase.common

import android.widget.TextView
import androidx.databinding.BindingAdapter



@BindingAdapter("loadCount")
fun loadCount(view: TextView, count: Int) {
    view.text = count.toString()
}

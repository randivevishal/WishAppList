package com.vishalrandive.wishlauncher

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter

class BinderManager: BaseObservable() {
    companion object{
        @JvmStatic
        @BindingAdapter("appIconDrawable")
        fun setImageViewDrawable(imageView: AppCompatImageView, drawable: Drawable) {
            imageView.setImageDrawable(drawable)
        }
    }
}
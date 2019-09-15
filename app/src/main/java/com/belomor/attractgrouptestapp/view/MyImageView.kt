package com.belomor.attractgrouptestapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.belomor.attractgrouptestapp.utils.Utils

class MyImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    var asyncTaskImage : Utils.AsyncTaskImage? = null
}
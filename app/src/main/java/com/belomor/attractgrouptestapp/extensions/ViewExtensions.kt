package com.belomor.attractgrouptestapp.extensions

import android.os.AsyncTask
import android.view.View
import com.belomor.attractgrouptestapp.utils.Utils
import com.belomor.attractgrouptestapp.view.MyImageView


fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun MyImageView.loadImageByURL(url : String) {
    asyncTaskImage = Utils.AsyncTaskImage(this)
    asyncTaskImage!!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url)
}

fun MyImageView.cancelAsyncTaskImage() {
    asyncTaskImage?.let {
        it.cancel(true)
    }

    asyncTaskImage = null
}

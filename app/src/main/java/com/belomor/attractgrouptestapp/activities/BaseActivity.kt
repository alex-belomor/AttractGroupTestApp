package com.belomor.attractgrouptestapp.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getLayoutId() : Int

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        onCreated(savedInstanceState)
    }

    open fun onCreated(savedInstanceState: Bundle?) {

    }
}
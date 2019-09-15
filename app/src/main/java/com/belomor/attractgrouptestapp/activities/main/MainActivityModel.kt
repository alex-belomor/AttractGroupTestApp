package com.belomor.attractgrouptestapp.activities.main

import android.util.Log
import com.belomor.attractgrouptestapp.network.HttpAttractListener
import com.belomor.attractgrouptestapp.network.ServiceAPI
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class MainActivityModel : Model {

    override fun getList(httpListener: HttpAttractListener) {
        ServiceAPI.getListData().enqueue(httpListener)
    }


}
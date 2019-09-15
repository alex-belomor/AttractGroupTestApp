package com.belomor.attractgrouptestapp.activities.main

import com.belomor.attractgrouptestapp.network.HttpAttractListener
import com.belomor.attractgrouptestapp.network.ServiceAPI
import okhttp3.Call
import java.lang.Exception

class MainActivityModel : Model {

    private val calls = ArrayList<Call>()

    override fun getList(httpListener: HttpAttractListener) {
        val call = ServiceAPI.getListData()
        calls.add(call)
        call.enqueue(httpListener)
    }

    fun cancelAll() {
        for (call in calls.iterator()) {
            try {
                call.cancel()
            }catch (ex : Exception) {
                ex.printStackTrace()
            }
        }
    }


}
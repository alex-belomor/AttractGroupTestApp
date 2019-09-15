package com.belomor.attractgrouptestapp.network

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

object ServiceAPI {

    val httpClient = OkHttpClient()

    fun init() {

    }

    fun getListData() : Call {
        val request = Request.Builder()
            .url("https://itunes.apple.com/search?term=jack+johnson")
            .build()

        return httpClient.newCall(request)
    }

}
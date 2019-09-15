package com.belomor.attractgrouptestapp.network

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

object ServiceAPI {

    private val httpClient = OkHttpClient()

    fun init() {
        //TODO There is can add something useful
    }

    fun getListData() : Call {
        val request = Request.Builder()
            .url("https://itunes.apple.com/search?term=jack+johnson")
            .build()

        return httpClient.newCall(request)
    }

}
package com.belomor.attractgrouptestapp.activities.main

import com.belomor.attractgrouptestapp.models.AttractGroupModel
import com.belomor.attractgrouptestapp.network.HttpAttractListener

interface View {
    fun isLoading(loading : Boolean)
    fun onNetworkFailure(message : String)
    fun onGetList(dataList : ArrayList<AttractGroupModel>)
}

interface Model {
    fun getList(httpListener: HttpAttractListener)
}

interface Presenter {
    fun onCreate()
    fun onDestroy()
    fun onStart()
    fun onStop()
    fun getList()
}
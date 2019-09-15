package com.belomor.attractgrouptestapp.activities.main

import com.belomor.attractgrouptestapp.models.AttractGroupModel
import com.belomor.attractgrouptestapp.network.HttpAttractListener

class MainActivityPresenter(val view: View) : Presenter {

    private val model = MainActivityModel()

    override fun onCreate() {

    }

    override fun onDestroy() {
        model.cancelAll()
    }

    override fun getList() {
        view.isLoading(true)

        model.getList(object : HttpAttractListener() {
            override fun onSuccess(attractList: ArrayList<AttractGroupModel>) {
                view.isLoading(false)
                view.onGetList(attractList)
            }

            override fun onFailure(message: String) {
                view.isLoading(false)
                view.onNetworkFailure(message)
            }
        })
    }
}
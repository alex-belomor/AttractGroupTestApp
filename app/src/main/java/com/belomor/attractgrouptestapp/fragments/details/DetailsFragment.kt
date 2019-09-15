package com.belomor.attractgrouptestapp.fragments.details

import android.os.Bundle
import android.view.View
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.extensions.cancelAsyncTaskImage
import com.belomor.attractgrouptestapp.extensions.loadImageByURL
import com.belomor.attractgrouptestapp.extensions.toStringDate
import com.belomor.attractgrouptestapp.fragments.BaseFragment
import com.belomor.attractgrouptestapp.models.AttractGroupModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_details

    var element : AttractGroupModel? = null

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        element?.let {
            imageView.loadImageByURL(it.image)
            name.text = it.name
            date.text = it.date.toStringDate()
            info.text = it.info
        }
    }

    fun setData(element : AttractGroupModel) {
        this.element = element
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageView?.cancelAsyncTaskImage()
    }
}
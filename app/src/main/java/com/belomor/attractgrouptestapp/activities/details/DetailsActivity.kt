package com.belomor.attractgrouptestapp.activities.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.activities.BaseActivity
import com.belomor.attractgrouptestapp.fragments.details.DetailsFragment
import com.belomor.attractgrouptestapp.models.AttractGroupModel
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_details

    private val EXTRA_POSITION = "extra_position"
    private val EXTRA_DATA_LIST = "extra_data_list"

    private lateinit var pagerAdapter: PagerAdapter

    private var dataList: ArrayList<AttractGroupModel>? = null

    var currentPosition = 0

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        if (savedInstanceState == null) {
            dataList = intent.getParcelableArrayListExtra<AttractGroupModel>(EXTRA_DATA_LIST)
            currentPosition = intent.getIntExtra(EXTRA_POSITION, 0)
        } else {
            dataList = savedInstanceState.getParcelableArrayList(EXTRA_DATA_LIST)
            currentPosition = savedInstanceState.getInt(EXTRA_POSITION)
        }

        pagerAdapter = PagerAdapter(supportFragmentManager, dataList!!)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = currentPosition

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(EXTRA_DATA_LIST, dataList)
        outState?.putInt(EXTRA_POSITION, currentPosition)
        super.onSaveInstanceState(outState)
    }

    inner class PagerAdapter(
        fm: FragmentManager?,
        private val listData: ArrayList<AttractGroupModel>
    ) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment = DetailsFragment()

            fragment.setData(listData[position])

            return fragment
        }

        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
        }

        override fun getCount(): Int {
            return listData.size
        }
    }
}
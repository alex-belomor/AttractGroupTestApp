package com.belomor.attractgrouptestapp.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.activities.BaseActivity
import com.belomor.attractgrouptestapp.activities.details.DetailsActivity
import com.belomor.attractgrouptestapp.adapters.main.MainListAdapter
import com.belomor.attractgrouptestapp.extensions.gone
import com.belomor.attractgrouptestapp.extensions.show
import com.belomor.attractgrouptestapp.interfaces.DefaultRecyclerViewClickListener
import com.belomor.attractgrouptestapp.models.AttractGroupModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), View {

    private val EXTRA_POSITION = "extra_position"
    private val EXTRA_DATA_LIST = "extra_data_list"

    private val BUNDLE_LIST_STATE = "bundle_list_state"
    private val BUNDLE_ADAPTER_STATE = "bundle_list_state"

    private lateinit var presenter: MainActivityPresenter

    private lateinit var adapter: MainListAdapter

    lateinit var hamburgerToggle: ActionBarDrawerToggle

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)

        setupToolbar()

        presenter = MainActivityPresenter(this)
        presenter.onCreate()

        adapter = MainListAdapter()
        list.adapter = adapter

        adapter.listener = object : DefaultRecyclerViewClickListener {
            override fun onClick(pos: Int) {
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra(EXTRA_POSITION, pos)
                intent.putExtra(EXTRA_DATA_LIST, adapter.getData())
                startActivity(intent)
            }
        }

        handleSavedInstanceState(savedInstanceState)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        when (toolbar.tag as String) {
            "normal_portrait", "normal_landscape", "large_portrait" -> {
                hamburgerToggle =
                    ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close)

                drawer_layout.addDrawerListener(hamburgerToggle)

                hamburgerToggle.syncState()

                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                supportActionBar?.setDisplayShowHomeEnabled(false)
            }

            "large_landscape", "xlarge_portrait", "xlarge_landscape" -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun handleSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            presenter.getList()
        } else {
            savedInstanceState.getParcelableArrayList<AttractGroupModel>(BUNDLE_ADAPTER_STATE)
                ?.let {
                    adapter.setData(it)
                }
            list.layoutManager?.onRestoreInstanceState(
                savedInstanceState.getParcelable(
                    BUNDLE_LIST_STATE
                )
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val listState = list.layoutManager?.onSaveInstanceState()
        outState?.putParcelable(BUNDLE_LIST_STATE, listState)
        outState?.putParcelableArrayList(BUNDLE_ADAPTER_STATE, adapter.getData())
    }

    override fun isLoading(loading: Boolean) {
        if (loading) {
            loader.show()
            failure.gone()
            list.gone()
        } else {
            loader.gone()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onNetworkFailure(message: String) {
        failure.text = message
        failure.show()
    }

    override fun onGetList(dataList: ArrayList<AttractGroupModel>) {
        adapter.setData(dataList)
        list.show()
    }
}

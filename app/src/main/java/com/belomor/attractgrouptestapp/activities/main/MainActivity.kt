package com.belomor.attractgrouptestapp.activities.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
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

    private var dataList: ArrayList<AttractGroupModel>? = null
    private var dataListFiltered = ArrayList<AttractGroupModel>()

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
                    ActionBarDrawerToggle(
                        this,
                        drawer_layout,
                        toolbar,
                        R.string.open,
                        R.string.close
                    )

                drawer_layout.addDrawerListener(hamburgerToggle)

                hamburgerToggle.syncState()

                supportActionBar?.setDisplayHomeAsUpEnabled(false)

            }

            "large_landscape", "xlarge_portrait", "xlarge_landscape" -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )

        searchView.setOnCloseListener {
            dataList?.let {
                adapter.setData(it)
                nothing_found.gone()
            }
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dataListFiltered = dataList?.filter { model ->
                    model.name.contains(
                        newText!!,
                        true
                    )
                } as ArrayList<AttractGroupModel>
                adapter.setData(dataListFiltered)

                if (dataListFiltered.size == 0)
                    nothing_found.show()
                else
                    nothing_found.gone()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            presenter.getList()
        } else {
            savedInstanceState.getParcelableArrayList<AttractGroupModel>(BUNDLE_ADAPTER_STATE)
                ?.let {
                    dataList = it
                    adapter.setData(dataList!!)
                } ?: run {
                presenter.getList()
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
        outState?.putParcelableArrayList(BUNDLE_ADAPTER_STATE, dataList)
    }

    override fun isLoading(loading: Boolean) {
        if (loading) {
            loader.show()
            failure.gone()
            nothing_found.gone()
            list.gone()
        } else {
            loader.gone()
        }
    }

    override fun onNetworkFailure(message: String) {
        failure.text = message
        failure.show()
    }

    override fun onGetList(dl: ArrayList<AttractGroupModel>) {
        this.dataList = dl
        adapter.setData(dataList!!)
        list.show()
    }
}

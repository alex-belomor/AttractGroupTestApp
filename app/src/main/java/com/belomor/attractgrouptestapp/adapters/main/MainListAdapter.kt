package com.belomor.attractgrouptestapp.adapters.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.extensions.cancelAsyncTaskImage
import com.belomor.attractgrouptestapp.extensions.loadImageByURL
import com.belomor.attractgrouptestapp.extensions.toStringDate
import com.belomor.attractgrouptestapp.interfaces.DefaultRecyclerViewClickListener
import com.belomor.attractgrouptestapp.models.AttractGroupModel
import com.belomor.attractgrouptestapp.view.MyImageView

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListViewHolder>() {

    private val dataList = ArrayList<AttractGroupModel>()

    var listener : DefaultRecyclerViewClickListener? = null

    fun setData(dataList: ArrayList<AttractGroupModel>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun getData() : ArrayList<AttractGroupModel> {
        return dataList
    }

    fun setOnClickListener(listener : DefaultRecyclerViewClickListener) {
        this.listener = listener
    }

    override fun onViewRecycled(holder: MainListViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.findViewById<MyImageView>(R.id.imageview).cancelAsyncTaskImage()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
        return MainListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        if (dataList.size > 0) {
            holder.bindData(dataList[position], position)
        }
    }

    inner class MainListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pos = 0

        init {
            listener?.let {
                itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        it.onClick(pos)
                    }
                })
            }
        }

        fun bindData(element : AttractGroupModel, pos : Int) {
            this.pos = pos

            itemView.findViewById<AppCompatTextView>(R.id.name)?.text = element.name
            itemView.findViewById<MyImageView>(R.id.imageview)?.loadImageByURL(element.image)
            itemView.findViewById<AppCompatTextView>(R.id.date)?.text = element.date.toStringDate()
        }
    }

}
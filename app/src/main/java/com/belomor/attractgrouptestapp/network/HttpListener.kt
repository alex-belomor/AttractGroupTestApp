package com.belomor.attractgrouptestapp.network

import android.os.Handler
import android.os.Looper
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.models.AttractGroupModel
import com.belomor.attractgrouptestapp.utils.Utils
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException


abstract class HttpAttractListener : Callback {

    var mainHandler = Handler(Looper.getMainLooper())

    final override fun onFailure(call: Call, e: IOException) {
        mainHandler.post {
            onFailure(e.toString())
        }
    }

    final override fun onResponse(call: Call, response: Response) {
        mainHandler.post {
            try {
                val jsonRaw = Utils.parseFileToJson(R.raw.sample_file)
                val listData = convertJSONToObject(jsonRaw)

                onSuccess(listData)
            } catch (ex: Exception) {
                onFailure(ex.toString())
            }
        }
    }

    private fun convertJSONToObject(string: String): ArrayList<AttractGroupModel> {
        val listData = ArrayList<AttractGroupModel>()
        try {
            val jsonArray = JSONArray(string)

            for (i in 0 until jsonArray.length()) {
                val element = jsonArray.getJSONObject(i)
                listData.add(
                    AttractGroupModel(
                        element.getString("name"),
                        element.getLong("date"),
                        element.getString("info"),
                        element.getString("image")
                    )
                )
            }
        } catch (ex: JSONException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return listData
    }

    abstract fun onSuccess(attractList: ArrayList<AttractGroupModel>)

    abstract fun onFailure(message: String)
}
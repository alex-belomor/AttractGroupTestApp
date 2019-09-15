package com.belomor.attractgrouptestapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import com.belomor.attractgrouptestapp.R
import com.belomor.attractgrouptestapp.view.MyImageView
import java.io.*
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


@SuppressLint("StaticFieldLeak")
object Utils {

    private lateinit var context : Context

    fun init(context : Context) {
        this.context = context
    }

    fun parseFileToJson(id : Int) : String {
        val file = context.resources.openRawResource(id)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        file.use { file ->
            val reader = BufferedReader(InputStreamReader(file, "UTF-8"))
            var n: Int
            n = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        }

        return writer.toString()
    }

    class AsyncTaskImage(imageView : MyImageView) : AsyncTask<String, Void, Bitmap>() {

        private var weakReferenceImageView: WeakReference<ImageView> = WeakReference(imageView)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceImageView.get()?.setImageBitmap(null)
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            var input: InputStream? = null
            try {

                val url = URL(params[0])
                val connection = url
                    .openConnection() as HttpURLConnection
                var input = connection.inputStream

                val baos = ByteArrayOutputStream()
                val byteChunk = ByteArray(4096)

                var n = input.read(byteChunk)
                while (n > 0) {
                    if (isCancelled) {
                        return null
                    }
                    baos.write(byteChunk, 0, n)
                    n = input.read(byteChunk)
                }

                return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size())
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            } finally {
                try {
                    input?.close()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                weakReferenceImageView.get()?.setImageBitmap(it)
            }
        }
    }
}
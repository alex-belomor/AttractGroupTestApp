package com.belomor.attractgrouptestapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Environment
import android.widget.ImageView
import com.belomor.attractgrouptestapp.view.MyImageView
import java.io.*
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


@SuppressLint("StaticFieldLeak")
object Utils {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun parseFileToJson(id: Int): String {
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

    class AsyncTaskImage(imageView: MyImageView) : AsyncTask<String, Void, Bitmap>() {

        private var weakReferenceImageView: WeakReference<ImageView> = WeakReference(imageView)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceImageView.get()?.setImageBitmap(null)
        }

        fun getBitmapFromReturnedImage(
            byteArray: ByteArray,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap? {

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            val nameOfImage = params[0]
                ?.replace("/", "_")
                ?.replace(":", "_")
                ?.replace(".", "_")

            if (isSavedBitmap(nameOfImage!!)) {
                return BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + File.separator + nameOfImage)
            }

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

                val bm = getBitmapFromReturnedImage(baos.toByteArray(), 200, 200)

                saveBitmap(bm!!, nameOfImage!!)

                return bm
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

        fun saveBitmap(bitmap: Bitmap, name: String) {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes)
            val f = File(Environment.getExternalStorageDirectory().toString() + File.separator + name)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            fo.close()
        }

        fun isSavedBitmap(name : String) : Boolean {
            val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + name)
            return file.exists()
        }

        override fun onPostExecute(result: Bitmap?) {
            result?.let {
                weakReferenceImageView.get()?.setImageBitmap(it)
            }
        }
    }
}
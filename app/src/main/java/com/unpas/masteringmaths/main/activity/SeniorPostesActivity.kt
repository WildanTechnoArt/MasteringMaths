package com.unpas.masteringmaths.main.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_pdf_view.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.ByteArrayInputStream
import java.io.IOException

class SeniorPostesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Postes"

        swipe_refresh?.setOnRefreshListener {
            showContent()
        }
        showContent()
    }

    private fun showContent() {
        when (intent.getIntExtra(UtilsConstant.NUMBER_INDEX, 0)) {
            0 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Kaidah%20Pencacahan%2FPOSTES.pdf?alt=media&token=6db9c1c3-61a7-469b-8717-f28f34bf114a")
            }
            1 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Matrix%2Fpostesfull.pdf?alt=media&token=988f29c6-8e76-4dd5-8b49-00a7a9bd7eef")
            }
        }
    }

    private fun loadContent(link: String) {
        swipe_refresh?.isRefreshing = true

        val client = OkHttpClient()
        val request = Request.Builder().url(link).build()

        client.newCall(request).enqueue(object : Callback {
            @Throws(IOException::class)
            override fun onResponse(response: Response?) {
                if (!response?.isSuccessful!!) {
                    swipe_refresh?.isRefreshing = false
                    throw IOException("Failed to download file: $response")
                } else {
                    swipe_refresh?.isRefreshing = false
                }

                val stream = ByteArrayInputStream(response.body()?.bytes())
                pdfView.fromStream(stream)
                    .load()
            }

            override fun onFailure(request: Request?, e: IOException?) {
                swipe_refresh?.isRefreshing = false
                e?.printStackTrace()
            }
        })
    }
}
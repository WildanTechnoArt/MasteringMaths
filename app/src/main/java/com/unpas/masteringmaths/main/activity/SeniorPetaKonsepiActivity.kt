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

class SeniorPetaKonsepiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Peta Konsep"

        swipe_refresh?.setOnRefreshListener {
            showContent()
        }
        showContent()
    }

    private fun showContent() {
        when (intent.getIntExtra(UtilsConstant.NUMBER_INDEX, 0)) {
            0 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Kaidah%20Pencacahan%2Fpetakonsep.pdf?alt=media&token=69f0f7d3-f7eb-4bd6-a153-5f5ba042ca8f")
            }
            1 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Matrix%2FPETA%20KONSEP.pdf?alt=media&token=2c155a12-89ac-4e3f-af24-540e70db15ca")
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
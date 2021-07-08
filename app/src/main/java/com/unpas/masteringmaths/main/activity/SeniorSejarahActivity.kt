package com.unpas.masteringmaths.main.activity

import android.annotation.SuppressLint
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

class SeniorSejarahActivity : AppCompatActivity() {
    private var number = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "History"
        swipe_refresh?.setOnRefreshListener {
            showContent()
        }
        showContent()
    }

    @SuppressLint("SetTextI18n")
    private fun showContent() {
        number = intent.getIntExtra(UtilsConstant.NUMBER_INDEX, 0)
        when (number) {
            0 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Kaidah%20Pencacahan%2Fsejarah.pdf?alt=media&token=78d1c17b-645c-4d1a-8cd3-a00cac3d12a6")
            }
            1 -> {
                loadContent("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Matrix%2Fsejarah.pdf?alt=media&token=4b59f65a-f565-4d4f-b91f-f7c16ea76fcb")
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
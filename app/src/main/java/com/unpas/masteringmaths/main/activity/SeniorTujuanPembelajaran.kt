package com.unpas.masteringmaths.main.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_senior_mas_kon.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.io.ByteArrayInputStream
import java.io.IOException

class SeniorTujuanPembelajaran : AppCompatActivity() {

    private var tvMateriNo: TextView? = null
    private var position = 0
    private var indext = 0
    private var btnNext: Button? = null
    private var btnprev: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_senior_tujuan_pembelajaran)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tujuan Pembelajaran"

        btnNext = findViewById(R.id.bt_next)
        btnprev = findViewById(R.id.btn_prev)
        tvMateriNo = findViewById(R.id.no_materi)
        swipe_refresh?.setOnRefreshListener {
            showContent()
        }
        showContent()
    }

    @SuppressLint("SetTextI18n")
    private fun showContent() {
        position = intent.getIntExtra(UtilsConstant.NUMBER_INDEX, 0)
        when (position) {
            // Kaidah Pencacahan
            0 -> {
                loadContent(0, getString(R.string.kaidah_pencacahan_tujpel_membilang))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++
                        loadContent(indext, getString(R.string.kaidah_pencacahan_tujpel_membilang))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.kaidah_pencacahan_tujpel_membilang))
                    }
                }
            }
            1 -> {
                loadContent(0, getString(R.string.tujpel_permutasi))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++
                        loadContent(indext, getString(R.string.tujpel_permutasi))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.tujpel_permutasi))
                    }
                }
            }
            2 -> {
                loadContent(0, getString(R.string.tujpel_kombinasi))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++
                        loadContent(indext, getString(R.string.tujpel_kombinasi))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.tujpel_kombinasi))
                    }
                }
            }

            // Matriks
            3 -> {
                loadContent(0, getString(R.string.tujpel_ordo_matriks))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++
                        loadContent(indext, getString(R.string.tujpel_ordo_matriks))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.tujpel_ordo_matriks))
                    }
                }
            }
            4 -> {
                loadContent(0, getString(R.string.tujpel_determinan))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++
                        loadContent(indext, getString(R.string.tujpel_determinan))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.tujpel_determinan))
                    }
                }
            }
            5 -> {
                loadContent(0, getString(R.string.tujpel_operasi_matriks))
                btnNext?.setOnClickListener {
                    if (pdfView.currentPage+1 != pdfView.pageCount) {
                        indext = pdfView.currentPage
                        indext++ 
                        loadContent(indext, getString(R.string.tujpel_operasi_matriks))
                    }
                }
                btnprev?.setOnClickListener {
                    if (pdfView.currentPage+1 != 1) {
                        indext = pdfView.currentPage
                        indext--
                        loadContent(indext, getString(R.string.tujpel_operasi_matriks))
                    }
                }
            }
        }
    }

    private fun loadContent(page: Int, link: String) {
        swipe_refresh?.isRefreshing = true

        val client = OkHttpClient()
        val request = Request.Builder().url(link).build()

        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("SetTextI18n")
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
                    .defaultPage(page)
                    .onPageChange { page, pageCount ->
                        tvMateriNo!!.text = "Page : ${page+1}/$pageCount"
                    }
                    .load()
            }

            override fun onFailure(request: Request?, e: IOException?) {
                swipe_refresh?.isRefreshing = false
                e?.printStackTrace()
            }
        })
    }
}
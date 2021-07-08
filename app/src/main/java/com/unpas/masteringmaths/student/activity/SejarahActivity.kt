package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_in_materi.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*

class SejarahActivity : AppCompatActivity() {

    private var listSlide = arrayListOf<String>()
    private var nomor = 1
    private var indext = 0
    private var number = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_materi)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Theory"

        no_materi.text = "Page : 1/1"
        bt_next.setOnClickListener {
            if (indext < listSlide.size -1) {
                indext++
                nomor++
                content_materi.loadUrl(listSlide[indext])
                no_materi.text = "Page : $nomor/1"
            }
        }
        addData()
    }

    override fun onStart() {
        super.onStart()
        content_materi?.loadUrl(listSlide[indext])
    }

    @SuppressLint("SetTextI18n")
    private fun addData() {
        number = intent.getIntExtra(UtilsConstant.NUMBER_INDEX, 0)
        listSlide = ArrayList()
        when (number) {
            0 -> {
                var a = 1
                while (a <= 1) {
                    listSlide.add("file:///android_asset/sejarah_relasi_fungsi_$a.html")
                    a++
                }
            }
            1 -> {
                no_materi?.text = "Page : 1/2"
                bt_next?.setOnClickListener { _: View? ->
                    if (indext < listSlide.size - 1) {
                        indext++
                        nomor++
                        content_materi?.loadUrl(listSlide[indext])
                        no_materi?.text = "Page : $nomor/2"
                    }
                }
                bt_next?.setOnClickListener {
                    if (indext > 0) {
                        indext--
                        nomor--
                        content_materi?.loadUrl(listSlide[indext])
                        no_materi?.text = "Page : $nomor/2"
                    }
                }
                var a = 1
                while (a <= 2) {
                    listSlide.add("file:///android_asset/sejarah_pythagoras_$a.html")
                    a++
                }
            }
            2 -> {
                var a = 1
                while (a <= 1) {
                    listSlide.add("file:///android_asset/sejarah_polabilangan_$a.html")
                    a++
                }
            }
            3 -> {
                var a = 1
                while (a <= 1) {
                    listSlide.add("file:///android_asset/sejarah_garislurus_$a.html")
                    a++
                }
            }
            4 -> {
                var a = 1
                while (a <= 1) {
                    listSlide.add("file:///android_asset/sejarah_kartesius_$a.html")
                    a++
                }
            }
            5 -> {
                var a = 1
                while (a <= 1) {
                    listSlide.add("file:///android_asset/sejarah_spldv_$a.html")
                    a++
                }
            }
        }
    }
}
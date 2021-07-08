package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_student_kompetesi.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class KompetensiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_kompetesi)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Kompetensi Dasar"

        main_content.settings.builtInZoomControls = true

        when (intent.getIntExtra(UtilsConstant.COMPETENT_NUMBER, 0)) {
            0 -> main_content.loadUrl("file:///android_asset/relasi_komdas.html")
            1 -> main_content.loadUrl("file:///android_asset/pythagoras_komdas.html")
            2 -> main_content.loadUrl("file:///android_asset/pola_bilangan_komdas.html")
            3 -> main_content.loadUrl("file:///android_asset/garis_lurus_komdas.html")
            4 -> main_content.loadUrl("file:///android_asset/cartesius_komdas.html")
            5 -> main_content.loadUrl("file:///android_asset/spldv_komdas.html")
        }
    }
}
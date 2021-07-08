package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_student_kompetesi.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class StudentKonsepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_kompetesi)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Peta Konsep"

        main_content.settings.builtInZoomControls = true
        val competentNumber =
            intent.getIntExtra(UtilsConstant.COMPETENT_NUMBER, 0)

        when (competentNumber) {
            0 -> main_content.loadUrl("file:///android_asset/petakonseprelasi.html")
            1 -> main_content.loadUrl("file:///android_asset/petakonseppythagoras.html")
            2 -> main_content.loadUrl("file:///android_asset/petakonseppolabilangan.html")
            3 -> main_content.loadUrl("file:///android_asset/petakonsepgarislurus.html")
            4 -> main_content.loadUrl("file:///android_asset/petakonsepkartesius.html")
            5 -> main_content.loadUrl("file:///android_asset/petakonsepspldv.html")
        }
    }
}
package com.unpas.masteringmaths.student.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.toolbar_layout.*

class StudentMateriActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_materi)

        val layS = findViewById<RelativeLayout>(R.id.lay_sejarah)
        val layR = findViewById<RelativeLayout>(R.id.lay_MateriR)
        val layF = findViewById<RelativeLayout>(R.id.lay_MateriF)

        layS.setOnClickListener { view: View? ->
            val intent = Intent(this@StudentMateriActivity, TujuanPembelajaran::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, 12)
            startActivity(intent)
        }
        layR.setOnClickListener { view: View? ->
            val intent = Intent(this@StudentMateriActivity, MasKonActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, 13)
            startActivity(intent)
        }
        layF.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    this@StudentMateriActivity,
                    InMateriActivity::class.java
                )
            )
        }
    }
}
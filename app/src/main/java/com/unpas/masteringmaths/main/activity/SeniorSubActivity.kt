package com.unpas.masteringmaths.main.activity

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.utils.UtilsConstant

class SeniorSubActivity : AppCompatActivity() {

    private var laySIndex = 0
    private var layRIndex = 0
    private var layFIndex = 0
    private var index: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_materi)

        val layS = findViewById<RelativeLayout>(R.id.lay_sejarah)
        val layR = findViewById<RelativeLayout>(R.id.lay_MateriR)
        val layF = findViewById<RelativeLayout>(R.id.lay_MateriF)
        numberIndex

        layS.setOnClickListener {
            val intent = Intent(this@SeniorSubActivity, SeniorTujuanPembelajaran::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, laySIndex)
            startActivity(intent)
        }
        layR.setOnClickListener {
            val intent = Intent(this@SeniorSubActivity, SeniorMasKonActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, layRIndex)
            startActivity(intent)
        }
        layF.setOnClickListener {
            val intent = Intent(this@SeniorSubActivity, SeniorMateriActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, layFIndex)
            startActivity(intent)
        }
    }

    private val numberIndex: Unit
        get() {
            index = intent.getStringExtra(UtilsConstant.SUB_ACTIVITY)
            when (index) {
                // Kaidah Pencacahan
                getString(R.string.teknik_membilang) -> {
                    laySIndex = 0
                    layRIndex = 0
                    layFIndex = 0
                }
                getString(R.string.permutasi) -> {
                    laySIndex = 1
                    layRIndex = 1
                    layFIndex = 1
                }
                getString(R.string.kombinasi) -> {
                    laySIndex = 2
                    layRIndex = 2
                    layFIndex = 2
                }

                // Matriks
                getString(R.string.ordo_matriks) -> {
                    laySIndex = 3
                    layRIndex = 3
                    layFIndex = 3
                }
                getString(R.string.determinan_invers_spl) -> {
                    laySIndex = 4
                    layRIndex = 4
                    layFIndex = 4
                }
                getString(R.string.operasi_matriks) -> {
                    laySIndex = 5
                    layRIndex = 5
                    layFIndex = 5
                }
            }
        }
}
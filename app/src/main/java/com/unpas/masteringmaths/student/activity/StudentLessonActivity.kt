package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.sub_menu.*

class StudentLessonActivity : AppCompatActivity() {

    private var numberLesson: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_lesson)
        numberLesson = intent.getIntExtra(UtilsConstant.NUMBER_LESSON, 0)
        initLesson()
        when (numberLesson) {
            0 -> Relasi()
            1 -> Pythagoras()
            2 -> PolaBilangan()
            3 -> PersamaanGaris()
            4 -> KoordinatCartesius()
            5 -> Spldv()
        }
    }

    private fun initLesson () {
        GlideApp.with(this).load(R.drawable.tutwuri).into(img_pembelajaran)
        GlideApp.with(this).load(R.drawable.indikator).into(img_indikator)
        GlideApp.with(this).load(R.drawable.materi).into(img_rangkuman)
        GlideApp.with(this).load(R.drawable.referensi).into(img_referensi)
        GlideApp.with(this).load(R.drawable.admin_jurnalpase).into(img_postes)
        GlideApp.with(this).load(R.drawable.latihan).into(img_soal)
        GlideApp.with(this).load(R.drawable.quize).into(img_kuis)

        card_pembelajaran.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, KompetensiActivity::class.java)
            intent.putExtra(UtilsConstant.COMPETENT_NUMBER, numberLesson)
            startActivity(intent)
        }
        card_indikator.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, StudentKonsepActivity::class.java)
            intent.putExtra(UtilsConstant.COMPETENT_NUMBER, numberLesson)
            startActivity(intent)
        }
        card_sejarah.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, SejarahActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }


        card_relasi.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_relasi.text.toString())
            startActivity(intent)
        }
        card_rumus_fungsi.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_rumus_fungsi.text.toString())
            startActivity(intent)
        }

        card_1.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_1.text.toString())
            startActivity(intent)
        }

        card_fungsi.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_fungsi.text.toString())
            startActivity(intent)
        }
        card_rangkuman.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, RangkumanActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }
        card_referensi.setOnClickListener {
            val intent = Intent(this@StudentLessonActivity, ReferensiRelasi::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX,numberLesson)
            startActivity(intent)
        }
        card_postes.setOnClickListener {

            val intent = Intent(this@StudentLessonActivity, PostesRelasi::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX,numberLesson)
            startActivity(intent)
        }
        card_soal.setOnClickListener {
            when (numberLesson){
                0 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketRelasiActivity::class.java)
                    )
                }
                1 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketPythagoras::class.java)
                    )
                }
                2 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketPolaBilanganActivity::class.java)
                    )
                }
                3 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketPersamaanGaris::class.java)
                    )
                }
                4 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketKordinat::class.java)
                    )
                }
                5 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, PaketSpldv::class.java)
                    )
                }
            }

        }
        card_kuis.setOnClickListener { view: View? ->
            when (numberLesson){
                0 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, SudentQuizeActivity::class.java)
                    )
                }
                1 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, QuizePythagorasActivity::class.java)
                    )
                }
                2 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, QuizePolaBilangan::class.java)
                    )
                }
                3 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, QuizePersamaanGaris::class.java)
                    )
                }
                4 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, QuizeKordinat::class.java)
                    )
                }
                5 -> {
                    startActivity(
                        Intent(this@StudentLessonActivity, QuizeSpldv::class.java)
                    )
                }
            }
        }
    }

    private fun Relasi() {
        GlideApp.with(this).load(R.drawable.biografisejarahrelasi).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.relasi).into(img_relasi)
        GlideApp.with(this).load(R.drawable.fungsi).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.rumus).into(img_rumus_fungsi)
    }

    @SuppressLint("SetTextI18n")
    private fun Pythagoras() {
        laytambah.visibility = View.VISIBLE
        card_2.visibility = View.INVISIBLE
        tv_relasi.text = "Teorema Pythagoras"
        tv_rumus_fungsi.text = "Segitiga Siku-Siku Khusus"
        tv_fungsi.text = "Tripel Pythagoras"
        tv_1.text = "Penerapan Teorema Pythagoras"

        GlideApp.with(this).load(R.drawable.sejarahphythagoras).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.teoremapyithagoras).into(img_relasi)
        GlideApp.with(this).load(R.drawable.tripelpythagoras).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.segitgakhusus).into(img_rumus_fungsi)
        GlideApp.with(this).load(R.drawable.penerapanteorema).into(img_1)
    }

    @SuppressLint("SetTextI18n")
    private fun PolaBilangan() {
        tv_relasi.text = "Barisan Bilangan"
        tv_fungsi.text = "Barisan dan Deret Aritmatika"
        tv_rumus_fungsi.text = "Barisan dan Deret Geometri"

        GlideApp.with(this).load(R.drawable.sejarahpolabilangan).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.barisanbilangan).into(img_relasi)
        GlideApp.with(this).load(R.drawable.deretaritmatika).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.barisangeometri).into(img_rumus_fungsi)
    }

    @SuppressLint("SetTextI18n")
    private fun PersamaanGaris() {
        tv_relasi.text = "Kemiringan"
        tv_fungsi.text = "Persamaan Garis"
        tv_rumus_fungsi.text = "Kedudukan Dua Garis"

        GlideApp.with(this).load(R.drawable.sejarahgaris).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.garislurus).into(img_relasi)
        GlideApp.with(this).load(R.drawable.garismiring).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.duagaris).into(img_rumus_fungsi)
    }

    @SuppressLint("SetTextI18n")
    private fun KoordinatCartesius() {
        tv_relasi.text = "Koordinat Cartesius"
        tv_fungsi.text = "Posisi Titik"
        tv_rumus_fungsi.text = "Posisi Garis"

        GlideApp.with(this).load(R.drawable.sejarahkoordinat).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.koordinat).into(img_relasi)
        GlideApp.with(this).load(R.drawable.posisititik).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.posisigaris).into(img_rumus_fungsi)
    }

    @SuppressLint("SetTextI18n")
    private fun Spldv() {
        laytambah.visibility = View.VISIBLE
        card_2.visibility = View.INVISIBLE
        tv_relasi.text = "Bentuk SPLDV"
        tv_fungsi.text = "Metode Eleminasi"
        tv_rumus_fungsi.text = "Metode Grafik"
        tv_1.text = "Metode Subtitusi"

        GlideApp.with(this).load(R.drawable.sejarahspldv).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.spldv).into(img_relasi)
        GlideApp.with(this).load(R.drawable.metodeeliminasi).into(img_fungsi)
        GlideApp.with(this).load(R.drawable.metodegrafik).into(img_rumus_fungsi)
        GlideApp.with(this).load(R.drawable.metodesubstitusi).into(img_1)
    }
}
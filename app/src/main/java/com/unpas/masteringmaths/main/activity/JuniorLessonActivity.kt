package com.unpas.masteringmaths.main.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.*
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.sub_menu.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class JuniorLessonActivity : AppCompatActivity() {

    private var numberLesson: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_lesson)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Main Menu"
        numberLesson = intent.getIntExtra(UtilsConstant.NUMBER_LESSON, 0)
        setMarqueeTextView()
        initLesson()
        when (numberLesson) {
            0 -> relasi()
            1 -> pythagoras()
            2 -> polaBilangan()
            3 -> persamaanGaris()
            4 -> koordinatCartesius()
            5 -> spldv()
        }
    }

    private fun setMarqueeTextView() {
        tv_materi_satu.isSelected = true
        tv_materi_tiga.isSelected = true
        tv_materi_dua.isSelected = true
        tv_1.isSelected = true
        tv_kompetensi.isSelected = true
        tv_indikator.isSelected = true
        tv_sejarah.isSelected = true
        tv_rangkuman.isSelected = true
        tv_referensi.isSelected = true
        tv_postes.isSelected = true
        tv_soal.isSelected = true
        tv_kuis.isSelected = true
        tv_2.isSelected = true
    }

    private fun initLesson() {
        GlideApp.with(this).load(R.drawable.tutwuri).into(img_kompetensi)
        GlideApp.with(this).load(R.drawable.indikator).into(img_indikator)
        GlideApp.with(this).load(R.drawable.materi).into(img_rangkuman)
        GlideApp.with(this).load(R.drawable.referensi).into(img_referensi)
        GlideApp.with(this).load(R.drawable.admin_jurnalpase).into(img_postes)
        GlideApp.with(this).load(R.drawable.latihan).into(img_soal)
        GlideApp.with(this).load(R.drawable.quize).into(img_kuis)

        card_kompetensi.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, KompetensiActivity::class.java)
            intent.putExtra(UtilsConstant.COMPETENT_NUMBER, numberLesson)
            startActivity(intent)
        }

        card_indikator.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, StudentKonsepActivity::class.java)
            intent.putExtra(UtilsConstant.COMPETENT_NUMBER, numberLesson)
            startActivity(intent)
        }

        card_sejarah.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, SejarahActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_materi_satu.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_satu.text.toString())
            startActivity(intent)
        }

        card_materi_tiga.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_tiga.text.toString())
            startActivity(intent)
        }

        card_1.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_1.text.toString())
            startActivity(intent)
        }

        card_materi_dua.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_dua.text.toString())
            startActivity(intent)
        }

        card_rangkuman.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, RangkumanActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_referensi.setOnClickListener {
            val intent = Intent(this@JuniorLessonActivity, ReferensiRelasi::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_postes.setOnClickListener {

            val intent = Intent(this@JuniorLessonActivity, PostesRelasi::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_soal.setOnClickListener {
            when (numberLesson) {
                0 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketRelasiActivity::class.java)
                    )
                }
                1 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketPythagoras::class.java)
                    )
                }
                2 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketPolaBilanganActivity::class.java)
                    )
                }
                3 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketPersamaanGaris::class.java)
                    )
                }
                4 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketKordinat::class.java)
                    )
                }
                5 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, PaketSpldv::class.java)
                    )
                }
            }

        }

        card_kuis.setOnClickListener {
            when (numberLesson) {
                0 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, SudentQuizeActivity::class.java)
                    )
                }
                1 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, QuizePythagorasActivity::class.java)
                    )
                }
                2 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, QuizePolaBilangan::class.java)
                    )
                }
                3 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, QuizePersamaanGaris::class.java)
                    )
                }
                4 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, QuizeKordinat::class.java)
                    )
                }
                5 -> {
                    startActivity(
                        Intent(this@JuniorLessonActivity, QuizeSpldv::class.java)
                    )
                }
            }
        }
    }

    private fun relasi() {
        GlideApp.with(this).load(R.drawable.biografisejarahrelasi).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.relasi).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.fungsi).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.rumus).into(img_materi_tiga)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTQCwHk6fxf8D4gOgzc6G2UR")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pythagoras() {
        card_1.visibility = View.VISIBLE
        tv_materi_satu.text = "Teorema Pythagoras"
        tv_materi_tiga.text = "Segitiga Siku-Siku Khusus"
        tv_materi_dua.text = "Tripel Pythagoras"
        tv_1.text = "Penerapan Teorema Pythagoras"

        GlideApp.with(this).load(R.drawable.sejarahphythagoras).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.teoremapyithagoras).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.tripelpythagoras).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.segitgakhusus).into(img_materi_tiga)
        GlideApp.with(this).load(R.drawable.penerapanteorema).into(img_1)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTQyge8b9k9ZhLSkAbrhHC8V")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun polaBilangan() {
        tv_materi_satu.text = "Barisan Bilangan"
        tv_materi_dua.text = "Barisan dan Deret Aritmatika"
        tv_materi_tiga.text = "Barisan dan Deret Geometri"

        GlideApp.with(this).load(R.drawable.sejarahpolabilangan).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.barisanbilangan).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.deretaritmatika).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.barisangeometri).into(img_materi_tiga)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTQ6RBHc-DFq_qVIHo06ymxG")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun persamaanGaris() {
        tv_materi_satu.text = "Kemiringan"
        tv_materi_dua.text = "Persamaan Garis"
        tv_materi_tiga.text = "Kedudukan Dua Garis"

        GlideApp.with(this).load(R.drawable.sejarahgaris).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.garislurus).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.garismiring).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.duagaris).into(img_materi_tiga)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTQp0m9cYvrowtV9izJYErAw")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun koordinatCartesius() {
        tv_materi_satu.text = "Koordinat Cartesius"
        tv_materi_dua.text = "Posisi Titik"
        tv_materi_tiga.text = "Posisi Garis"

        GlideApp.with(this).load(R.drawable.sejarahkoordinat).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.koordinat).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.posisititik).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.posisigaris).into(img_materi_tiga)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTRg3rwW8qrNiYTlmXOf0DaK")
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun spldv() {
        card_1.visibility = View.VISIBLE
        tv_materi_satu.text = "Bentuk SPLDV"
        tv_materi_dua.text = "Metode Eleminasi"
        tv_materi_tiga.text = "Metode Grafik"
        tv_1.text = "Metode Subtitusi"

        GlideApp.with(this).load(R.drawable.sejarahspldv).into(img_sejarah)
        GlideApp.with(this).load(R.drawable.spldv).into(img_materi_satu)
        GlideApp.with(this).load(R.drawable.metodeeliminasi).into(img_materi_dua)
        GlideApp.with(this).load(R.drawable.metodegrafik).into(img_materi_tiga)
        GlideApp.with(this).load(R.drawable.metodesubstitusi).into(img_1)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)
        card_video.setOnClickListener {
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra(UtilsConstant.PLAYLIST_LINK, "PLkGmWOmjVFTQBYoKH9iz8qJ1_i2pTlCNm")
            startActivity(intent)
        }
    }
}
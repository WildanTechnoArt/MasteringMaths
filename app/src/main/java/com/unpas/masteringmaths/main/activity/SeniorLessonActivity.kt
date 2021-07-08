package com.unpas.masteringmaths.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.*
import com.unpas.masteringmaths.utils.UtilsConstant
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.PLAYLIST_LINK
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.THEORY
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TITLE_BAR
import kotlinx.android.synthetic.main.activity_student_lesson.*
import kotlinx.android.synthetic.main.new_sub_menu.*
import kotlinx.android.synthetic.main.sub_menu.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class SeniorLessonActivity : AppCompatActivity() {

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
            0 -> pencacahan()
            1 -> matriks()
            2 -> peluang()
            3 -> barisDanDeret()
            4 -> limitFungsi()
            5 -> turunanFungsi()
            6 -> tigaDimensi()
            7 -> trigonometri()
            8 -> transformasi()
        }
    }

    private fun setMarqueeTextView() {
        tv_materi_satu.isSelected = true
        tv_materi_tiga.isSelected = true
        tv_materi_dua.isSelected = true
        tv_first_lesson.isSelected = true
        tv_second_lesson.isSelected = true
        tv_third_lesson.isSelected = true
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
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_materi_satu)
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_materi_dua)
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_materi_tiga)
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_first_lesson)
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_second_lesson)
        GlideApp.with(this).load(getString(R.string.icon_math_url)).into(img_third_lesson)

        GlideApp.with(this).load(getString(R.string.icon_tutwuri)).into(img_kompetensi)
        GlideApp.with(this).load(getString(R.string.icon_peta_konsep)).into(img_indikator)
        GlideApp.with(this).load(getString(R.string.icon_materi)).into(img_rangkuman)
        GlideApp.with(this).load(getString(R.string.icon_referensi)).into(img_referensi)
        GlideApp.with(this).load(getString(R.string.icon_postest)).into(img_postes)
        GlideApp.with(this).load(getString(R.string.icon_latihan)).into(img_soal)
        GlideApp.with(this).load(getString(R.string.icon_quize)).into(img_kuis)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video)

        GlideApp.with(this).load(getString(R.string.icon_latihan)).into(img_exercises)
        GlideApp.with(this).load(getString(R.string.icon_quize)).into(img_quiz_lesson)
        GlideApp.with(this).load(getString(R.string.icon_video)).into(img_video_lesson)

        card_kompetensi.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorKompetensiActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_indikator.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorPetaKonsepiActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_sejarah.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorSejarahActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_materi_satu.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorSubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_satu.text.toString())
            startActivity(intent)
        }

        card_materi_dua.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorSubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_dua.text.toString())
            startActivity(intent)
        }

        card_materi_tiga.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorSubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_materi_tiga.text.toString())
            startActivity(intent)
        }

        card_1.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SubActivity::class.java)
            intent.putExtra(UtilsConstant.SUB_ACTIVITY, tv_1.text.toString())
            startActivity(intent)
        }

        card_rangkuman.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorRangkumanActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_referensi.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorReferensiActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_postes.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, SeniorPostesActivity::class.java)
            intent.putExtra(UtilsConstant.NUMBER_INDEX, numberLesson)
            startActivity(intent)
        }

        card_soal.setOnClickListener {
            when (numberLesson) {
                0 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                1 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                2 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                3 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                4 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                5 -> {
                    Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

        card_kuis.setOnClickListener {
            when (numberLesson) {
                0 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                1 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                2 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                3 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                4 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
                5 -> {
                    Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun pencacahan() {
        main_sub_menu.visibility = VISIBLE
        new_main_sub_menu.visibility = GONE

        card_video.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }

        tv_materi_satu.text = getString(R.string.teknik_membilang)
        tv_materi_dua.text = getString(R.string.permutasi)
        tv_materi_tiga.text = getString(R.string.kombinasi)

        GlideApp.with(this).load(getString(R.string.img_sejarah_pencacahan)).into(img_sejarah)
    }

    private fun matriks() {
        main_sub_menu.visibility = VISIBLE
        new_main_sub_menu.visibility = GONE

        card_video.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, VideoPlayerActivity::class.java)
            intent.putExtra(PLAYLIST_LINK, "PLC-uYqYvk3NIoTYQ0f0SqLbVMSvoWa8cZ")
            startActivity(intent)
        }

        tv_materi_satu.text = getString(R.string.ordo_matriks)
        tv_materi_dua.text = getString(R.string.determinan_invers_spl)
        tv_materi_tiga.text = getString(R.string.operasi_matriks)

        GlideApp.with(this).load(getString(R.string.img_sejarah_matrix)).into(img_sejarah)
    }

    private fun peluang() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_third_lesson.visibility = GONE
        val param = card_video_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_video_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.peluang_materi_1)
        tv_second_lesson.text = getString(R.string.peluang_materi_2)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/MateriPeluang%2FBahan%20Ajar%20Materi%20Peluang%20baru%20chapter%201.pdf?alt=media&token=43b96285-8d73-4d0e-b9fd-3df487f6705d")
            startActivity(intent)
        }

        card_second_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_second_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/MateriPeluang%2FBahan%20Ajar%20Materi%20Peluang%20baru%20chapter%202.pdf?alt=media&token=37d93311-6e3f-41f9-b422-4d04047815df")
            startActivity(intent)
        }
    }

    private fun barisDanDeret() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        tv_first_lesson.text = getString(R.string.barisderet_materi_1)
        tv_second_lesson.text = getString(R.string.barisderet_materi_2)
        tv_third_lesson.text = getString(R.string.barisderet_materi_3)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/baris%20dan%20deret%2FBahan%20ajar%20Baris%20%26%20deret%20aritmatika.pdf?alt=media&token=0a04fe77-f21a-4496-9097-97d929933ef7")
            startActivity(intent)
        }

        card_second_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_second_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/baris%20dan%20deret%2FBahan%20ajar%20Baris%20%26%20deret%20Geometri.pdf?alt=media&token=196f1bc9-99f5-4f9a-af24-107e73a69c18")
            startActivity(intent)
        }

        card_third_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_third_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/baris%20dan%20deret%2FBahan%20ajar%20postest.pdf?alt=media&token=c42ccbf8-dc2c-407c-b607-e8d81d217847")
            startActivity(intent)
        }
    }

    private fun limitFungsi() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_second_lesson.visibility = GONE
        val param1 = card_second_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param1.marginEnd = 0
        card_second_lesson.layoutParams = param1

        card_third_lesson.visibility = GONE
        val param = card_third_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_third_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.limit_fungsi_materi_1)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/materi%20limit%20fungsi%2FPPT%20Limit%20Fungsi%20_irma.pdf?alt=media&token=9051d2d4-8c8d-42a9-ae02-b2581b0d7ce7")
            startActivity(intent)
        }
    }

    private fun turunanFungsi() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_third_lesson.visibility = GONE
        val param = card_video_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_video_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.turunan_fungsi_materi_1)
        tv_second_lesson.text = getString(R.string.turunan_fungsi_materi_2)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Turunan%20Fungsi%2FBAHAN%20AJAR%20TURUNAN%20FUNGSI%20BAG%201.pdf?alt=media&token=b110deda-9901-46ba-9714-9b6e153688fb")
            startActivity(intent)
        }

        card_second_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_second_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Turunan%20Fungsi%2FBAHAN%20AJAR%20TURUNAN%20FUNGSI%20BAG%202.pdf?alt=media&token=0de42487-d1c5-4592-8446-abe6c8a02599")
            startActivity(intent)
        }
    }

    private fun tigaDimensi() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_second_lesson.visibility = GONE
        val param1 = card_second_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param1.marginEnd = 0
        card_second_lesson.layoutParams = param1

        card_third_lesson.visibility = GONE
        val param = card_third_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_third_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.tiga_dimensi_materi_1)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Tiga_Dimensi%2FBAHAN%20AJAR%20JADI_SARKANI.pdf?alt=media&token=8d95b369-d0b2-427d-8bfb-50b815af53dd")
            startActivity(intent)
        }
    }

    private fun trigonometri() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_second_lesson.visibility = GONE
        val param1 = card_second_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param1.marginEnd = 0
        card_second_lesson.layoutParams = param1

        card_third_lesson.visibility = GONE
        val param = card_third_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_third_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.trigonometri_materi_1)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Trigonometri%2FBAHAN%20AJAR%20TRIGONOMETRI%202%20KD.pdf?alt=media&token=c1eab488-e20f-4b55-9e0a-7b812c17f628")
            startActivity(intent)
        }
    }

    private fun transformasi() {
        new_main_sub_menu.visibility = VISIBLE
        main_sub_menu.visibility = GONE

        card_video_lesson.setOnClickListener {
            Toast.makeText(this, "No video can be played yet", Toast.LENGTH_SHORT).show()
        }
        card_quiz_lesson.setOnClickListener {
            Toast.makeText(this, "The quiz is not yet accessible", Toast.LENGTH_SHORT).show()
        }
        card_exercises.setOnClickListener {
            Toast.makeText(this, "The question is not yet accessible", Toast.LENGTH_SHORT).show()
        }

        card_second_lesson.visibility = GONE
        val param1 = card_second_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param1.marginEnd = 0
        card_second_lesson.layoutParams = param1

        card_third_lesson.visibility = GONE
        val param = card_third_lesson.layoutParams as ViewGroup.MarginLayoutParams
        param.marginStart = 0
        card_third_lesson.layoutParams = param

        tv_first_lesson.text = getString(R.string.transformasi_materi_1)

        card_first_lesson.setOnClickListener {
            val intent = Intent(this@SeniorLessonActivity, TheoryViewActivity::class.java)
            intent.putExtra(TITLE_BAR, tv_first_lesson.text.toString())
            intent.putExtra(THEORY, "https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/Transformasi%2Fbahan%20ajar%20Westi%20Transformasi%20revisi.pdf?alt=media&token=376e20a4-3325-4a78-a879-4875fd5a719f")
            startActivity(intent)
        }
    }
}
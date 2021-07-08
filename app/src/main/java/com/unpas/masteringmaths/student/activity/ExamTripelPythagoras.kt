package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import kotlinx.android.synthetic.main.activity_student_exam.*
import kotlinx.android.synthetic.main.imge_answer.*
import kotlinx.android.synthetic.main.text_answer.*
import java.util.*

class ExamTripelPythagoras : AppCompatActivity() {

    private var listSoal = ArrayList<String>()
    private var answerA = ArrayList<String>()
    private var answerB = ArrayList<String>()
    private var answerC = ArrayList<String>()
    private var answerD = ArrayList<String>()
    private var kunciJawaban = ArrayList<String>()
    private var listAnswerChecked = ArrayList<String>()
    private var listIsAnswer = ArrayList<Boolean>()
    private var totalAnswer = 0
    private var isFinish = false
    private var kondisi = false
    private var myAnswer: String? = null
    private var nomor = 1
    private var indext = 0
    private var point = 0
    private var benar = 0
    private var salah = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_exam)
        init()
        addData()
        addAnswerA()
        addAnswerB()
        addAnswerC()
        addAnswerD()
        addKunciJawaban()
        btnAnswerListener()

        btn_answer.setOnClickListener {
            if (listIsAnswer[indext]) {
                btn_answer.isEnabled = false
            }

            if (kondisi) {
                if (myAnswer == kunciJawaban[indext]) {
                    Toast.makeText(applicationContext, "Benar", Toast.LENGTH_SHORT).show()
                    listAnswerChecked[indext] = myAnswer.toString()
                    point += 10
                    benar += 1
                    totalAnswer += 1
                } else {
                    listAnswerChecked[indext] = myAnswer.toString()
                    salah += 1
                    totalAnswer += 1
                    Toast.makeText(applicationContext, "Salah", Toast.LENGTH_SHORT).show()
                }

                if (totalAnswer == 10) {
                    btn_answer.text = getString(R.string.btn_finish)
                    isFinish = true
                }

                if (isFinish) {
                    val intent =
                        Intent(
                            this,
                            StudentResultExamActivity::class.java
                        )
                    intent.putExtra(StudentExamActivity.MY_POINT, point)
                    intent.putExtra(StudentExamActivity.TRUE_ANSWER, benar)
                    intent.putExtra(StudentExamActivity.FALSE_ANSWER, salah)
                    startActivity(intent)
                    finish()
                } else {
                    listIsAnswer[indext] = true

                    if (nomor == 10) {
                        btn_answer.isEnabled = !listIsAnswer[indext]
                    } else {
                        indext++
                        nomor++
                        btnMoveListener()
                    }
                    kondisi = false
                }

                btn_prev.isEnabled = nomor != 1
                btn_next.isEnabled = nomor != 10
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Pilih Jawaban Terlebih Dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btn_prev.setOnClickListener {
            indext--
            nomor--
            btnMoveListener()
            btn_prev.isEnabled = nomor != 1
            btn_next.isEnabled = nomor != 10
        }

        btn_next.setOnClickListener {
            indext++
            nomor++
            btnMoveListener()
            btn_next.isEnabled = nomor != 10
            btn_prev.isEnabled = nomor != 1
        }

        tv_answerA.text = answerA[0]
        tv_answerB.text = answerB[0]
        tv_answerC.text = answerC[0]
        tv_answerD.text = answerD[0]
    }

    @SuppressLint("SetTextI18n")
    private fun btnMoveListener() {
        btn_answer.isEnabled = !listIsAnswer[indext]

        if (nomor == 10) {
            main_answer_img.visibility = View.VISIBLE
            main_answer_text.visibility = View.GONE
            GlideApp.with(applicationContext)
                .load(answerA[indext])
                .into(img_answer_A)
            GlideApp.with(applicationContext)
                .load(answerB[indext])
                .into(img_answer_B)
            GlideApp.with(applicationContext)
                .load(answerC[indext])
                .into(img_answer_C)
            GlideApp.with(applicationContext)
                .load(answerD[indext])
                .into(img_answer_D)
            btnAnsImgChecked()
        } else {
            main_answer_img.visibility = View.GONE
            main_answer_text.visibility = View.VISIBLE
            tv_answerA.text = answerA[indext]
            tv_answerB.text = answerB[indext]
            tv_answerC.text = answerC[indext]
            tv_answerD.text = answerD[indext]
            btnAnsTxtChecked()
        }
        content_soal.loadUrl(listSoal[indext])
        no_soal.text = "No Soal : $nomor/10"
    }

    private fun init() {
        btn_prev.isEnabled = false

        for (isAnswer in 1..10) {
            listIsAnswer.add(false)
        }

        for (checkedAnswer in 1..10) {
            listAnswerChecked.add("null")
        }
    }

    private fun btnAnsImgChecked() {
        when {
            listAnswerChecked[indext] == "A" -> {
                setColorImg(
                    "#0F82F7", "#F1F1F1", "#F1F1F1", "#F1F1F1",
                    "#F1F1F1", "#FF757575", "#FF757575", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "B" -> {
                setColorImg(
                    "#F1F1F1", "#0F82F7", "#F1F1F1", "#F1F1F1",
                    "#FF757575", "#F1F1F1", "#FF757575", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "C" -> {
                setColorImg(
                    "#F1F1F1", "#F1F1F1", "#0F82F7", "#F1F1F1",
                    "#FF757575", "#FF757575", "#F1F1F1", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "D" -> {
                setColorImg(
                    "#F1F1F1", "#F1F1F1", "#F1F1F1", "#0F82F7",
                    "#FF757575", "#FF757575", "#FF757575", "#F1F1F1"
                )
            }
            else -> {
                defaultColorImg()
            }
        }
    }

    private fun btnAnsTxtChecked() {
        when {
            listAnswerChecked[indext] == "A" -> {
                setColorText(
                    "#0F82F7", "#F1F1F1", "#F1F1F1", "#F1F1F1",
                    "#F1F1F1", "#FF757575", "#FF757575", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "B" -> {
                setColorText(
                    "#F1F1F1", "#0F82F7", "#F1F1F1", "#F1F1F1",
                    "#FF757575", "#F1F1F1", "#FF757575", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "C" -> {
                setColorText(
                    "#F1F1F1", "#F1F1F1", "#0F82F7", "#F1F1F1",
                    "#FF757575", "#FF757575", "#F1F1F1", "#FF757575"
                )
            }
            listAnswerChecked[indext] == "D" -> {
                setColorText(
                    "#F1F1F1", "#F1F1F1", "#F1F1F1", "#0F82F7",
                    "#FF757575", "#FF757575", "#FF757575", "#F1F1F1"
                )
            }
            else -> {
                defaultColorText()
            }
        }
    }

    private fun btnAnswerListener() {
        getAnsImgA.setOnClickListener {
            myAnswer = "A"
            kondisi = true
            setColorImg(
                "#0F82F7", "#F1F1F1", "#F1F1F1", "#F1F1F1",
                "#F1F1F1", "#FF757575", "#FF757575", "#FF757575"
            )
        }

        getAnsImgB.setOnClickListener {
            myAnswer = "B"
            kondisi = true
            setColorImg(
                "#F1F1F1", "#0F82F7", "#F1F1F1", "#F1F1F1",
                "#FF757575", "#F1F1F1", "#FF757575", "#FF757575"
            )
        }

        getAnsImgC.setOnClickListener {
            myAnswer = "C"
            kondisi = true
            setColorImg(
                "#F1F1F1", "#F1F1F1", "#0F82F7", "#F1F1F1",
                "#FF757575", "#FF757575", "#F1F1F1", "#FF757575"
            )
        }

        getAnsImgD.setOnClickListener {
            myAnswer = "D"
            kondisi = true
            setColorImg(
                "#F1F1F1", "#F1F1F1", "#F1F1F1", "#0F82F7",
                "#FF757575", "#FF757575", "#FF757575", "#F1F1F1"
            )
        }

        getAnsTextA.setOnClickListener {
            myAnswer = "A"
            kondisi = true
            setColorText(
                "#0F82F7", "#F1F1F1", "#F1F1F1", "#F1F1F1",
                "#F1F1F1", "#FF757575", "#FF757575", "#FF757575"
            )
        }

        getAnsTextB.setOnClickListener {
            myAnswer = "B"
            kondisi = true
            setColorText(
                "#F1F1F1", "#0F82F7", "#F1F1F1", "#F1F1F1",
                "#FF757575", "#F1F1F1", "#FF757575", "#FF757575"
            )
        }

        getAnsTextC.setOnClickListener {
            myAnswer = "C"
            kondisi = true
            setColorText(
                "#F1F1F1", "#F1F1F1", "#0F82F7", "#F1F1F1",
                "#FF757575", "#FF757575", "#F1F1F1", "#FF757575"
            )
        }

        getAnsTextD.setOnClickListener {
            myAnswer = "D"
            kondisi = true
            setColorText(
                "#F1F1F1", "#F1F1F1", "#F1F1F1", "#0F82F7",
                "#FF757575", "#FF757575", "#FF757575", "#F1F1F1"
            )
        }
    }

    private fun defaultColorText() {
        char_TextA?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_TextA?.setTextColor(Color.parseColor("#FF757575"))
        char_TextB?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_TextB?.setTextColor(Color.parseColor("#FF757575"))
        char_TextC?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_TextC?.setTextColor(Color.parseColor("#FF757575"))
        char_TextD?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_TextD?.setTextColor(Color.parseColor("#FF757575"))
    }

    private fun defaultColorImg() {
        char_ImgA?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_ImgA?.setTextColor(Color.parseColor("#FF757575"))
        char_ImgB?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_ImgB?.setTextColor(Color.parseColor("#FF757575"))
        char_ImgC?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_ImgC?.setTextColor(Color.parseColor("#FF757575"))
        char_ImgD?.setBackgroundColor(Color.parseColor("#F1F1F1"))
        char_ImgD?.setTextColor(Color.parseColor("#FF757575"))
    }

    private fun setColorText(
        BackcolorA: String,
        BackcolorB: String,
        BackcolorC: String,
        BackcolorD: String,
        CharcolorA: String,
        CharcolorB: String,
        CharcolorC: String,
        CharcolorD: String
    ) {
        char_TextA?.setBackgroundColor(Color.parseColor(BackcolorA))
        char_TextA?.setTextColor(Color.parseColor(CharcolorA))
        char_TextB?.setBackgroundColor(Color.parseColor(BackcolorB))
        char_TextB?.setTextColor(Color.parseColor(CharcolorB))
        char_TextC?.setBackgroundColor(Color.parseColor(BackcolorC))
        char_TextC?.setTextColor(Color.parseColor(CharcolorC))
        char_TextD?.setBackgroundColor(Color.parseColor(BackcolorD))
        char_TextD?.setTextColor(Color.parseColor(CharcolorD))
    }

    private fun setColorImg(
        BackcolorA: String,
        BackcolorB: String,
        BackcolorC: String,
        BackcolorD: String,
        CharcolorA: String,
        CharcolorB: String,
        CharcolorC: String,
        CharcolorD: String
    ) {
        char_ImgA?.setBackgroundColor(Color.parseColor(BackcolorA))
        char_ImgA?.setTextColor(Color.parseColor(CharcolorA))
        char_ImgB?.setBackgroundColor(Color.parseColor(BackcolorB))
        char_ImgB?.setTextColor(Color.parseColor(CharcolorB))
        char_ImgC?.setBackgroundColor(Color.parseColor(BackcolorC))
        char_ImgC?.setTextColor(Color.parseColor(CharcolorC))
        char_ImgD?.setBackgroundColor(Color.parseColor(BackcolorD))
        char_ImgD?.setTextColor(Color.parseColor(CharcolorD))
    }

    override fun onStart() {
        super.onStart()
        content_soal?.loadUrl(listSoal[indext])
    }

    private fun addData() {
        listSoal = ArrayList()
        for (a in 1..10) {
            listSoal.add("file:///android_asset/soal_tripel_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("Segitiga Lancip")
        answerA.add("(1) dan (2)")
        //jawaban no 3 dengan gambar
        answerA.add("60")
        answerA.add("2")
        answerA.add("I dan II")
        answerA.add("Segitiga Lancip")
        //jawaban no 6 dengan gambar
        answerA.add("(i), (ii), dan (iii)")
        answerA.add("(i) dan (ii)")
        answerA.add("Segitiga sebarang")
        //gambar no 9 dengan gambar
        answerA.add("5")
    }

    private fun addAnswerB() {
        answerB.add("Segitiga Siku-siku")
        answerB.add("(1) dan (3)")
        answerB.add("45")
        answerB.add("3")
        answerB.add("I dan III")
        answerB.add("Segitiga Siku-siku")
        answerB.add("(i) dan (iii)")
        answerB.add("(i) dan (iii)")
        answerB.add("Segitiga sama kaki\n")
        answerB.add("10")
    }

    private fun addAnswerC() {
        answerC.add("Segitiga Tumpul")
        answerC.add("(2) dan (3)")
        answerC.add("30")
        answerC.add("4")
        answerC.add("II dan III")
        answerC.add("Segitiga Tumpul")
        answerC.add("(ii) dan (iv)")
        answerC.add("(ii) dan (iii)")
        answerC.add("Segitiga sama sisi\n")
        answerC.add("15")
    }

    private fun addAnswerD() {
        answerD.add("Segitiga Sembarang")
        answerD.add("(3) dan (4)")
        answerD.add("15")
        answerD.add("5")
        answerD.add("I dan IV")
        answerD.add("Segitiga Sembarang")
        answerD.add("(i), (ii), (iii), dan (iv)")
        answerD.add("(iii) dan (iv)")
        answerD.add("Segitiga siku-siku")
        answerD.add("20")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("A")
        kunciJawaban.add("B")
        kunciJawaban.add("D")
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("D")
        kunciJawaban.add("B")
        kunciJawaban.add("D")
    }

    companion object {
        var MY_POINT = "my_point"
        var TRUE_ANSWER = "true_answer"
        var FALSE_ANSWER = "false_answer"
    }
}
package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import kotlinx.android.synthetic.main.activity_student_exam.*
import kotlinx.android.synthetic.main.text_answer.*
import java.util.*

class ExamGrafik : AppCompatActivity() {

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
        main_answer_img.visibility = View.GONE
        main_answer_text.visibility = View.VISIBLE
        tv_answerA.text = answerA[indext]
        tv_answerB.text = answerB[indext]
        tv_answerC.text = answerC[indext]
        tv_answerD.text = answerD[indext]
        btnAnsTxtChecked()
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

    override fun onStart() {
        super.onStart()
        content_soal.loadUrl(listSoal[indext])
    }

    private fun addData() {
        listSoal = ArrayList()
        for (a in 1..10) {
            listSoal.add("file:///android_asset/soal_grafik_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("x-y=0; 2x+y=250.000")
        answerA.add("x=1 dan y=1")
        answerA.add("-25")
        answerA.add("Rp 108.000,-")
        answerA.add("3")
        answerA.add("45 tahun")
        answerA.add("Rp 14.000,-")
        answerA.add("14 tahun")
        answerA.add("18")
        answerA.add("2 mobil A dan 6 mobil B")
    }

    private fun addAnswerB() {
        answerB.add("2x-y=0; 2x+y=250.000")
        answerB.add("x=2 dan y=0 ")
        answerB.add("-5")
        answerB.add("Rp 144.000,-")
        answerB.add("4")
        answerB.add("20 tahun")
        answerB.add("Rp 24.000,-")
        answerB.add("12 tahun")
        answerB.add("17")
        answerB.add("4 mobil A dan 4 mobil B")
    }

    private fun addAnswerC() {
        answerC.add("x=2y, 2x=y+250.000")
        answerC.add("x=0 dan y=2")
        answerC.add("5")
        answerC.add("Rp 154.000,-")
        answerC.add("5")
        answerC.add("13 tahun")
        answerC.add("Rp 36.000,-")
        answerC.add("10 tahun")
        answerC.add("-1")
        answerC.add("6 monil A dan 2 mobil B")
    }

    private fun addAnswerD() {
        answerD.add("y=2x, 2x=x+250.000")
        answerD.add("x=1 dan y=2")
        answerD.add("25")
        answerD.add("Rp 158.000,-")
        answerD.add("6")
        answerD.add("8 tahun")
        answerD.add("Rp 38.000,-")
        answerD.add("8 tahun")
        answerD.add("-6")
        answerD.add("8 mobil B")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("A")
        kunciJawaban.add("C")
        kunciJawaban.add("D")
        kunciJawaban.add("B")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("D")
        kunciJawaban.add("D")
    }

    companion object {
        var MY_POINT = "my_point"
        var TRUE_ANSWER = "true_answer"
        var FALSE_ANSWER = "false_answer"
    }
}
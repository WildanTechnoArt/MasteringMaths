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

class ExamDuaGaris : AppCompatActivity() {

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
            listSoal.add("file:///android_asset/soal_dua_garis_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("y = -x + 9")
        answerA.add("2y + 3x = 6")
        answerA.add("y = - 3/4 x + 5")
        answerA.add("3y - 2x - 11 = 0")
        answerA.add("y = x - 4")
        answerA.add("3y + x = 14")
        answerA.add("2y + 3x = -21")
        answerA.add("2x + y - 6 = 0")
        answerA.add("(18, 0)")
        answerA.add("2y - 5x = -16")
    }

    private fun addAnswerB() {
        answerB.add("y = x - 9")
        answerB.add("-2y + 3x = 6")
        answerB.add("y = 3/4 x + 5")
        answerB.add("-3y - 2x - 11 = 0")
        answerB.add("y = x + 4")
        answerB.add("3y + x = -14")
        answerB.add("2y - 3x = 21")
        answerB.add("2x - y - 6 = 0")
        answerB.add("(22, 0)")
        answerB.add("2y - 5x = 16")
    }

    private fun addAnswerC() {
        answerC.add("y = -x - 9")
        answerC.add("2y + 3x = -6")
        answerC.add("y = - 3/4 x - 7")
        answerC.add("3y + 2x - 11 = 0")
        answerC.add("y = -x - 4")
        answerC.add("3y - x = 14")
        answerC.add("2y + 3x = 21")
        answerC.add("2x - y + 6 = 0")
        answerC.add("(25, 0)")
        answerC.add("2y + 5x = -16")
    }

    private fun addAnswerD() {
        answerD.add("y = x + 9")
        answerD.add("2y - 3x = -6")
        answerD.add("y = 3/4 x - 7")
        answerD.add("3y - 2x + 11 = 0")
        answerD.add("y = -x + 4")
        answerD.add("3y - x = -14")
        answerD.add("2y - 3x = -21")
        answerD.add("2x + y + 6 = 0")
        answerD.add("(29, 0)")
        answerD.add("2y + 5x = 16")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("A")
        kunciJawaban.add("D")
        kunciJawaban.add("B")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("D")
        kunciJawaban.add("C")
        kunciJawaban.add("A")
        kunciJawaban.add("C")
        kunciJawaban.add("D")
    }

    companion object {
        var MY_POINT = "my_point"
        var TRUE_ANSWER = "true_answer"
        var FALSE_ANSWER = "false_answer"
    }
}
package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.unpas.masteringmaths.R
import kotlinx.android.synthetic.main.activity_sudent_quize.*
import kotlinx.android.synthetic.main.text_answer.*
import java.util.*
import java.util.concurrent.TimeUnit

class QuizePythagorasActivity : AppCompatActivity() {

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
    private var countDownTimer: CountDownTimer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudent_quize)
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
                    listAnswerChecked[indext] = myAnswer.toString()
                    point += 10
                    benar += 1
                    totalAnswer += 1
                } else {
                    listAnswerChecked[indext] = myAnswer.toString()
                    salah += 1
                    totalAnswer += 1
                }

                if (totalAnswer == 10) {
                    btn_answer.text = getString(R.string.btn_finish)
                    isFinish = true
                }

                if (isFinish) {
                    countDownTimer?.cancel()

                    val intent = Intent(this, StudentResultActivity::class.java)

                    intent.putExtra(MY_POINT, point)
                    intent.putExtra(TRUE_ANSWER, benar)
                    intent.putExtra(FALSE_ANSWER, salah)
                    intent.putExtra(TIMER, waktu.text.toString())
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

        countDownTimer = object : CountDownTimer(60000*30, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                @SuppressLint("DefaultLocale") val timer =
                    String.format(
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                        ),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        )
                    )
                waktu.text = timer
            }

            override fun onFinish() {
                val alert =
                    MaterialAlertDialogBuilder(applicationContext)
                alert.setMessage("Waktu Habis")
                alert.setPositiveButton(
                    "Ok"
                ) { _: DialogInterface?, _: Int -> finish() }
                alert.setCancelable(false)
                alert.create()
                alert.show()
            }
        }
        countDownTimer?.start()
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
        content_soal?.loadUrl(listSoal[indext])
    }

    private fun addData() {
        listSoal = ArrayList()
        for (a in 1..10) {
            listSoal.add("file:///android_asset/soal_pythagoras_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("Jika \uD835\uDC5F²=\uD835\uDC5D²+\uD835\uDC5E², besar ∠P = 90º")
        answerA.add("13 m")
        answerA.add("24 cm")
        answerA.add("290 \uD835\uDC50\uD835\uDC5A²")
        answerA.add("2 cm")
        answerA.add("25 cm")
        answerA.add("3")
        answerA.add("19")
        answerA.add("√1000")
        answerA.add("30 cm")
    }

    private fun addAnswerB() {
        answerB.add("Jika \uD835\uDC5F²=\uD835\uDC5D²−\uD835\uDC5E², besar ∠R = 90º")
        answerB.add("12 m")
        answerB.add("25 cm")
        answerB.add("294 \uD835\uDC50\uD835\uDC5A²")
        answerB.add("2,1 cm")
        answerB.add("24 cm")
        answerB.add("4")
        answerB.add("18")
        answerB.add("√130")
        answerB.add("40 cm")
    }

    private fun addAnswerC() {
        answerC.add("Jika \uD835\uDC5F²=\uD835\uDC5E²−\uD835\uDC5D², besar ∠ Q = 90º")
        answerC.add("11 m")
        answerC.add("26 cm")
        answerC.add("490 \uD835\uDC50\uD835\uDC5A²")
        answerC.add("2,4 cm")
        answerC.add("17 cm")
        answerC.add("5")
        answerC.add("17")
        answerC.add("√216,67")
        answerC.add("48 cm")
    }

    private fun addAnswerD() {
        answerD.add("Jika \uD835\uDC5D²=\uD835\uDC5E²+\uD835\uDC5F², besar ∠P = 90º")
        answerD.add("10 m")
        answerD.add("27 cm")
        answerD.add("494 \uD835\uDC50\uD835\uDC5A²")
        answerD.add("2,9 cm")
        answerD.add("15 cm")
        answerD.add("6")
        answerD.add("16")
        answerD.add("√260")
        answerD.add("52 cm")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("D")
        kunciJawaban.add("A")
        kunciJawaban.add("A")
        kunciJawaban.add("B")
        kunciJawaban.add("A")
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("C")
        kunciJawaban.add("A")
        kunciJawaban.add("D")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer?.cancel()
    }

    companion object {
        var MY_POINT = "my_point"
        var TRUE_ANSWER = "true_answer"
        var FALSE_ANSWER = "false_answer"
        var TIMER = "timer"
    }
}
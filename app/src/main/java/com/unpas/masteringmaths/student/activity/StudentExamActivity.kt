package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import kotlinx.android.synthetic.main.activity_student_exam.*
import kotlinx.android.synthetic.main.imge_answer.*
import kotlinx.android.synthetic.main.text_answer.*
import java.util.*

class StudentExamActivity : AppCompatActivity() {

    private var listSoal = ArrayList<String>()
    private var answerA = ArrayList<String>()
    private var answerB = ArrayList<String>()
    private var answerC = ArrayList<String>()
    private var answerD = ArrayList<String>()
    private var kunciJawaban = ArrayList<String>()
    private var listAnswerChecked = ArrayList<String>()
    private var listIsAnswer = ArrayList<Boolean>()
    private var kondisi = false
    private var isFinish = false
    private var myAnswer: String? = null
    private var nomor = 1
    private var indext = 0
    private var point = 0
    private var benar = 0
    private var salah = 0
    private var totalAnswer = 0

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
                    val alert = MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.exam_confirmation))
                        .setMessage(getString(R.string.exam_finish_information))
                        .setPositiveButton("YA") { _, _ ->
                            val intent =
                                Intent(
                                    this@StudentExamActivity,
                                    StudentResultExamActivity::class.java
                                )
                            intent.putExtra(MY_POINT, point)
                            intent.putExtra(TRUE_ANSWER, benar)
                            intent.putExtra(FALSE_ANSWER, salah)
                            startActivity(intent)
                            finish()
                        }
                        .setNegativeButton("TIDAK") { dialog, _ ->
                            dialog.dismiss()
                        }
                    alert.setCancelable(false)
                    alert.create()
                    alert.show()
                } else {
                    listIsAnswer[indext] = true
                    indext++
                    nomor++

                    btnAnsChecked()

                    if (nomor == 3 || nomor == 6 || nomor == 9) {
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
                    } else {
                        main_answer_img.visibility = View.GONE
                        main_answer_text.visibility = View.VISIBLE
                        tv_answerA.text = answerA[indext]
                        tv_answerB.text = answerB[indext]
                        tv_answerC.text = answerC[indext]
                        tv_answerD.text = answerD[indext]
                    }
                    content_soal.loadUrl(listSoal[indext])
                    no_soal.text = "No Soal : $nomor/10"

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

            btn_answer.isEnabled = !listIsAnswer[indext]

            if (nomor == 3 || nomor == 6 || nomor == 9) {
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
            } else {
                main_answer_img.visibility = View.GONE
                main_answer_text.visibility = View.VISIBLE
                tv_answerA.text = answerA[indext]
                tv_answerB.text = answerB[indext]
                tv_answerC.text = answerC[indext]
                tv_answerD.text = answerD[indext]
            }
            content_soal.loadUrl(listSoal[indext])
            no_soal.text = "No Soal : $nomor/10"
            btn_prev.isEnabled = nomor != 1
            btn_next.isEnabled = nomor != 10

            btnAnsChecked()
        }

        btn_next.setOnClickListener {
            indext++
            nomor++

            btn_answer.isEnabled = !listIsAnswer[indext]

            if (nomor == 3 || nomor == 6 || nomor == 9) {
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
            } else {
                main_answer_img.visibility = View.GONE
                main_answer_text.visibility = View.VISIBLE
                tv_answerA.text = answerA[indext]
                tv_answerB.text = answerB[indext]
                tv_answerC.text = answerC[indext]
                tv_answerD.text = answerD[indext]
            }
            content_soal.loadUrl(listSoal[indext])
            no_soal.text = "No Soal : $nomor/10"
            btn_next.isEnabled = nomor != 10
            btn_prev.isEnabled = nomor != 1

            btnAnsChecked()
        }

        tv_answerA.text = answerA[0]
        tv_answerB.text = answerB[0]
        tv_answerC.text = answerC[0]
        tv_answerD.text = answerD[0]
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

    private fun btnAnsChecked() {
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
            listSoal.add("file:///android_asset/relasi_soal_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("akar dari")
        answerA.add("{(3,2), (4,2), (5,2)}")

        //jawaban no 3 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_a.png?alt=media&token=cad5a911-3925-47dc-956f-f274bc74be75")
        answerA.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(6,3),(6,6),(1,8),(2,8),(4,8)}")
        answerA.add("daerah di Indonesia")

        //jawaban no 6 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_a.png?alt=media&token=bf67d0dd-1fcb-4901-8c92-f29b164b203d")
        answerA.add("A ke B")
        answerA.add("{(Badminton, Annisa), (Badminton, Derrall), (Badminton, Ataya), (Sepak bola, Hafidz), (Sepak bola, Wildan), (Badminton, Azis), (Basket, Azis)}")

        //gambar no 9 dengan gambar
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_a.png?alt=media&token=f326bac2-f04f-4507-b23c-d9e5aaa696d7")
        answerA.add("Tami")
    }

    private fun addAnswerB() {
        answerB.add("faktor dari")
        answerB.add("{(3,5), (4,6), (5,7)}")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_b.png?alt=media&token=a2eb98c9-1bd3-4cfe-b60e-e8149698e9f7")
        answerB.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(3,8),(4,8),(6,8)}")
        answerB.add("daerah asal tari")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_b.png?alt=media&token=e301446d-a293-4ec3-857f-2edbebfa4b39")
        answerB.add("B ke C")
        answerB.add("{(Annisa, Badminton), (Derrall, Badminton), (Ataya, Badminton), (Hafidz, Sepak bola), (Wildan, Sepak bola), (Azis, Badminton),      (Azis, Basket)}")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_b.png?alt=media&token=1ac3fa44-d738-40c7-b082-982552fd6d37")
        answerB.add("Nengsih")
    }

    private fun addAnswerC() {
        answerC.add("lebih dari")
        answerC.add("{(3,4), (4,5), (5,6)}")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_c.png?alt=media&token=59b44c66-9ace-46bd-9169-5717a6118c92")
        answerC.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(2,8),(4,8)}")
        answerC.add("tari dari")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_c.png?alt=media&token=04a92038-0068-4561-8caf-5b028e8dc9d5")
        answerC.add("A ke D")
        answerC.add("{(Badminton, Annisa), (Badminton, Derrall), (Badminton, Ataya), (Badminton, Hafidz), (Sepak bola, Wildan), (Sepak bola, Azis), (Basket, Azis)}")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_c.png?alt=media&token=608b23a4-55bc-4f15-ae5f-ddadaa7677ec")
        answerC.add("Kinanti")
    }

    private fun addAnswerD() {
        answerD.add("jumlah dari")
        answerD.add("{(3,1), (4,2), (5,3)}")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_3_d.png?alt=media&token=afa3a2f5-062b-4f71-b963-930641a3ddb4")
        answerD.add("{(1,3),(3,3),(1,4),(2,4),(4,4),(1,5),(5,5),(1,6),(2,6),(3,6),(6,6),(1,8),(2,8),(4,8),(6,8)}")
        answerD.add("himpunan nama daerah dan tariannya")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_6_d.png?alt=media&token=7686c941-3f85-4a77-9528-22aca279e319")
        answerD.add("D ke A")
        answerD.add("{(Annisa, Badminton), (Derrall, Badminton), (Ataya, Badminton), (Hafidz, Sepak bola), (Wildan, Sepak bola), (Azis, Sepak bola),       (Azis, Basket)}")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_relasi_9_d.png?alt=media&token=f99c0a3e-3285-443b-bcb0-fe72af42efb9")
        answerD.add("Suci")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("B")
        kunciJawaban.add("B")
        kunciJawaban.add("A")
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("A")
        kunciJawaban.add("D")
        kunciJawaban.add("D")
    }

    companion object {
        @JvmField
        var MY_POINT = "my_point"

        @JvmField
        var TRUE_ANSWER = "true_answer"

        @JvmField
        var FALSE_ANSWER = "false_answer"
    }
}
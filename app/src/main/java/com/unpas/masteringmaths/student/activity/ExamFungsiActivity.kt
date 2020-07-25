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

class ExamFungsiActivity : AppCompatActivity() {

    private var listcontent_soal = ArrayList<String>()
    private var nomor = 1
    private var indext = 0
    private var answerA = ArrayList<String>()
    private var answerB = ArrayList<String>()
    private var answerC = ArrayList<String>()
    private var answerD = ArrayList<String>()
    private var kunciJawaban = ArrayList<String>()
    private var kondisi = false
    private var myAnswer: String? = null
    private var point = 0
    private var benar = 0
    private var salah = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_exam)
        addData()

        addAnswerA()
        addAnswerB()
        addAnswerC()
        addAnswerD()
        addKunciJawaban()

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

        btn_answer.setOnClickListener { view: View? ->
            if (kondisi) {
                defaultColorImg()
                defaultColorText()
                if (myAnswer == kunciJawaban[indext]) {
                    Toast.makeText(applicationContext, "Benar", Toast.LENGTH_SHORT).show()
                    point += 10
                    benar += 1
                } else {
                    salah += 1
                    Toast.makeText(applicationContext, "Salah", Toast.LENGTH_SHORT).show()
                }
                if (indext < listcontent_soal.size - 1) {
                    indext++
                    nomor++
                    if (nomor == 2 || nomor == 3 || nomor == 4) {
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
                    content_soal.loadUrl(listcontent_soal[indext])
                    no_soal.text = "No content_soal : $nomor/10"
                    if (nomor == 10) {
                        btn_answer.text = "Selesai"
                    }
                    kondisi = false
                } else {
                    val intent =
                        Intent(this@ExamFungsiActivity, StudentResultExamActivity::class.java)
                    intent.putExtra(MY_POINT, point)
                    intent.putExtra(TRUE_ANSWER, benar)
                    intent.putExtra(FALSE_ANSWER, salah)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Pilih Jawaban Terlebih Dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        tv_answerA.text = answerA[0]
        tv_answerB.text = answerB[0]
        tv_answerC.text = answerC[0]
        tv_answerD.text = answerD[0]
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
        content_soal.loadUrl(listcontent_soal[indext])
    }

    private fun addData() {
        listcontent_soal = ArrayList()
        for (a in 1..10) {
            listcontent_soal.add("file:///android_asset/content_soal_fungsi_$a.html")
        }
    }

    private fun addAnswerA() {
        answerA.add("(i) dan (ii)")
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_a.png?alt=media&token=cee53ef3-a956-4df5-b550-fa06cb8c0c25")
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_a.png?alt=media&token=8c7d7e5e-dd87-4330-b030-654e238bc927")
        answerA.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_a.png?alt=media&token=42be81ec-81b5-4b3f-a775-0ae1ed7bc110")
        answerA.add("{a,b}")
        answerA.add("{x│0≤x≤7, x∈ℤ}")
        answerA.add("{−2, −1, 0}")
        answerA.add("PPKn, Bahasa Indonesia, Bahasa Inggris")
        answerA.add("(3,2)")
        answerA.add("Rp. 150.000,-")
    }

    private fun addAnswerB() {
        answerB.add("(i) dan (iii)")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_b.png?alt=media&token=88b1e698-4306-442a-aaea-a5e5deef3c54")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_b.png?alt=media&token=cde8a6d5-b67d-4190-a7d3-aab36f9760ad")
        answerB.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_b.png?alt=media&token=366879b8-265a-4341-92cc-46ed20df8f72")
        answerB.add("{1,2,3,4}")
        answerB.add("{x│0≤x<7, x∈ℤ}")
        answerB.add("{−2, −1, 1}")
        answerB.add("Matematika, IPS, Seni Budaya")
        answerB.add("(4,2)")
        answerB.add("Rp. 200.000,-")
    }

    private fun addAnswerC() {
        answerC.add("(ii) dan (iii)")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_c.png?alt=media&token=9bd7100c-637d-40a4-a9c3-506c43372212")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_c.png?alt=media&token=57db4556-9260-4e1b-8af5-983fecf7f564")
        answerC.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_c.png?alt=media&token=199ebdf6-cde4-45d5-b08d-15edefae12ae")
        answerC.add("{a,b,c,d}")
        answerC.add("{y│−5≤y≤10, y∈ℤ}")
        answerC.add("{−6, −1, 0}")
        answerC.add("Matematika, IPS, Olah Raga")
        answerC.add("(3,1)")
        answerC.add("Rp. 300.000,-")
    }

    private fun addAnswerD() {
        answerD.add("(iii) dan (iv)")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_1_d.png?alt=media&token=41d52d3a-8700-4ca8-922e-c0aa0a698fc8")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_3_d.png?alt=media&token=f5aa5d9f-df41-41c8-adba-7d13a4a61af8")
        answerD.add("https://firebasestorage.googleapis.com/v0/b/mastering-maths.appspot.com/o/gambar_jawaban%2Fjawaban_fungsi_4_d.png?alt=media&token=67cf17b5-7440-42e5-92ce-786d912b34e3")
        answerD.add("{(1, a,2, b,3, c,4, d}")
        answerD.add("{y│−5<y≤10, y∈ℤ}")
        answerD.add("{−6, 0, 1}")
        answerD.add("Matematika, Bahasa Indonesia, PPKn")
        answerD.add("(7,1)")
        answerD.add("Rp. 400.000,-")
    }

    private fun addKunciJawaban() {
        kunciJawaban.add("C")
        kunciJawaban.add("B")
        kunciJawaban.add("D")
        kunciJawaban.add("A")
        kunciJawaban.add("B")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
        kunciJawaban.add("C")
    }

    companion object {
        var MY_POINT = "my_point"
        var TRUE_ANSWER = "true_answer"
        var FALSE_ANSWER = "false_answer"
    }
}
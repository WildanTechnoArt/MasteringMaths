package com.unpas.masteringmaths.student.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import kotlinx.android.synthetic.main.activity_student_result_exam.*

class StudentResultExamActivity : AppCompatActivity() {

    private var myPoint = 0
    private var myTrueAnswer = 0
    private var myFalseAnswer = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_result_exam)
        init()

        if (myPoint >= 70) {
            GlideApp.with(this)
                .load(R.drawable.trophy_icon)
                .into(img_result)

            tv_congratulation.text = getString(R.string.tv_congratulation)
            tv_congratulation.setTextColor(Color.parseColor("#2ECC71"))
        } else {
            GlideApp.with(this)
                .load(R.drawable.remedial)
                .into(img_result)

            tv_congratulation.text = getString(R.string.tv_exam_failed)
            tv_congratulation.setTextColor(Color.parseColor("#F44336"))
        }

        tv_true.text = "Correct Answe : $myTrueAnswer"
        tv_false.text = "Wrong Answer : $myFalseAnswer"
        tv_myscore.text = myPoint.toString()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        myPoint = intent.getIntExtra(StudentExamActivity.MY_POINT, 0)
        myTrueAnswer = intent.getIntExtra(StudentExamActivity.TRUE_ANSWER, 0)
        myFalseAnswer = intent.getIntExtra(StudentExamActivity.FALSE_ANSWER, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.result_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.finish) {
            finish()
        }
        return true
    }
}
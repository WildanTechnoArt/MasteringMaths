package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.model.DataClass
import kotlinx.android.synthetic.main.activity_inside_class.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class InsideClassActivity : AppCompatActivity() {

    private var getIdTeacher: String? = null
    private var getCodeClass: String? = null
    private var getClassTitle: String? = null
    private var getClassGrade: String? = null
    private var getStudentCount: Long? = null
    private var getTeacherName: String? = null
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_class)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Masuk kelas"
        }

        userId = SharedPrefManager.getInstance(this).getUserId.toString()

        // Menampilkan Daftar Mata Pelajaran
        val lessonAdapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.lesson_list)
        )
        (add_lesson as? AutoCompleteTextView)?.setAdapter(lessonAdapter)

        swipe_refresh.isEnabled = false

        btn_submit.setOnClickListener {
            getIdTeacher = input_teacher_id.text.toString()
            getCodeClass = input_code_class.text.toString()
            if ((getIdTeacher?.isNotEmpty() == true) && (getCodeClass?.isNotEmpty() == true)) {
                checkClass()
            } else {
                Toast.makeText(this, "Masukan Id Guru dan Kode Kelas", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkClass() {
        val classCode = input_code_class.text.toString()

        val db = FirebaseFirestore.getInstance()
        db.collection("students")
            .document(userId)
            .collection("classList")
            .document(classCode)
            .get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    getDataClass()
                } else {
                    Toast.makeText(this, "Kelas sudah pernah ditambahkan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                swipe_refresh.isRefreshing = false
                Toast.makeText(this, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveMyClass() {
        swipe_refresh.isRefreshing = true

        val data = DataClass()
        data.classTitle = getClassTitle
        data.className = add_lesson.text.toString()
        data.studentCount = getStudentCount
        data.classGrade = getClassGrade
        data.teacherName = getTeacherName
        data.teacherId = getIdTeacher
        data.classCode = getCodeClass

        val db = FirebaseFirestore.getInstance()
        db.collection("students")
            .document(userId)
            .collection("classList")
            .document(getCodeClass.toString())
            .set(data)
            .addOnSuccessListener {
                swipe_refresh.isRefreshing = false
                Toast.makeText(this, "Kelas berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                swipe_refresh.isRefreshing = false
                Toast.makeText(this, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
    }

    private fun getDataClass() {
        swipe_refresh.isRefreshing = true

        val db = FirebaseFirestore.getInstance()
        db.collection("teacher")
            .document("classList")
            .collection(getIdTeacher.toString())
            .document(getCodeClass.toString())
            .get()
            .addOnSuccessListener {
                val mClassName = it.getString("className")
                if (it.exists() && (mClassName == add_lesson.text.toString())) {
                    btn_submit.isEnabled = true
                    swipe_refresh.isRefreshing = false

                    getClassTitle = it.getString("classTitle")
                    getClassGrade = it.getString("classGrade")
                    getStudentCount = it.getLong("studentCount")
                    getTeacherName = it.getString("teacherName")

                    saveMyClass()
                } else {
                    swipe_refresh.isRefreshing = false
                    Toast.makeText(this, "Kelas Tidak Ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                swipe_refresh.isRefreshing = false
                Toast.makeText(this, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
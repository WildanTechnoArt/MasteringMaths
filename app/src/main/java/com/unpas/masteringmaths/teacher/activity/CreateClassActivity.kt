package com.unpas.masteringmaths.teacher.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields
import kotlinx.android.synthetic.main.activity_create_class.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CreateClassActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var dataClass: DataClass
    private lateinit var classId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)
        prepare()
        btn_create_class.setOnClickListener {
            if (validateFields(input_class_name.text.toString())) {
                Toast.makeText(
                    applicationContext, getString(R.string.empty_class),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                btn_create_class.showProgress { progressColor = Color.WHITE }
                createClass()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Buat Kelas"
        }

        classId = intent?.getStringExtra(CLASS_ID).toString()

        bindProgressButton(btn_create_class)
        btn_create_class.attachTextChangeAnimator()
        userId = SharedPrefManager.getInstance(this).getUserId.toString()

        // Menampilkan Daftar Jenjang Pelajaran
        val gradeAdapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.grade_level_list)
        )
        (add_grade as? AutoCompleteTextView)?.setAdapter(gradeAdapter)

        add_grade.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    val classList = arrayOf("Kelas VIII")
                    val builder = MaterialAlertDialogBuilder(this)
                        .setTitle("Pilih Kelas")
                        .setItems(
                            classList
                        ) { _, id ->
                            add_grade.setText("${gradeAdapter.getItem(id)} ${classList[id]}")
                        }

                    val dialog = builder.create()
                    dialog.setCancelable(false)
                    dialog.show()
                }

                1 -> {
                    val classList = arrayOf("Kelas X")
                    val builder = MaterialAlertDialogBuilder(this)
                        .setTitle("Pilih Kelas")
                        .setItems(
                            classList
                        ) { _, id ->
                            add_grade.setText("${gradeAdapter.getItem(id)} ${classList[id]}")
                        }
                    val dialog = builder.create()
                    dialog.setCancelable(false)
                    dialog.show()
                }
            }
        }

        // Menampilkan Daftar Mata Pelajaran
        val lessonAdapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.lesson_list)
        )
        (add_lesson as? AutoCompleteTextView)?.setAdapter(lessonAdapter)
    }

    private fun createClass() {
        val teacherName = SharedPrefManager.getInstance(this).getUserName
        dataClass = DataClass()
        dataClass.classTitle = input_class_name.text.toString()
        dataClass.className = add_lesson.text.toString()
        dataClass.classGrade = add_grade.text.toString()
        dataClass.teacherName = teacherName
        dataClass.studentCount = 0

        val db = FirebaseFirestore.getInstance()

        db.collection("teacher")
            .document("classList")
            .collection(userId)
            .document()
            .set(dataClass)
            .addOnSuccessListener {
                createClassList()
            }.addOnFailureListener {
                btn_create_class.hideProgress(getString(R.string.buat_kelas))
                Toast.makeText(
                    this@CreateClassActivity, getString(R.string.error_request),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun createClassList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("classList")
            .document(add_lesson.text.toString())
            .collection(userId)
            .document()
            .set(dataClass)
            .addOnSuccessListener {
                Toast.makeText(
                    this@CreateClassActivity, getString(R.string.success_create_class),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }.addOnFailureListener {
                btn_create_class.hideProgress(getString(R.string.buat_kelas))
                Toast.makeText(
                    this@CreateClassActivity, getString(R.string.error_request),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
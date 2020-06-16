package com.unpas.masteringmaths.teacher.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields
import kotlinx.android.synthetic.main.activity_create_class.*

class CreateClassActivity : AppCompatActivity() {

    private lateinit var classGrade: String
    private lateinit var userId: String
    private lateinit var dataClass: DataClass
    private lateinit var classId: String
    private var getClassName: String? = null

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

        sp_lesson.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                getClassName = resources.getStringArray(R.array.lesson_list)[position].toString()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        classId = intent?.getStringExtra(CLASS_ID).toString()

        bindProgressButton(btn_create_class)
        btn_create_class.attachTextChangeAnimator()
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        sp_level_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (resources.getStringArray(R.array.grade_level_list)[position].toString()) {
                    "SMP" -> {
                        setClassLevel(
                            applicationContext,
                            resources.getStringArray(R.array.junior_high_school)
                        )
                    }
                }
            }
        }
    }

    private fun createClass() {
        val teacherName = SharedPrefManager.getInstance(this).getUserName
        dataClass = DataClass()
        dataClass.classTitle = input_class_name.text.toString()
        dataClass.className = getClassName.toString()
        dataClass.classGrade = classGrade
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
            .document(getClassName.toString())
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

    private fun setClassLevel(context: Context, item: Array<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, item)
        sp_class_list.adapter = adapter
        sp_class_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                classGrade = sp_class_list.selectedItem.toString()
            }

        }
    }
}
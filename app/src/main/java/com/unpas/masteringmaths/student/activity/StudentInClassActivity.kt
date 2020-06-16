package com.unpas.masteringmaths.student.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.StudentClassListener
import com.unpas.masteringmaths.student.adapter.FirestoreStudentClassAdapter
import com.unpas.masteringmaths.student.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import kotlinx.android.synthetic.main.activity_student_in_class.*

class StudentInClassActivity : AppCompatActivity(), StudentClassListener {

    private lateinit var mUserId: String
    private lateinit var className: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_in_class)

        prepare()
        setupDatabse()
        getDataCount()

        val fabInside = findViewById<FloatingActionButton>(R.id.fab_inside_kelas)
        fabInside.setOnClickListener {
            val intent = Intent(this, InsideClassActivity::class.java)
            intent.putExtra(CLASS_NAME, className)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClickListener(classTitle: String, className: String, teacherId: String, key: String) {
        val intent = Intent(this, StudentClassRoomActivity::class.java)
        intent.putExtra(CLASS_TITLE, classTitle)
        intent.putExtra(TEACHER_ID, teacherId)
        intent.putExtra(CLASS_ID, key)
        intent.putExtra(CLASS_NAME, className)
        startActivity(intent)
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("classList")
            .document(className)
            .collection(mUserId)

        val options = FirestoreRecyclerOptions.Builder<DataClass>()
            .setQuery(query, DataClass::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = FirestoreStudentClassAdapter(options, this)
        rv_list_class.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("classList")
            .document(className)
            .collection(mUserId)

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rv_list_class.visibility = View.VISIBLE
                tv_nothing_class.visibility = View.GONE
            } else {
                rv_list_class.visibility = View.GONE
                tv_nothing_class.visibility = View.VISIBLE
            }
        }
    }

    private fun prepare() {
        className = intent?.getStringExtra(CLASS_NAME).toString()

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            //title = className
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mUserId = SharedPrefManager.getInstance(this).getUserId.toString()
        rv_list_class.layoutManager = LinearLayoutManager(this)
        rv_list_class.setHasFixedSize(true)
    }
}

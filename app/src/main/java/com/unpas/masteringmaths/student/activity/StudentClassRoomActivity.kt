package com.unpas.masteringmaths.student.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.adapter.StudentClassRoomAdapter
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_student_class_room.*

class StudentClassRoomActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var classTitle: String
    private lateinit var classGrade: String
    private lateinit var className: String
    private lateinit var teacherId: String
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_class_room)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        classId = intent?.getStringExtra(UtilsConstant.CLASS_ID).toString()
        classTitle = intent?.getStringExtra(UtilsConstant.CLASS_TITLE).toString()
        classGrade = intent?.getStringExtra(UtilsConstant.CLASS_GRADE).toString()
        className = intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()
        teacherId = intent?.getStringExtra(UtilsConstant.TEACHER_ID).toString()

        setSupportActionBar(toolbar)
        supportActionBar?.title = classTitle

        tabs_class.addTab(tabs_class.newTab().setText(resources.getString(R.string.posts)))
        tabs_class.addTab(tabs_class.newTab().setText(resources.getString(R.string.members)))
        tabs_class.addTab(tabs_class.newTab().setText(getString(R.string.discussion)))

        val pageAdapter = StudentClassRoomAdapter(
            supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        container.adapter = pageAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_class))

        tabs_class.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                container.currentItem = tab.position

                when (tab.position) {
                    2 -> fab_add_post.visibility = View.GONE
                    else -> {
                        fab_add_post.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        fab_add_post.setOnClickListener {
            val intent = Intent(this, StudentPostActivity::class.java)
            intent.putExtra(UtilsConstant.CLASS_ID, classId)
            intent.putExtra(UtilsConstant.CLASS_NAME, className)
            intent.putExtra(UtilsConstant.TEACHER_ID, teacherId)
            intent.putExtra(UtilsConstant.TOOLBAR_TITLE, "Buat Postingan")
            startActivity(intent)
        }

        checkMemberExists()
    }

    private fun createMember() {
        val userName = SharedPrefManager.getInstance(this).getUserName.toString()
        val dataMember = MemberData()

        dataMember.username = userName
        dataMember.userId = userId
        dataMember.status = SharedPrefManager.getInstance(this).getUserStatus.toString()

        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(classId)
            .collection("members")
            .document(userId)
            .set(dataMember)
    }

    private fun checkMemberExists() {
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(classId)
            .collection("members")
            .document(userId)
            .get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    createMember()
                }
            }
    }

    companion object {
        const val BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1
    }
}
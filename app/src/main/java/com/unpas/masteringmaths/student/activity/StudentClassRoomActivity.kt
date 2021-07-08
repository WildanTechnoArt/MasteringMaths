package com.unpas.masteringmaths.student.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.adapter.StudentClassRoomAdapter
import com.unpas.masteringmaths.teacher.activity.CreatePostActivity
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_class_room.*
import kotlinx.android.synthetic.main.activity_student_class_room.*
import kotlinx.android.synthetic.main.activity_student_class_room.fab_create_post
import kotlinx.android.synthetic.main.activity_student_class_room.tab_layout
import kotlinx.android.synthetic.main.activity_student_class_room.toolbar
import kotlinx.android.synthetic.main.activity_student_class_room.view_pager

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
        prepare()
        fab_create_post.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra(UtilsConstant.CLASS_ID, classId)
            intent.putExtra(UtilsConstant.CLASS_NAME, className)
            intent.putExtra(UtilsConstant.TEACHER_ID, teacherId)
            intent.putExtra(UtilsConstant.IS_TEACHER, false)
            intent.putExtra(UtilsConstant.TOOLBAR_TITLE, "Create Post")
            startActivity(intent)
        }
        checkMemberExists()
    }

    private fun prepare() {
        val tabMenus = arrayListOf(
            getString(R.string.posts),
            getString(R.string.members),
            getString(R.string.discussion)
        )

        classId = intent?.getStringExtra(UtilsConstant.CLASS_ID).toString()
        classTitle = intent?.getStringExtra(UtilsConstant.CLASS_TITLE).toString()
        classGrade = intent?.getStringExtra(UtilsConstant.CLASS_GRADE).toString()
        className = intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()
        teacherId = intent?.getStringExtra(UtilsConstant.TEACHER_ID).toString()

        setSupportActionBar(toolbar)
        supportActionBar?.title = classTitle

        val pageAdapter = StudentClassRoomAdapter(this)

        view_pager.adapter = pageAdapter

        TabLayoutMediator(
            tab_layout,
            view_pager
        ) { tab, position ->
            tab.text = tabMenus[position]
        }.attach()

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> fab_create_post.show()
                    else -> fab_create_post.hide()
                }
            }

        })
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
}
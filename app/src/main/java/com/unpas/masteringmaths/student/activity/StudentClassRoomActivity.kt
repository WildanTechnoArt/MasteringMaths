package com.unpas.masteringmaths.student.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.adapter.StudentClassRoomAdapter
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_student_class_room.*

class StudentClassRoomActivity : AppCompatActivity() {

    private val tabMenus = arrayListOf(
        getString(R.string.posts),
        getString(R.string.members),
        getString(R.string.discussion)
    )

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

        val pageAdapter = StudentClassRoomAdapter(this)

        container.adapter = pageAdapter

        TabLayoutMediator(
            tabs_class,
            container,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabMenus[position]
            }
        )

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
}
package com.unpas.masteringmaths.teacher.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.adapter.ClassRoomAdapter
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_GRADE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLOSE_ACTIVITY
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.activity_class_room.*

class ClassRoomActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var classTitle: String
    private lateinit var classGrade: String
    private lateinit var className: String
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_room)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        classId = intent?.getStringExtra(CLASS_ID).toString()
        classTitle = intent?.getStringExtra(CLASS_TITLE).toString()
        className = intent?.getStringExtra(CLASS_NAME).toString()
        classGrade = intent?.getStringExtra(CLASS_GRADE).toString()

        userId = SharedPrefManager.getInstance(this).getUserId.toString()

        setSupportActionBar(toolbar)
        getClassName()

        tabs_class.addTab(tabs_class.newTab().setText(resources.getString(R.string.posts)))
        tabs_class.addTab(tabs_class.newTab().setText(resources.getString(R.string.members)))
        tabs_class.addTab(tabs_class.newTab().setText(getString(R.string.discussion)))

        val pageAdapter = ClassRoomAdapter(
            supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        container.adapter = pageAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_class))

        tabs_class.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                container.currentItem = tab.position

                when (tab.position) {
                    2 -> menuFab.visibility = View.GONE
                    else -> {
                        menuFab.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        fab_posts.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra(CLASS_ID, classId)
            intent.putExtra(CLASS_NAME, className)
            intent.putExtra(TOOLBAR_TITLE, "Buat Postingan")
            startActivity(intent)
        }

        fab_exam.setOnClickListener {
            val intent = Intent(this, CreateAssignmentActivity::class.java)
            intent.putExtra(CLASS_ID, classId)
            intent.putExtra(CLASS_NAME, className)
            intent.putExtra(TOOLBAR_TITLE, "Buat Tugas")
            startActivity(intent)
        }

        checkMemberExists()
    }

    private fun getClassName(){
        val db = FirebaseFirestore.getInstance()
        db.collection("teacher")
            .document("classList")
            .collection(userId)
            .document(classId)
            .addSnapshotListener { it, _ ->
                supportActionBar?.title = it?.getString("classTitle").toString()
            }
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
            .collection(userId)
            .document(classId)
            .collection("members")
            .document(userId)
            .set(dataMember)
    }

    private fun checkMemberExists() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(userId)
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

    override fun onResume() {
        super.onResume()
        menuFab.close(true)
        val close = intent?.getBooleanExtra(CLOSE_ACTIVITY, false)
        if (close == true) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.class_room, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val intent = Intent(this, SettingClassActivity::class.java)
                intent.putExtra(CLASS_ID, classId)
                intent.putExtra(CLASS_TITLE, classTitle)
                intent.putExtra(CLASS_NAME, className)
                intent.putExtra(CLASS_GRADE, classGrade)
                startActivity(intent)
            }
        }
        return true
    }

    companion object {
        const val BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT = 1
    }
}


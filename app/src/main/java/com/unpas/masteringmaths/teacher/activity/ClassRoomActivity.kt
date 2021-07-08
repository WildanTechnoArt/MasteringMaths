package com.unpas.masteringmaths.teacher.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_TEACHER
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
        prepare()
        fab_create_post.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            intent.putExtra(CLASS_ID, classId)
            intent.putExtra(CLASS_NAME, className)
            intent.putExtra(IS_TEACHER, true)
            intent.putExtra(TOOLBAR_TITLE, "Create Post")
            startActivity(intent)
        }
        checkMemberExists()
    }

    override fun onResume() {
        super.onResume()
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

    private fun prepare() {
        val tabMenus = arrayOf(
            getString(R.string.posts),
            getString(R.string.members),
            getString(R.string.discussion)
        )

        classId = intent?.getStringExtra(CLASS_ID).toString()
        classTitle = intent?.getStringExtra(CLASS_TITLE).toString()
        className = intent?.getStringExtra(CLASS_NAME).toString()
        classGrade = intent?.getStringExtra(CLASS_GRADE).toString()

        userId = SharedPrefManager.getInstance(this).getUserId.toString()

        setSupportActionBar(toolbar)
        getClassName()

        val pageAdapter = ClassRoomAdapter(this)

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

    private fun getClassName() {
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
}


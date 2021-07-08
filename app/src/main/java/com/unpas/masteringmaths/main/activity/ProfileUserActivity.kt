package com.unpas.masteringmaths.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.StudentChatActivity
import com.unpas.masteringmaths.teacher.activity.TeacherChatActivity
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.STUDENT_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.USER_ID
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ProfileUserActivity : AppCompatActivity() {

    private lateinit var memberUserId: String
    private lateinit var myUserId: String
    private var isTeacher = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        init()
        getProfileData()
        getPhotoProfile()
        swipe_refresh?.setOnRefreshListener {
            getProfileData()
        }
        btn_chat.setOnClickListener {
            if (!isTeacher) {
                val intent = Intent(this, TeacherChatActivity::class.java)
                intent.putExtra(STUDENT_ID, memberUserId)
                startActivity(intent)
            } else {
                val intent = Intent(this, StudentChatActivity::class.java)
                intent.putExtra(TEACHER_ID, memberUserId)
                startActivity(intent)
            }
        }
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        memberUserId = intent?.getStringExtra(USER_ID).toString()
        myUserId = SharedPrefManager.getInstance(this).getUserId.toString()

        if (myUserId == memberUserId) {
            btn_chat.visibility = View.GONE
        }
    }

    private fun getProfileData() {
        swipe_refresh?.isRefreshing = true

        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(memberUserId)
            .get()
            .addOnSuccessListener {
                btn_chat.isEnabled = true
                swipe_refresh?.isRefreshing = false

                isTeacher = it.getBoolean("teacher") ?: false
                val getUsername = it.getString("username").toString()
                val getEmail = it.getString("email").toString()
                val getPhoneNumber = it.getString("phone").toString()

                tv_email.text = getEmail
                tv_user_name.text = getUsername
                tv_phone_number.text = getPhoneNumber

                if (isTeacher) {
                    if (myUserId == memberUserId) {
                        supportActionBar?.title = "My Profile"
                    } else {
                        supportActionBar?.title = "Teacher Profile"
                    }

                    tv_school.visibility = View.VISIBLE
                    line_three.visibility = View.VISIBLE
                    img_school.visibility = View.VISIBLE

                    val getSchoolName = it.getString("sekolah").toString()
                    val getNip = it.getString("nip").toString()
                    val getCity = it.getString("kota").toString()

                    tv_school.text = getSchoolName
                    tv_city.text = getCity
                    tv_id.text = getNip
                } else {
                    if (myUserId == memberUserId) {
                        supportActionBar?.title = "My Profile"
                    } else {
                        supportActionBar?.title = "Teacher Profile"
                    }

                    val getNisn = it.getString("nisn").toString()
                    val getAddress = it.getString("address").toString()

                    tv_id.text = getNisn
                    tv_city.text = getAddress
                }
            }
            .addOnFailureListener {
                swipe_refresh?.isRefreshing = false
                val message = resources.getString(R.string.request_error)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getPhotoProfile() {
        val db = FirebaseFirestore.getInstance()
        db.collection("photos")
            .document(memberUserId)
            .get()
            .addOnSuccessListener {
                swipe_refresh?.isRefreshing = false

                val photoUrl = it.getString("photoUrl").toString()
                GlideApp.with(this)
                    .load(photoUrl)
                    .placeholder(R.drawable.profile_placeholder)
                    .into(img_profile)
            }
            .addOnFailureListener {
                swipe_refresh?.isRefreshing = false
                val message = resources.getString(R.string.request_error)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
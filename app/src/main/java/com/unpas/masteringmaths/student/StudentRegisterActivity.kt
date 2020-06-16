package com.unpas.masteringmaths.student

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.activity.StudentDashboardActivity
import com.unpas.masteringmaths.student.model.Student
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields
import kotlinx.android.synthetic.main.activity_student_register.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class StudentRegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mNisn: String
    private lateinit var mUsername: String
    private lateinit var mEmail: String
    private lateinit var mAddress: String
    private lateinit var mPhoneNumber: String
    private lateinit var mPassword: String
    private lateinit var mReTypePassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)
        prepare()
        btn_register.setOnClickListener {
            createAccount()
        }
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Daftar Sebagai Siswa"
        }

        bindProgressButton(btn_register)
        btn_register.attachTextChangeAnimator()

        mAuth = FirebaseAuth.getInstance()
    }

    private fun createAccount() {
        mNisn = input_nisn.text.toString()
        mUsername = input_name.text.toString()
        mEmail = input_email.text.toString()
        mAddress = input_address.text.toString()
        mPhoneNumber = input_phone.text.toString()
        mPassword = input_password.text.toString().trim()
        mReTypePassword = input_retype_password.text.toString().trim()

        if (validateFields(mNisn) || validateFields(mUsername) || validateFields(mPassword)) {

            Toast.makeText(
                this, getString(R.string.warning_input_data),
                Toast.LENGTH_SHORT
            ).show()

        } else if (validateEmail(mEmail)) {

            Toast.makeText(
                this, getString(R.string.email_not_valid),
                Toast.LENGTH_SHORT
            ).show()

        } else {

            if (mPassword == mReTypePassword) {
                btn_register.showProgress { progressColor = Color.WHITE }

                mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                    .addOnSuccessListener {
                        it.user?.uid?.let { it1 -> addDataUser(it1) }
                    }.addOnFailureListener {
                        btn_register.hideProgress(R.string.daftar)
                        Toast.makeText(
                            this, getString(R.string.register_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {

                Toast.makeText(
                    this, getString(R.string.password_not_valid),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun addDataUser(userId: String) {
        val student = Student()
        student.nisn = mNisn
        student.username = mUsername
        student.email = mEmail
        student.address = mAddress
        student.phone = mPhoneNumber
        student.isTeacher = false

        val db = FirebaseFirestore.getInstance()
            .collection("users").document(userId)

        db.set(student)
            .addOnSuccessListener {
                SharedPrefManager.getInstance(this).saveUserId(userId)
                SharedPrefManager.getInstance(this).saveUserName(mUsername)
                SharedPrefManager.getInstance(this).saveUserStatus("Pelajar")

                startActivity(Intent(this, StudentDashboardActivity::class.java))
                finishAffinity()
            }.addOnFailureListener {
                btn_register.hideProgress(R.string.daftar)

                Toast.makeText(
                    this, getString(R.string.register_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
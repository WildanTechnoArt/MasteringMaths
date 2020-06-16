package com.unpas.masteringmaths.teacher.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.presenter.RegisterPresenter
import com.unpas.masteringmaths.teacher.view.RegisterView
import kotlinx.android.synthetic.main.activity_teacher_register.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class TeacherRegisterActivity : AppCompatActivity(), RegisterView.View {

    private lateinit var mNip: String
    private lateinit var mUsername: String
    private lateinit var mEmail: String
    private lateinit var mSchool: String
    private lateinit var mCity: String
    private lateinit var mPhoneNumber: String
    private lateinit var mPassword: String
    private lateinit var mReTypePassword: String

    private lateinit var presenter: RegisterView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)
        prepare()
        btn_register.setOnClickListener {
            mNip = input_nip.text.toString()
            mUsername = input_name.text.toString()
            mEmail = input_email.text.toString()
            mSchool = input_address.text.toString()
            mCity = input_city.text.toString()
            mPhoneNumber = input_phone.text.toString()
            mPassword = input_password.text.toString().trim()
            mReTypePassword = input_retype_password.text.toString().trim()
            presenter.requestRegister(mNip, mUsername, mEmail, mSchool, mCity, mPhoneNumber, mPassword, mReTypePassword)
        }
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Daftar Sebagai Guru"
        }

        presenter = RegisterPresenter(this, this)

        bindProgressButton(btn_register)
        btn_register.attachTextChangeAnimator()
    }

    override fun onSuccess(userId: String, message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()

        SharedPrefManager.getInstance(this).saveUserId(userId)
        startActivity(Intent(this, TeacherDashboardActivity::class.java))
        finishAffinity()
    }

    override fun handleResponse(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        btn_register.hideProgress(R.string.daftar)
    }

    override fun showProgressBar() {
        btn_register.showProgress { progressColor = Color.WHITE }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

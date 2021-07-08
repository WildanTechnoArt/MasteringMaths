package com.unpas.masteringmaths.main.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.DocumentSnapshot
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.main.presenter.LoginPresenter
import com.unpas.masteringmaths.main.view.LoginView
import com.unpas.masteringmaths.student.StudentRegisterActivity
import com.unpas.masteringmaths.student.activity.StudentDashboardActivity
import com.unpas.masteringmaths.teacher.activity.TeacherDashboardActivity
import com.unpas.masteringmaths.teacher.activity.TeacherRegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView.View {

    private lateinit var presenter: LoginView.Presenter

    private lateinit var mEmail: String
    private lateinit var mPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prepare()

        btn_login.setOnClickListener {
            mEmail = input_email.text.toString().trim()
            mPassword = input_password.text.toString().trim()
            presenter.requestLogin(mEmail, mPassword)
        }

        tv_forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPassActivity::class.java))
        }

        tv_register.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
                .setTitle("Select User Type")
                .setMessage("Want to Register As?")
                .setPositiveButton("Teacher") { _, _ ->
                    startActivity(Intent(this, TeacherRegisterActivity::class.java))
                }
                .setNegativeButton("Student"){ _, _ ->
                    startActivity(Intent(this, StudentRegisterActivity::class.java))
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onSuccess(result: DocumentSnapshot) {
        val myUsername = result.getString("username").toString()
        if (result.getBoolean("teacher") == true) {
            SharedPrefManager.getInstance(this).saveUserStatus("Guru")
            SharedPrefManager.getInstance(this).saveUserName(myUsername)
            startActivity(Intent(this, TeacherDashboardActivity::class.java))
            finishAffinity()
        } else {
            SharedPrefManager.getInstance(this).saveUserStatus("Pelajar")
            SharedPrefManager.getInstance(this).saveUserName(myUsername)
            startActivity(Intent(this, StudentDashboardActivity::class.java))
            finishAffinity()
        }
    }

    override fun handleResponse(message: String) {
        when (message) {
            "ERROR_USER_NOT_FOUND" -> Toast.makeText(
                this, getString(R.string.error_user_not_found),
                Toast.LENGTH_SHORT
            ).show()

            "ERROR_WRONG_PASSWORD" -> Toast.makeText(
                this, getString(R.string.error_wrong_password),
                Toast.LENGTH_SHORT
            ).show()

            else -> Toast.makeText(
                this, message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun showProgressBar() {
        btn_login.showProgress { progressColor = Color.WHITE }
    }

    override fun hideProgressBar() {
        btn_login.hideProgress(R.string.login)
    }

    private fun prepare() {
        presenter = LoginPresenter(this, this)

        GlideApp.with(this)
            .load(R.drawable.logo_not_background)
            .into(img_app)

        bindProgressButton(btn_login)
        btn_login.attachTextChangeAnimator()
    }
}
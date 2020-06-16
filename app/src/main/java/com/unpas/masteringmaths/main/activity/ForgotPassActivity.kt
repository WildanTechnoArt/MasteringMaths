package com.unpas.masteringmaths.main.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.presenter.ForgotPasswordPresenter
import com.unpas.masteringmaths.main.view.ForgotPasswordView
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPassActivity : AppCompatActivity(), ForgotPasswordView.View {

    private lateinit var mEmail: String
    private lateinit var presenter: ForgotPasswordView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        prepare()
        btn_forgot_password.setOnClickListener {
            mEmail = input_email.text.toString()
            presenter.requestForgotPassword(mEmail)
        }
    }

    override fun onSuccess(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun handleResponse(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showProgressBar() {
        btn_forgot_password.showProgress { progressColor = Color.WHITE }
    }

    override fun hideProgressBar() {
        btn_forgot_password.hideProgress(R.string.lupa_password)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        presenter = ForgotPasswordPresenter(this, this)

        bindProgressButton(btn_forgot_password)
        btn_forgot_password.attachTextChangeAnimator()
    }
}
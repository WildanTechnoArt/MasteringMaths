package com.unpas.masteringmaths.main.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.view.ForgotPasswordView
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail

class ForgotPasswordPresenter(private val context: Context,
                              private val view: ForgotPasswordView.View) : ForgotPasswordView.Presenter {

    override fun requestForgotPassword(email: String) {
        if (validateEmail(email)) {
            view.handleResponse(context.getString(R.string.email_not_valid))
        } else {
            view.showProgressBar()

            val mAuth = FirebaseAuth.getInstance()

            mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        view.onSuccess(context.getString(R.string.send_reset_password))
                        view.hideProgressBar()
                    } else {
                        view.hideProgressBar()
                        view.handleResponse(context.getString(R.string.email_not_valid))
                    }
                }
        }
    }
}
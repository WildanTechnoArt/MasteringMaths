package com.unpas.masteringmaths.main.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.view.LoginView
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields

class LoginPresenter(private val context: Context,
                     private val view: LoginView.View
) : LoginView.Presenter {

    override fun requestLogin(email: String, password: String) {
        if (validateFields(email) || validateFields(password)) {
            view.handleResponse(context.getString(R.string.email_password_null))
        } else if (validateEmail(email)) {
            view.handleResponse(context.getString(R.string.email_not_valid))
        } else {
            view.showProgressBar()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val userId = task.result?.user?.uid.toString()

                    val db = FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(userId)
                        .get()

                    SharedPrefManager.getInstance(context).saveUserId(userId)

                    db.addOnSuccessListener { result ->
                        view.onSuccess(result)
                    }.addOnFailureListener {
                        view.hideProgressBar()
                        view.handleResponse(context.getString(R.string.request_error))
                    }

                } else {
                    view.hideProgressBar()
                    view.handleResponse((task.exception as FirebaseAuthException).errorCode)
                }
            }
    }
}
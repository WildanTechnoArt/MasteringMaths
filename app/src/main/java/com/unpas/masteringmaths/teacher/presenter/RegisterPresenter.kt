package com.unpas.masteringmaths.teacher.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.model.Teacher
import com.unpas.masteringmaths.teacher.view.RegisterView
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields

class RegisterPresenter(
    private val context: Context,
    private val view: RegisterView.View
) : RegisterView.Presenter {

    override fun requestRegister(
        nip: String, name: String, email: String,
        school: String, city: String, phone: String, password: String,
        retype: String
    ) {
        if (validateFields(nip) || validateFields(name) || validateFields(password)) {
            view.handleResponse(context.getString(R.string.warning_input_data))
        } else if (validateEmail(email)) {
            view.handleResponse(context.getString(R.string.email_not_valid))
        } else {
            val mAuth = FirebaseAuth.getInstance()
            if (password == retype) {

                view.showProgressBar()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        addDataUser(it.user?.uid.toString(), nip, name, email, school, city, phone)
                    }.addOnFailureListener {
                        view.hideProgressBar()
                        view.handleResponse(context.getString(R.string.register_failed))
                    }
            } else {
                view.handleResponse(context.getString(R.string.password_not_valid))
            }
        }
    }

    private fun addDataUser(
        userId: String, nip: String, name: String,
        email: String, school: String, city: String, phone: String
    ) {
        val teacher = Teacher()
        teacher.nip = nip
        teacher.username = name
        teacher.email = email
        teacher.sekolah = school
        teacher.kota = city
        teacher.phone = phone
        teacher.isTeacher = true

        val db = FirebaseFirestore.getInstance()
            .collection("users").document(userId)

        db.set(teacher)
            .addOnSuccessListener {
                SharedPrefManager.getInstance(context).saveUserName(name)
                SharedPrefManager.getInstance(context).saveUserStatus("Guru")
                view.onSuccess(userId, context.getString(R.string.register_success))
            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(context.getString(R.string.register_failed))
            }
    }
}
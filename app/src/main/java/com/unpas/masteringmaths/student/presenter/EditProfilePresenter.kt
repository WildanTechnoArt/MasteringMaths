package com.unpas.masteringmaths.student.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.model.Student
import com.unpas.masteringmaths.student.view.EditProfileView
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields


class EditProfilePresenter(
    private val context: Context,
    private val view: EditProfileView.View
) : EditProfileView.Presenter {

    private val mUserId = SharedPrefManager.getInstance(context).getUserId.toString()

    private lateinit var mDefaultEmail: String

    override fun requestDataUser() {
        val db = FirebaseFirestore.getInstance()
            .collection("users")
            .document(mUserId)

        db.get()
            .addOnSuccessListener {
                val nisn = it.getString("nisn").toString()
                val name = it.getString("username").toString()
                mDefaultEmail = it.getString("email").toString()
                val city = it.getString("address").toString()
                val phone = it.getString("phone").toString()

                view.hideProgressBar()
                view.showProfileUser(nisn, name, mDefaultEmail, city, phone)

            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(it.localizedMessage?.toString().toString())
            }
    }

    override fun requestEditProfile(nisn: String, name: String, email: String, city: String, phone: String) {
        val student = Student()
        student.nisn = nisn
        student.username = name
        student.email = email
        student.address = city
        student.phone = phone
        student.isTeacher = false

        if (validateFields(nisn) || validateFields(name)
             || validateFields(city)  || validateFields(phone)
        ) {
            view.handleResponse(context.getString(R.string.warning_input_data))
        } else if (validateEmail(email)) {
            view.handleResponse(context.getString(R.string.email_not_valid))
        } else {
            if (mDefaultEmail == email) {
                editDataUser(student)
            } else {
                val mAuth = FirebaseAuth.getInstance().currentUser
                mAuth?.updateEmail(email)
                    ?.addOnCompleteListener {
                        editDataUser(student)
                    }
                    ?.addOnFailureListener {
                        view.handleResponse(it.localizedMessage?.toString().toString())
                    }
            }
        }
    }

    private fun editDataUser(student: Student) {
        view.showProgressBar()

        val db = FirebaseFirestore.getInstance()
            .collection("users").document(mUserId)

        db.set(student)
            .addOnSuccessListener {
                view.onSuccessSaveData(context.getString(R.string.success_edit_profile))
            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(it.localizedMessage?.toString().toString())
            }
    }
}
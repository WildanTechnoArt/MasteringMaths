package com.unpas.masteringmaths.teacher.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.model.Teacher
import com.unpas.masteringmaths.utils.Validation.Companion.validateEmail
import com.unpas.masteringmaths.utils.Validation.Companion.validateFields
import com.unpas.masteringmaths.teacher.view.EditProfileView

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
                val nip = it.getString("nip").toString()
                val name = it.getString("username").toString()
                mDefaultEmail = it.getString("email").toString()
                val school = it.getString("sekolah").toString()
                val city = it.getString("kota").toString()
                val phone = it.getString("phone").toString()

                view.hideProgressBar()
                view.showProfileUser(nip, name, mDefaultEmail, school, city, phone)

            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(it.localizedMessage?.toString().toString())
            }
    }

    override fun requestEditProfile(nip: String, name: String, email: String, school: String, city: String, phone: String) {
        val teacher = Teacher()
        teacher.nip = nip
        teacher.username = name
        teacher.email = email
        teacher.sekolah = school
        teacher.kota = city
        teacher.phone = phone
        teacher.isTeacher = true

        if (validateFields(nip) || validateFields(name)
            || validateFields(school) || validateFields(city)  || validateFields(phone)
        ) {
            view.handleResponse(context.getString(R.string.warning_input_data))
        } else if (validateEmail(email)) {
            view.handleResponse(context.getString(R.string.email_not_valid))
        } else {
            if (mDefaultEmail == email) {
                editDataUser(teacher)
            } else {
                val mAuth = FirebaseAuth.getInstance().currentUser
                mAuth?.updateEmail(email)
                    ?.addOnCompleteListener {
                        editDataUser(teacher)
                    }
                    ?.addOnFailureListener {
                        view.handleResponse(it.localizedMessage?.toString().toString())
                    }
            }
        }
    }

    private fun editDataUser(teacher: Teacher) {
        view.showProgressBar()

        val db = FirebaseFirestore.getInstance()
            .collection("users").document(mUserId)

        db.set(teacher)
            .addOnSuccessListener {
                view.onSuccessSaveData(context.getString(R.string.success_edit_profile))
            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(it.localizedMessage?.toString().toString())
            }
    }
}
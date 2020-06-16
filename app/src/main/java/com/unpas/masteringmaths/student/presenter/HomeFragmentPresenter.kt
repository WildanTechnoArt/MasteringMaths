package com.unpas.masteringmaths.student.presenter

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.view.HomeFragmentView

class HomeFragmentPresenter(private val context: Context,
                            private val view: HomeFragmentView.View) : HomeFragmentView.Presenter {

    private val database = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance().currentUser

    override fun getPhotoFromStorage() {
        view.showProgressBar()

        database.collection("photos").document(mAuth?.uid.toString())
            .get()
            .addOnSuccessListener {
                val photoUrl: String = it.getString("photoUrl").toString()

                view.showPhotoProfile(photoUrl)
                view.hideProgressBar()

            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(context.getString(R.string.failed_show_photo))
            }
    }

    override fun requestDatabase() {
        view.showProgressBar()

        val db = FirebaseFirestore.getInstance().collection("users")
        val mUserId = SharedPrefManager.getInstance(context).getUserId.toString()

        db.document(mUserId)
            .get()
            .addOnSuccessListener {
                view.hideProgressBar()
                view.onSuccess(it.getString("username").toString(), it.getString("nisn").toString())
            }.addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(context.getString(R.string.request_error))
            }
    }
}
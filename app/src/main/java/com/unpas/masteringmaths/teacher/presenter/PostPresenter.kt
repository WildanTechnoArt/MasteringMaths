package com.unpas.masteringmaths.teacher.presenter

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.teacher.view.PostView

class PostPresenter(
    private val context: Context,
    private val view: PostView.View
) : PostView.Presenter {

    private lateinit var db: FirebaseFirestore

    override fun requestProfile(userId: String) {
        view.showProgressBar()
        db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val username = it.getString("username").toString()
                val isTeacher = it.getBoolean("teacher")
                val id: String

                id = if (isTeacher == true) {
                    it.getString("nip").toString()
                } else {
                    it.getString("nisn").toString()
                }

                view.showProfileUser(username, id)
                view.hideProgressBar()
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }

    override fun postData(
        urlPhoto: String, post: String?, nip: String,
        username: String, userId: String, teacherId: String, codeClass: String, lesson: String
    ) {
        view.showProgressBar()

        val data = PostData()
        data.urlPhoto = urlPhoto
        data.nip = nip
        data.userId = userId
        data.username = username
        data.isTeacher = false
        data.postContent = post
        data.postType = 1

        db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(lesson)
            .collection(teacherId)
            .document(codeClass)
            .collection("posts")
            .document()
            .set(data)
            .addOnSuccessListener {
                view.onSuccessPost(context.getString(R.string.success_upload_post))
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }

    override fun updateData(
        postKey: String,
        urlPhoto: String,
        post: String?,
        nip: String,
        username: String,
        userId: String,
        teacherId: String,
        codeClass: String,
        lesson: String
    ) {
        view.showProgressBar()

        val data = PostData()
        data.urlPhoto = urlPhoto
        data.nip = nip
        data.userId = userId
        data.username = username
        data.postContent = post
        data.postType = 1

        db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(lesson)
            .collection(teacherId)
            .document(codeClass)
            .collection("posts")
            .document(postKey)
            .set(data)
            .addOnSuccessListener {
                view.onSuccessUpdate(context.getString(R.string.update_success))
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }

    override fun requestPhoto(userId: String) {
        view.showProgressBar()
        db = FirebaseFirestore.getInstance()
        db.collection("photos")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val url = it.getString("thumbUrl").toString()
                view.showPhotoUser(url)
                view.hideProgressBar()
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }
}
package com.unpas.masteringmaths.teacher.view

import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage

class PostView {

    interface View {
        fun handleResponse(message: String)
        fun showProfileUser(username: String, nip: String)
        fun showPhotoUser(photoUrl: String)
        fun onSuccessPost(message: String)
        fun onSuccessUpdate(message: String)
        fun showFileName(fileName: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestProfile(userId: String)

        fun addPost(
            className: String,
            userId: String,
            urlPhoto: String,
            postContent: String?,
            nomorInduk: String,
            codeClass: String,
            username: String
        )

        fun updatePost(
            className: String,
            userId: String,
            postKey: String,
            urlPhoto: String,
            postContent: String?,
            nomorInduk: String,
            codeClass: String,
            username: String
        )

        fun requestPhoto(userId: String)

        fun uploadFilePhoto(result: CropImage.ActivityResult)
        fun uploadFileDocument(result: Uri)
    }
}
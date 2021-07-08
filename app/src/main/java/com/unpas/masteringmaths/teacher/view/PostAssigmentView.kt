package com.unpas.masteringmaths.teacher.view

import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage

class PostAssigmentView {

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
        fun uploadFilePhoto(result: CropImage.ActivityResult)
        fun uploadFileDocument(result: Uri)
        fun requestProfile(userId: String)
        fun postData(urlPhoto: String, post: String?, postTitle: String?, nip: String, username: String,
                     userId: String, codeClass: String, lesson: String)
        fun updateData(postKey: String, urlPhoto: String, post: String?, postTitle: String?, nip: String, username: String,
                     userId: String, codeClass: String, lesson: String)
        fun requestPhoto(userId: String)
    }
}
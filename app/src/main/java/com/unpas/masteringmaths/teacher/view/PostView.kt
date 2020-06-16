package com.unpas.masteringmaths.teacher.view

class PostView {

    interface View {
        fun handleResponse(message: String)
        fun showProfileUser(username: String, nip: String)
        fun showPhotoUser(photoUrl: String)
        fun onSuccessPost(message: String)
        fun onSuccessUpdate(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestProfile(userId: String)
        fun postData(urlPhoto: String, post: String?, nip: String, username: String,
                     userId: String, teacherId: String, codeClass: String, lesson: String)
        fun updateData(postKey: String, urlPhoto: String, post: String?, nip: String, username: String,
                     userId: String, teacherId: String, codeClass: String, lesson: String)
        fun requestPhoto(userId: String)
    }
}
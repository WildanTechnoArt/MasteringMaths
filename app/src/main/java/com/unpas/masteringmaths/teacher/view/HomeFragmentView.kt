package com.unpas.masteringmaths.teacher.view

class HomeFragmentView {

    interface View {
        fun onSuccess(name: String, teacherId: String)
        fun handleResponse(message: String)
        fun showPhotoProfile(photoUrl: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestDatabase()
        fun getPhotoFromStorage()
    }
}
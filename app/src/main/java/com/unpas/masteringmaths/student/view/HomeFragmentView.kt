package com.unpas.masteringmaths.student.view

class HomeFragmentView {

    interface View {
        fun onSuccess(name: String, nisn: String)
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
package com.unpas.masteringmaths.student.view

class EditProfileView {

    interface View {
        fun onSuccessSaveData(message: String)
        fun showProfileUser(nisn: String, name: String, email: String, city: String, phone: String)
        fun handleResponse(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestDataUser()
        fun requestEditProfile(nisn: String, name: String, email: String, city: String, phone: String)
    }
}
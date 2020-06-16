package com.unpas.masteringmaths.teacher.view

class EditProfileView {

    interface View {
        fun onSuccessSaveData(message: String)
        fun showProfileUser(nip: String, name: String, email: String, school: String, city: String, phone: String)
        fun handleResponse(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestDataUser()
        fun requestEditProfile(nip: String, name: String, email: String, school: String, city: String, phone: String)
    }
}
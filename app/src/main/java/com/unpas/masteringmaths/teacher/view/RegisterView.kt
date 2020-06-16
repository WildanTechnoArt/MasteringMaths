package com.unpas.masteringmaths.teacher.view

class RegisterView {

    interface View {
        fun onSuccess(userId: String, message: String)
        fun handleResponse(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestRegister(
            nip: String,
            name: String,
            email: String,
            school: String,
            city: String,
            phone: String,
            password: String,
            retype: String
        )
    }
}
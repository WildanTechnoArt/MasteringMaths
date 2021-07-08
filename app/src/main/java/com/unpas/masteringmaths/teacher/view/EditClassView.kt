package com.unpas.masteringmaths.teacher.view

class EditClassView {

    interface View {
        fun onSuccessEditData(message: String, className: String, classGrade: String)
        fun onSuccessDeleteData(message: String)
        fun handleResponse(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestEditClass(className: String, classTitle: String, classGrade: String, userId: String, codeClass: String)
        fun requestDeleteClass(className: String, userId: String, codeClass: String)
    }
}
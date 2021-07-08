package com.unpas.masteringmaths.student.view

import com.theartofdev.edmodo.cropper.CropImage

class ProfileFragmentView {

    interface View {
        fun onSuccessLogout()
        fun onSuccessUpload(message: String)
        fun showProfileUser(nisn: String, name: String, email: String, city: String, phone: String)
        fun showPhotoProfile(photoUrl: String)
        fun handleResponse(message: String)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestDataUser()
        fun getPhotoFromStorage()
        fun uploadPhotoProfile(result: CropImage.ActivityResult)
        fun requestLogout()
    }
}
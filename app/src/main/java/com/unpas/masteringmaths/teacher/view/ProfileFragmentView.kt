package com.unpas.masteringmaths.teacher.view

import android.content.Context
import com.theartofdev.edmodo.cropper.CropImage

class ProfileFragmentView {

    interface View {
        fun onSuccessLogout()
        fun onSuccessUpload(context: Context?, message: String?)
        fun showProfileUser(nip: String, name: String, email: String, school: String, city: String, phone: String)
        fun showPhotoProfile(photoUrl: String)
        fun handleResponse(message: String?)
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun requestDataUser()
        fun getPhotoFromStorage()
        fun uploadPhotoProfile(result: CropImage.ActivityResult)
        fun requestLogout()
        fun onDestroy()
    }
}
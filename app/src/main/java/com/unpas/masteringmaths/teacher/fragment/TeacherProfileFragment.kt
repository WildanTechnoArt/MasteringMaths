package com.unpas.masteringmaths.teacher.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.main.activity.LoginActivity
import com.unpas.masteringmaths.teacher.activity.EditProfileActivity
import com.unpas.masteringmaths.teacher.presenter.ProfilePresenter
import com.unpas.masteringmaths.teacher.view.ProfileFragmentView
import kotlinx.android.synthetic.main.fragment_teacher_profile.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class TeacherProfileFragment : Fragment(), ProfileFragmentView.View {

    private lateinit var presenter: ProfileFragmentView.Presenter
    private lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teacher_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        prepare(view)

        presenter.requestDataUser()
        presenter.getPhotoFromStorage()

        swipe_refresh.setOnRefreshListener {
            presenter.requestDataUser()
        }

        fab_change_photo.setOnClickListener {
            getPhotoFromStorage(it.context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                val alert = MaterialAlertDialogBuilder(mContext)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin ingin keluar?")
                    .setPositiveButton("YA") { _, _ ->
                        presenter.requestLogout()
                    }
                    .setNegativeButton("TIDAK") { dialog, _ ->
                        dialog.dismiss()
                    }
                alert.create()
                alert.show()
            }
            R.id.edit_profile -> {
                startActivity(Intent(mContext, EditProfileActivity::class.java))
            }
        }
        return true
    }

    override fun onSuccessLogout() {
        startActivity(Intent(mContext, LoginActivity::class.java))
        context?.let { it1 -> SharedPrefManager.getInstance(it1).deleteUserId() }
        context?.let { it1 -> SharedPrefManager.getInstance(it1).deleteUserProfile() }
        (mContext as AppCompatActivity).finish()
    }

    override fun showProfileUser(
        nip: String, name: String, email: String,
        school: String, city: String, phone: String
    ) {
        tv_nip?.text = nip
        tv_teacher_name?.text = name
        tv_email?.text = email
        tv_school?.text = school
        tv_city?.text = city
        tv_phone_number?.text = phone
    }

    override fun handleResponse(message: String?) {
        Toast.makeText(
            mContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        swipe_refresh?.isRefreshing = false
    }

    override fun showProgressBar() {
        swipe_refresh?.isRefreshing = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data
            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(200, 200)
                .start(context!!, this@TeacherProfileFragment)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {

                presenter.uploadPhotoProfile(result)

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(
                    context, "Crop Image Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onSuccessUpload(context: Context?, message: String?) {
        presenter.getPhotoFromStorage()
        Toast.makeText(
            context, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showPhotoProfile(photoUrl: String) {
        activity?.let {
            GlideApp.with(it)
                .load(photoUrl)
                .placeholder(R.drawable.profile_placeholder)
                .into(img_profile)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun prepare(view: View) {
        mContext = view.context

        (mContext as AppCompatActivity).setSupportActionBar(toolbar)
        (mContext as AppCompatActivity).supportActionBar?.title = "Profil Guru"

        presenter = ProfilePresenter(mContext, this)
    }

    private fun getPhotoFromStorage(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PERMISSION_STORAGE
                )
            }

        } else {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val galleryIntent = Intent()
                    galleryIntent.type = "image/*"
                    galleryIntent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK)
                }
                return
            }
        }
    }

    companion object {
        const val PERMISSION_STORAGE = 1
        const val GALLERY_PICK = 2
        const val FILE_PICK = 3
    }
}
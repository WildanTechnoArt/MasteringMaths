package com.unpas.masteringmaths.teacher.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.main.activity.LoginActivity
import com.unpas.masteringmaths.teacher.activity.EditProfileActivity
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.presenter.ProfilePresenter
import com.unpas.masteringmaths.teacher.view.ProfileFragmentView
import de.hdodenhof.circleimageview.CircleImageView

class TeacherProfileFragment : Fragment(), ProfileFragmentView.View {

    private lateinit var tvNip: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvSchool: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var fabChangePhoto: FloatingActionButton
    private lateinit var imageProfile: CircleImageView

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

        swipeRefresh.setOnRefreshListener {
            presenter.requestDataUser()
        }

        fabChangePhoto.setOnClickListener {
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
                val alert = AlertDialog.Builder(mContext)
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
        tvNip.text = nip
        tvUsername.text = name
        tvEmail.text = email
        tvSchool.text = school
        tvCity.text = city
        tvPhoneNumber.text = phone
    }

    override fun handleResponse(message: String?) {
        Toast.makeText(
            mContext,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        swipeRefresh.isRefreshing = false
    }

    override fun showProgressBar() {
        swipeRefresh.isRefreshing = true
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
                .into(imageProfile)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun prepare(view: View) {
        tvNip = view.findViewById(R.id.tv_nip)
        tvUsername = view.findViewById(R.id.tv_teacher_name)
        tvEmail = view.findViewById(R.id.tv_email)
        tvSchool = view.findViewById(R.id.tv_school)
        tvCity = view.findViewById(R.id.tv_city)
        tvPhoneNumber = view.findViewById(R.id.tv_phone_number)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        mToolbar = view.findViewById(R.id.toolbar)
        fabChangePhoto = view.findViewById(R.id.fab_change_photo)
        imageProfile = view.findViewById(R.id.img_profile)

        mContext = view.context

        (mContext as AppCompatActivity).setSupportActionBar(mToolbar)

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
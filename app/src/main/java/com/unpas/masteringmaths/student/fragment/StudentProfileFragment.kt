package com.unpas.masteringmaths.student.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.EditProfileStudentActivity
import com.unpas.masteringmaths.student.presenter.ProfilePresenter
import com.unpas.masteringmaths.student.view.ProfileFragmentView
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.activity.LoginActivity
import de.hdodenhof.circleimageview.CircleImageView


/**
 * A simple [Fragment] subclass.
 */
class StudentProfileFragment : Fragment(),ProfileFragmentView.View {

    private lateinit var tvNisn: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvCity: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var fabChangePhoto: FloatingActionButton
    private lateinit var imageProfile: CircleImageView

    private lateinit var presenter: ProfileFragmentView.Presenter
    private lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profil, container, false)
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

    private fun prepare (view: View){
        tvNisn = view.findViewById(R.id.tv_nisn)
        tvUsername = view.findViewById(R.id.tv_students_name)
        tvEmail = view.findViewById(R.id.tv_student_email)
        tvCity = view.findViewById(R.id.tv_address)
        tvPhoneNumber = view.findViewById(R.id.tv_student_phone_number)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        mToolbar = view.findViewById(R.id.toolbar)
        fabChangePhoto = view.findViewById(R.id.fab_change_photo)
        imageProfile = view.findViewById(R.id.img_profile)

        mContext = view.context

        (mContext as AppCompatActivity).setSupportActionBar(mToolbar)

        presenter = ProfilePresenter(mContext, this)

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
                startActivity(Intent(mContext, EditProfileStudentActivity::class.java))
            }
        }
        return true
    }

    override fun onSuccessLogout() {
        startActivity(Intent(mContext, LoginActivity::class.java))
        context?.let { it1 -> SharedPrefManager.getInstance(it1).deleteUserId() }
        (mContext as AppCompatActivity).finish()
    }

    override fun showProfileUser(nisn: String, name: String, email: String, city: String, phone: String) {
        tvNisn.text = nisn
        tvUsername.text = name
        tvEmail.text = email
        tvCity.text = city
        tvPhoneNumber.text = phone
    }

    override fun handleResponse(message: String) {
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

        if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(200, 200)
                .start(context!!, this@StudentProfileFragment)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {

                presenter.uploadPhotoProfile(result)

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(
                    context, "Crop Image Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onSuccessUpload(message: String) {
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
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"),
                GALLERY_PICK
            )
        }
    }

    companion object {
        const val PERMISSION_STORAGE = 1
        const val GALLERY_PICK = 2
    }

}// Required empty public constructor

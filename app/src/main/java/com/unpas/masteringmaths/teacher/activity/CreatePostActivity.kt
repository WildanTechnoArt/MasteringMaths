package com.unpas.masteringmaths.teacher.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.fragment.StudentProfileFragment.Companion.GALLERY_PICK
import com.unpas.masteringmaths.student.fragment.StudentProfileFragment.Companion.PERMISSION_STORAGE
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.FILE_PICK
import com.unpas.masteringmaths.teacher.presenter.PostPresenter
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.teacher.view.PostView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.GET_POST
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_ASSIGNMENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_EDITED
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_TEACHER
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class CreatePostActivity : AppCompatActivity(), PostView.View {

    private lateinit var presenter: PostView.Presenter
    private lateinit var userId: String
    private lateinit var username: String
    private lateinit var nip: String
    private lateinit var classId: String
    private lateinit var photoUrl: String
    private lateinit var className: String
    private var isEdited: Boolean? = false
    private lateinit var getPostId: String
    private lateinit var getPostContent: String
    private var post: String? = null
    private var isDocument: Boolean? = false
    private var isAssisgment: Boolean? = true
    private var isTeacher: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        prepare()
        checkIsAssignment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        post = input_post.text.toString().trim()
        when (item.itemId) {
            R.id.post -> {
                if (post != "") {
                    if (isEdited == true) {
                        getPostId = intent?.getStringExtra(POST_ID).toString()
                        presenter.updatePost(
                            className,
                            userId,
                            getPostId,
                            photoUrl,
                            post,
                            nip,
                            classId,
                            username
                        )
                    } else {
                        if (isTeacher == false) {
                            userId = intent.getStringExtra(TEACHER_ID).toString()
                        }
                        presenter.addPost(className, userId, photoUrl, post, nip, classId, username)
                    }
                } else {
                    Toast.makeText(this, "Isi postingan tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return true
    }

    override fun onSuccessPost(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessUpdate(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun handleResponse(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProfileUser(username: String, nip: String) {
        this.username = username
        this.nip = nip
        tv_teacher_name.text = username
        tv_teacher_nip.text = nip
    }

    override fun showPhotoUser(photoUrl: String) {
        this.photoUrl = photoUrl
        GlideApp.with(this)
            .load(photoUrl)
            .placeholder(R.drawable.profile_placeholder)
            .into(img_profile)
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        val getTitle = intent?.getStringExtra(TOOLBAR_TITLE).toString()
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getTitle
        }

        presenter = PostPresenter(this, this)
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        classId = intent?.getStringExtra(CLASS_ID).toString()
        className = intent?.getStringExtra(CLASS_NAME).toString()
        isEdited = intent?.getBooleanExtra(IS_EDITED, false)

        presenter.requestProfile(userId)
        presenter.requestPhoto(userId)

        if (isEdited == true) {
            getPostContent = intent?.getStringExtra(GET_POST).toString()
            input_post.setText(getPostContent)
        }

        fab_assignment.setOnClickListener {
            val itemMenu = arrayOf("Upload File Dokumen", "Upload File Gambar")
            val alert = MaterialAlertDialogBuilder(this)
                .setTitle("Pilih Opsi")
                .setItems(itemMenu) { _, item ->
                    when (item) {
                        0 -> {
                            isDocument = true
                            getFileFromStorage()
                        }
                        1 -> {
                            isDocument = false
                            getPhotoFromStorage()
                        }
                    }
                }
            alert.create()
            alert.show()
        }
    }

    private fun getFileFromStorage() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_STORAGE
            )

        } else {

            val mimeTypes = arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/vnd.ms-excel",
                "text/plain"
            )

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.isNotEmpty()) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }

            startActivityForResult(Intent.createChooser(intent, "SELECT FILE"), FILE_PICK)
        }
    }

    private fun getPhotoFromStorage() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_STORAGE
            )

        } else {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(galleryIntent, "SELECT IMAGE"),
                GALLERY_PICK
            )
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

                    if (isDocument == true) {
                        val mimeTypes = arrayOf(
                            "application/pdf",
                            "application/msword",
                            "application/vnd.ms-powerpoint",
                            "application/vnd.ms-excel",
                            "text/plain"
                        )

                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.addCategory(Intent.CATEGORY_OPENABLE)

                        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
                        if (mimeTypes.isNotEmpty()) {
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                        }

                        startActivityForResult(
                            Intent.createChooser(intent, "SELECT FILE"),
                            FILE_PICK
                        )
                    } else {
                        val galleryIntent = Intent()
                        galleryIntent.type = "image/*"
                        galleryIntent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            Intent.createChooser(galleryIntent, "SELECT IMAGE"),
                            GALLERY_PICK
                        )
                    }
                }
                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICK && resultCode == Activity.RESULT_OK) {

            val fileData = data?.data
            fileData?.let { presenter.uploadFileDocument(it) }

        } else if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(200, 200)
                .start(this)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {

                presenter.uploadFilePhoto(result)

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(
                    this, "Crop Image Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun showFileName(fileName: String) {
        tv_file_name.text = fileName
    }

    private fun checkIsAssignment() {
        isAssisgment = intent.getBooleanExtra(IS_ASSIGNMENT, false)
        isTeacher = intent.getBooleanExtra(IS_TEACHER, false)
        if (isAssisgment == true || isTeacher == true) {
            img_file.visibility = View.VISIBLE
            tv_file_name.visibility = View.VISIBLE
            fab_assignment.visibility = View.VISIBLE
        } else {
            img_file.visibility = View.GONE
            tv_file_name.visibility = View.GONE
            fab_assignment.visibility = View.GONE
        }
    }
}
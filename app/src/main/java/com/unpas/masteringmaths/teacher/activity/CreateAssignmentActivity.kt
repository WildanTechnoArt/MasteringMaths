package com.unpas.masteringmaths.teacher.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.FILE_PICK
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.GALLERY_PICK
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.PERMISSION_STORAGE
import com.unpas.masteringmaths.teacher.presenter.AssignmentPresenter
import com.unpas.masteringmaths.teacher.view.PostAssigmentView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.GET_POST
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_EDITED
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.activity_create_assignment.*

class CreateAssignmentActivity : AppCompatActivity(), PostAssigmentView.View {

    private lateinit var presenter: PostAssigmentView.Presenter
    private lateinit var userId: String
    private lateinit var username: String
    private lateinit var nip: String
    private lateinit var classId: String
    private lateinit var photoUrl: String
    private lateinit var className: String
    private var isEdited: Boolean? = false
    private lateinit var getPostId: String
    private lateinit var getPostContent: String
    private lateinit var getPostTitle: String
    private var postTitle: String? = null
    private var post: String? = null
    private var isDocument = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_assignment)
        prepare()
        swipe_refresh.setOnRefreshListener {
            presenter.requestPhoto(userId)
            presenter.requestProfile(userId)
        }

        btn_upload_file.setOnClickListener {
            val itemMenu = arrayOf("Upload File Dokumen", "Upload File Gambar")
            val alert = AlertDialog.Builder(this)
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

    private fun prepare() {
        setSupportActionBar(toolbar)

        val title = intent?.getStringExtra(TOOLBAR_TITLE).toString()
        supportActionBar?.apply {
            setTitle(title)
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = AssignmentPresenter(this, this)
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        classId = intent?.getStringExtra(CLASS_ID).toString()
        className = intent?.getStringExtra(CLASS_NAME).toString()
        isEdited = intent?.getBooleanExtra(IS_EDITED, false)
        presenter.requestProfile(userId)
        presenter.requestPhoto(userId)

        if (isEdited == true) {
            getPostContent = intent?.getStringExtra(GET_POST).toString()
            getPostTitle = intent?.getStringExtra(POST_TITLE).toString()
            input_title.setText(getPostTitle)
            input_instructions.setText(getPostContent)
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
            val galleryIntent = Intent()
            galleryIntent.type = "text/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT FILE"), FILE_PICK)
        }
    }

    override fun showFileName(fileName: String) {
        tv_file_name.text = fileName
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

                    if (isDocument) {
                        val galleryIntent = Intent()
                        galleryIntent.type = "file/*"
                        galleryIntent.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            Intent.createChooser(galleryIntent, "SELECT FILE"),
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_post, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        postTitle = input_title.text.toString().trim()
        post = input_instructions.text.toString().trim()
        when (item.itemId) {
            R.id.post -> {
                if (postTitle != null && post != null) {
                    if (isEdited == true) {
                        getPostId = intent?.getStringExtra(POST_ID).toString()
                        presenter.updateData(
                            getPostId,
                            photoUrl,
                            post,
                            postTitle,
                            nip,
                            username,
                            userId,
                            classId,
                            className
                        )
                    } else {
                        presenter.postData(
                            photoUrl,
                            post,
                            postTitle,
                            nip,
                            username,
                            userId,
                            classId,
                            className
                        )
                    }
                } else {
                    Toast.makeText(this, "Tidak boleh ada data yang kosong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return true
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
        swipe_refresh.isRefreshing = false
    }

    override fun showProgressBar() {
        swipe_refresh.isRefreshing = true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
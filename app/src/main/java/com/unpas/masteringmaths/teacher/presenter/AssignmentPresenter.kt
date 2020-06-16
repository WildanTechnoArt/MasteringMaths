package com.unpas.masteringmaths.teacher.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.teacher.view.PostAssigmentView

class AssignmentPresenter(
    private val context: Context,
    private val view: PostAssigmentView.View
) : PostAssigmentView.Presenter {

    private lateinit var db: FirebaseFirestore
    private lateinit var fileUrl: String
    private var resultUri: Uri? = null
    private val fileReference = FirebaseStorage.getInstance().reference

    @SuppressLint("Recycle")
    fun getFileName(uri: Uri?): String {
        var result: String? = null
        if (uri?.scheme.equals("content")) {
            val cursor = uri?.let { context.contentResolver.query(it, null, null, null, null) }
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        }
        if (result == null) {
            result = uri?.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    override fun uploadFilePhoto(result: CropImage.ActivityResult) {
        resultUri = result.uri
        view.showFileName(getFileName(resultUri))
    }

    override fun uploadFileDocument(result: Uri) {
        resultUri = result
        view.showFileName(getFileName(resultUri))
    }

    override fun requestProfile(userId: String) {
        view.showProgressBar()
        db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val username = it.getString("username").toString()
                val nip = it.getString("nip").toString()
                view.showProfileUser(username, nip)
                view.hideProgressBar()
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }

    override fun postData(
        urlPhoto: String, post: String?, postTitle: String?, nip: String,
        username: String, userId: String, codeClass: String, lesson: String
    ) {

        if (resultUri != null) {
            view.showProgressBar()

            val fileURL = "file_tugas/$userId" + "_" + "${resultUri?.lastPathSegment}"
            val filePath = fileReference.child(fileURL)

            filePath.putFile(resultUri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.child(fileURL).downloadUrl
                        .addOnSuccessListener { imageUri: Uri? ->
                            fileUrl = imageUri.toString()

                            val data = PostData()
                            data.urlPhoto = urlPhoto
                            data.nip = nip
                            data.userId = userId
                            data.username = username
                            data.postContent = post
                            data.postTitle = postTitle
                            data.isTeacher = true
                            data.postType = 0
                            data.fileUrl = fileUrl

                            db = FirebaseFirestore.getInstance()
                            db.collection("classRooms")
                                .document(lesson)
                                .collection(userId)
                                .document(codeClass)
                                .collection("posts")
                                .document()
                                .set(data)
                                .addOnSuccessListener {
                                    view.onSuccessPost(context.getString(R.string.success_upload_post))
                                }
                                .addOnFailureListener {
                                    view.handleResponse(context.getString(R.string.error_request))
                                    view.hideProgressBar()
                                }

                        }.addOnFailureListener {
                            view.hideProgressBar()
                            view.handleResponse(context.getString(R.string.upload_failed))
                        }

                } else {
                    view.hideProgressBar()
                    view.handleResponse(context.getString(R.string.upload_failed))
                }
            }
        } else {
            Toast.makeText(context, "Tidak ada file yang di upload", Toast.LENGTH_SHORT).show()
        }
    }

    override fun updateData(
        postKey: String,
        urlPhoto: String,
        post: String?,
        postTitle: String?,
        nip: String,
        username: String,
        userId: String,
        codeClass: String,
        lesson: String
    ) {

        if (resultUri != null) {
            view.showProgressBar()

            val fileURL = "file_tugas/$userId" + "_" + "${resultUri?.lastPathSegment}"
            val filePath = fileReference.child(fileURL)

            filePath.putFile(resultUri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.child(fileURL).downloadUrl
                        .addOnSuccessListener { imageUri: Uri? ->
                            fileUrl = imageUri.toString()

                            val data = PostData()
                            data.urlPhoto = urlPhoto
                            data.nip = nip
                            data.username = username
                            data.postContent = post
                            data.userId = userId
                            data.postTitle = postTitle
                            data.fileUrl = fileUrl
                            data.postType = 0

                            db = FirebaseFirestore.getInstance()
                            db.collection("classRooms")
                                .document(lesson)
                                .collection(userId)
                                .document(codeClass)
                                .collection("posts")
                                .document(postKey)
                                .set(data)
                                .addOnSuccessListener {
                                    view.onSuccessUpdate(context.getString(R.string.update_success))
                                }
                                .addOnFailureListener {
                                    view.handleResponse(context.getString(R.string.error_request))
                                    view.hideProgressBar()
                                }
                        }.addOnFailureListener {
                            view.hideProgressBar()
                            view.handleResponse(context.getString(R.string.upload_failed))
                        }

                } else {
                    view.hideProgressBar()
                    view.handleResponse(context.getString(R.string.upload_failed))
                }
            }
        } else {
            Toast.makeText(context, "Tidak ada file yang di upload", Toast.LENGTH_SHORT).show()
        }
    }

    override fun requestPhoto(userId: String) {
        view.showProgressBar()
        db = FirebaseFirestore.getInstance()
        db.collection("photos")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val url = it.getString("thumbUrl").toString()
                view.showPhotoUser(url)
                view.hideProgressBar()
            }
            .addOnFailureListener {
                view.handleResponse(context.getString(R.string.error_request))
                view.hideProgressBar()
            }
    }
}
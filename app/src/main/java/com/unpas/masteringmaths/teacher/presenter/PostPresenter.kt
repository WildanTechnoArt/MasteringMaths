package com.unpas.masteringmaths.teacher.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.teacher.view.PostView

class PostPresenter(
    private val context: Context,
    private val view: PostView.View
) : PostView.Presenter {

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
                val isTeacher = it.getBoolean("teacher")
                val id: String

                id = if (isTeacher == true) {
                    it.getString("nip").toString()
                } else {
                    it.getString("nisn").toString()
                }

                view.showProfileUser(username, id)
                view.hideProgressBar()
            }
            .addOnFailureListener {
                view.handleResponse(it.localizedMessage?.toString().toString())
                view.hideProgressBar()
            }
    }

    override fun addPost(
        className: String, userId: String, urlPhoto: String,
        postContent: String?, nomorInduk: String, codeClass: String, username: String
    ) {
        view.showProgressBar()

        if (resultUri != null) {

            val fileURL = "file_tugas/$userId" + "_" + "${resultUri?.lastPathSegment}"
            val filePath = fileReference.child(fileURL)

            filePath.putFile(resultUri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.child(fileURL).downloadUrl
                        .addOnSuccessListener { imageUri: Uri? ->
                            fileUrl = imageUri.toString()

                            val data = PostData()
                            data.urlPhoto = urlPhoto
                            data.nip = nomorInduk
                            data.userId = userId
                            data.username = username
                            data.postContent = postContent
                            data.postType = 0
                            data.fileUrl = fileUrl

                            db = FirebaseFirestore.getInstance()
                            db.collection("classRooms")
                                .document(className)
                                .collection(userId)
                                .document(codeClass)
                                .collection("posts")
                                .document()
                                .set(data)
                                .addOnSuccessListener {
                                    view.onSuccessPost(context.getString(R.string.success_upload_post))
                                }
                                .addOnFailureListener {
                                    view.handleResponse(it.localizedMessage?.toString().toString())
                                    view.hideProgressBar()
                                }

                        }.addOnFailureListener {
                            view.hideProgressBar()
                            view.handleResponse(it.localizedMessage?.toString().toString())
                        }

                } else {
                    view.hideProgressBar()
                    view.handleResponse(context.getString(R.string.upload_failed))
                }
            }

        } else {
            val data = PostData()
            data.urlPhoto = urlPhoto
            data.nip = nomorInduk
            data.userId = userId
            data.username = username
            data.userId = userId
            data.postType = 1
            data.postContent = postContent

            db = FirebaseFirestore.getInstance()
            db.collection("classRooms")
                .document(className)
                .collection(userId)
                .document(codeClass)
                .collection("posts")
                .document()
                .set(data)
                .addOnSuccessListener {
                    view.onSuccessPost(context.getString(R.string.success_upload_post))
                }
                .addOnFailureListener {
                    it.localizedMessage?.toString()?.let { it1 -> view.handleResponse(it1) }
                    view.hideProgressBar()
                }
        }
    }

    override fun updatePost(
        className: String,
        userId: String,
        postKey: String,
        urlPhoto: String,
        postContent: String?,
        nomorInduk: String,
        codeClass: String,
        username: String
    ) {
        view.showProgressBar()

        if (resultUri != null) {

            val fileURL = "file_tugas/$userId" + "_" + "${resultUri?.lastPathSegment}"
            val filePath = fileReference.child(fileURL)

            filePath.putFile(resultUri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.child(fileURL).downloadUrl
                        .addOnSuccessListener { imageUri: Uri? ->
                            fileUrl = imageUri.toString()

                            val data = PostData()
                            data.urlPhoto = urlPhoto
                            data.nip = nomorInduk
                            data.userId = userId
                            data.username = username
                            data.postContent = postContent
                            data.postType = 0
                            data.fileUrl = fileUrl

                            db = FirebaseFirestore.getInstance()
                            db.collection("classRooms")
                                .document(className)
                                .collection(userId)
                                .document(codeClass)
                                .collection("posts")
                                .document(postKey)
                                .set(data)
                                .addOnSuccessListener {
                                    view.onSuccessPost(context.getString(R.string.post_has_editted))
                                }
                                .addOnFailureListener {
                                    view.handleResponse(it.localizedMessage?.toString().toString())
                                    view.hideProgressBar()
                                }

                        }.addOnFailureListener {
                            view.hideProgressBar()
                            view.handleResponse(it.localizedMessage?.toString().toString())
                        }

                } else {
                    view.hideProgressBar()
                    view.handleResponse(context.getString(R.string.upload_failed))
                }
            }

        } else {
            val data = PostData()
            data.urlPhoto = urlPhoto
            data.nip = nomorInduk
            data.userId = userId
            data.username = username
            data.userId = userId
            data.postType = 1
            data.postContent = postContent

            db = FirebaseFirestore.getInstance()
            db.collection("classRooms")
                .document(className)
                .collection(userId)
                .document(codeClass)
                .collection("posts")
                .document(postKey)
                .set(data)
                .addOnSuccessListener {
                    view.onSuccessPost(context.getString(R.string.post_has_editted))
                }
                .addOnFailureListener {
                    it.localizedMessage?.toString()?.let { it1 -> view.handleResponse(it1) }
                    view.hideProgressBar()
                }
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
                view.handleResponse(it.localizedMessage?.toString().toString())
                view.hideProgressBar()
            }
    }
}
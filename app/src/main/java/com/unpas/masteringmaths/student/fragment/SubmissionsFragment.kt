package com.unpas.masteringmaths.student.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage

import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.model.SubmissionData
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.FILE_PICK
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.GALLERY_PICK
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment.Companion.PERMISSION_STORAGE
import com.unpas.masteringmaths.utils.UtilsConstant

class SubmissionsFragment : Fragment() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var tvSubmissionStatus: TextView
    private lateinit var tvGrade: TextView
    private lateinit var tvStatus: TextView
    private lateinit var inputTextAnswer: EditText
    private lateinit var line: View
    private lateinit var lineThree: View
    private lateinit var tvFileUrl: TextView
    private lateinit var btnAttach: Button
    private val fileReference = FirebaseStorage.getInstance().reference
    private var mTeacherId: String? = null
    private var mAssignmentId: String? = null
    private var mStudentId: String? = null
    private var mStudentName: String? = null
    private var menuItem: Menu? = null
    private var fileUri: Uri? = null
    private var isDocument = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_submissions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        init(view)
        checkSubmission()
    }

    private fun init(view: View) {
        tvSubmissionStatus = view.findViewById(R.id.tv_submission_status)
        inputTextAnswer = view.findViewById(R.id.input_text)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.isEnabled = false
        tvFileUrl = view.findViewById(R.id.tv_file_name)
        line = view.findViewById(R.id.line)
        lineThree = view.findViewById(R.id.line_three)
        tvGrade = view.findViewById(R.id.tv_grade)
        tvStatus = view.findViewById(R.id.tv_status)
        btnAttach = view.findViewById(R.id.btn_attach)
        btnAttach.isEnabled = false

        mTeacherId = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.TEACHER_ID)
            .toString()
        mAssignmentId =
            (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.ASSIGNMENT_ID)
                .toString()
        mStudentId = SharedPrefManager.getInstance(context).getUserId.toString()
        mStudentName = SharedPrefManager.getInstance(context).getUserName.toString()

        btnAttach.setOnClickListener {
            val itemMenu = arrayOf("Upload File Dokumen", "Upload File Gambar")
            val alert = AlertDialog.Builder(view.context)
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

    private fun getPhotoFromStorage() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it, android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED && activity?.let {
                ContextCompat.checkSelfPermission(
                    it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
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
            startActivityForResult(
                Intent.createChooser(galleryIntent, "SELECT IMAGE"),
                GALLERY_PICK
            )
        }
    }

    private fun getFileFromStorage() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it, android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED && activity?.let {
                ContextCompat.checkSelfPermission(
                    it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED
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
            galleryIntent.type = "text/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT FILE"), FILE_PICK)
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

    @SuppressLint("SetTextI18n")
    private fun checkSubmission() {
        swipeRefreshLayout.isRefreshing = true
        val db = FirebaseFirestore.getInstance()
        db.collection("submissions")
            .document(mTeacherId.toString())
            .collection(mAssignmentId.toString())
            .document(mStudentId.toString())
            .addSnapshotListener { snapshot, _ ->
                swipeRefreshLayout.isRefreshing = false
                btnAttach.isEnabled = true
                if (snapshot?.exists() == false) {
                    tvSubmissionStatus.setTextColor(Color.parseColor("#FFFFB300"))
                    tvSubmissionStatus.text = "Unverified"
                } else {
                    btnAttach.visibility = View.GONE
                    inputTextAnswer.isEnabled = false

                    val getTextAnswer = snapshot?.getString("textAnswer")
                    val isApproved = snapshot?.getBoolean("approved")
                    val grade = snapshot?.getString("grade")

                    line.visibility = View.VISIBLE
                    lineThree.visibility = View.VISIBLE
                    tvGrade.visibility = View.VISIBLE
                    tvStatus.visibility = View.VISIBLE
                    tvSubmissionStatus.visibility = View.VISIBLE

                    if (isApproved == true) {
                        tvSubmissionStatus.text = "Approved"
                        tvSubmissionStatus.setTextColor(Color.parseColor("#FF48D700"))
                    } else {
                        tvSubmissionStatus.text = "Unverified"
                        tvSubmissionStatus.setTextColor(Color.parseColor("#FFFFB300"))
                    }

                    inputTextAnswer.setText(getTextAnswer.toString())
                    tvGrade.text = "Grade: $grade/100"
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICK && resultCode == Activity.RESULT_OK) {

            fileUri = data?.data
            tvFileUrl.text = getFileName(fileUri).toString()

        } else if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setMinCropWindowSize(200, 200)
                .start(context!!, this@SubmissionsFragment)
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == Activity.RESULT_OK) {

                fileUri = result.uri
                tvFileUrl.text = getFileName(fileUri).toString()

            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(
                    context, "Crop Image Error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadFile() {
        val db = FirebaseFirestore.getInstance()

        if (fileUri != null) {
            swipeRefreshLayout.isRefreshing = true

            val fileURL = "submissions/$mStudentId" + "_" + "${fileUri?.lastPathSegment}"
            val filePath = fileReference.child(fileURL)

            filePath.putFile(fileUri!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fileReference.child(fileURL).downloadUrl
                        .addOnSuccessListener { imageUri: Uri? ->
                            val fileUrl = imageUri.toString()
                            val data = SubmissionData()
                            data.textAnswer = inputTextAnswer.text.toString()
                            data.fileUrl = fileUrl
                            data.studentId = mStudentId.toString()
                            data.isApproved = false
                            data.assignmentId = mAssignmentId.toString()
                            data.grade = "0"
                            data.username = mStudentName

                            db.collection("submissions")
                                .document(mTeacherId.toString())
                                .collection(mAssignmentId.toString())
                                .document(mStudentId.toString())
                                .set(data)
                                .addOnSuccessListener {
                                    swipeRefreshLayout.isRefreshing = false
                                    inputTextAnswer.isEnabled = false
                                    tvFileUrl.visibility = View.GONE
                                    btnAttach.visibility = View.GONE
                                    line.visibility = View.VISIBLE
                                    lineThree.visibility = View.VISIBLE
                                    tvGrade.visibility = View.VISIBLE
                                    tvStatus.visibility = View.VISIBLE
                                    tvSubmissionStatus.visibility = View.VISIBLE
                                    Toast.makeText(
                                        context,
                                        "Tugas Berhasil Dikirim",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        context?.getString(R.string.error_request),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    swipeRefreshLayout.isRefreshing = false
                                }

                        }.addOnFailureListener {
                            swipeRefreshLayout.isRefreshing = false
                            Toast.makeText(
                                context,
                                context?.getString(R.string.upload_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(
                        context,
                        context?.getString(R.string.upload_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(context, "Tidak ada file yang di upload", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Recycle")
    fun getFileName(uri: Uri?): String? {
        var result: String? = null
        if (uri?.scheme.equals("content")) {
            val cursor = uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menuItem = menu
        var item: MenuItem?

        val db = FirebaseFirestore.getInstance()
        db.collection("submissions")
            .document(mTeacherId.toString())
            .collection(mAssignmentId.toString())
            .document(mStudentId.toString())
            .addSnapshotListener { snapshot, _ ->
                if (snapshot?.exists() == true) {
                    val getIsApproved = snapshot.getBoolean("approved")
                    if (getIsApproved == true) {
                        item = menuItem?.findItem(R.id.submit) as MenuItem
                        item?.title = "KIRIM ULANG"
                        item?.isVisible = true
                    } else {
                        item = menuItem?.findItem(R.id.submit) as MenuItem
                        item?.isVisible = false
                    }
                } else {
                    item = menuItem?.findItem(R.id.submit) as MenuItem
                    item?.title = "KIRIM"
                    item?.isVisible = true
                }
            }
        inflater.inflate(R.menu.student_assignment, menuItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.submit -> {
                if (menuItem?.getItem(0)?.title == "KIRIM") {
                    uploadFile()
                } else {
                    menuItem?.getItem(0)?.title = "KIRIM"
                    inputTextAnswer.isEnabled = true
                    tvFileUrl.visibility = View.VISIBLE
                    btnAttach.visibility = View.VISIBLE
                    line.visibility = View.GONE
                    lineThree.visibility = View.GONE
                    tvGrade.visibility = View.GONE
                    tvStatus.visibility = View.GONE
                    tvSubmissionStatus.visibility = View.GONE
                }
            }
        }
        return true
    }
}

package com.unpas.masteringmaths.teacher.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.model.SubmissionData
import com.unpas.masteringmaths.teacher.adapter.TecherGradedAdapter
import com.unpas.masteringmaths.utils.UtilsConstant
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.LINK_URL
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_CONTENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_NIP
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.USERNAME
import kotlinx.android.synthetic.main.activity_view_assignment.*

class ViewAssignmentActivity : AppCompatActivity() {

    private var mTeacherNip: String? = null
    private var mUsername: String? = null
    private var mPostContent: String? = null
    private var mPostTitle: String? = null
    private var mLinkUrl: String? = null
    private var mTeacherId: String? = null
    private var mAssignmentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_assignment)
        init()
        getAssignmentData()
        setAssignmentData()
        setupDatabse()
        getDataCount()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mTeacherId = intent?.getStringExtra(UtilsConstant.TEACHER_ID).toString()
        mAssignmentId = intent?.getStringExtra(UtilsConstant.ASSIGNMENT_ID).toString()

        rv_graded_list.layoutManager = LinearLayoutManager(this)
        rv_graded_list.setHasFixedSize(true)
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("submissions")
            .document(mTeacherId.toString())
            .collection(mAssignmentId.toString())

        val options = FirestoreRecyclerOptions.Builder<SubmissionData>()
            .setQuery(query, SubmissionData::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = TecherGradedAdapter(options)
        rv_graded_list.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("submissions")
            .document(mTeacherId.toString())
            .collection(mAssignmentId.toString())

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rv_graded_list.visibility = View.VISIBLE
                tv_no_graded.visibility = View.GONE
            } else {
                rv_graded_list.visibility = View.GONE
                tv_no_graded.visibility = View.VISIBLE
            }
        }
    }

    private fun getAssignmentData() {
        mTeacherNip = intent.getStringExtra(TEACHER_NIP)
        mUsername = intent.getStringExtra(USERNAME)
        mPostContent = intent.getStringExtra(POST_CONTENT)
        mPostTitle = intent.getStringExtra(POST_TITLE)
        mLinkUrl = intent.getStringExtra(LINK_URL)
    }

    private fun setAssignmentData() {
        GlideApp.with(this)
            .load(mLinkUrl)
            .placeholder(R.drawable.profile_placeholder)
            .into(img_photo)

        tv_username.text = mUsername
        tv_teacher_nip.text = mTeacherNip
        tv_assignment_title.text = mPostTitle
        tv_description.text = mPostContent

        btn_download.setOnClickListener {
            try {
                val pdfUrl = Uri.parse(mLinkUrl)
                val intent = Intent(Intent.ACTION_VIEW, pdfUrl)
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this,
                    "Tidak ada aplikasi yang dapat menangani permintaan ini. Silakan instal browser web",
                    Toast.LENGTH_SHORT
                ).show()
                ex.printStackTrace()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
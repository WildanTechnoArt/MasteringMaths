package com.unpas.masteringmaths.student.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.LINK_URL
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_CONTENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_NIP
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.USERNAME
import de.hdodenhof.circleimageview.CircleImageView

class InstructionsFragment : Fragment() {

    private var mTeacherNip: String? = null
    private var mUsername: String? = null
    private var mPostContent: String? = null
    private var mPostTitle: String? = null
    private var mLinkUrl: String? = null

    private lateinit var tvUsername: TextView
    private lateinit var tvTeacherId: TextView
    private lateinit var tvAssignmentTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnViewAssignment: Button
    private lateinit var imgPhoto: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getAssignmentData()
        setAssignmentData()
    }

    private fun init(view: View) {
        tvUsername = view.findViewById(R.id.tv_username)
        tvTeacherId = view.findViewById(R.id.tv_teacher_nip)
        tvAssignmentTitle = view.findViewById(R.id.tv_assignment_title)
        tvDescription = view.findViewById(R.id.tv_description)
        btnViewAssignment = view.findViewById(R.id.btn_download)
        imgPhoto = view.findViewById(R.id.img_photo)
    }

    private fun getAssignmentData() {
        mTeacherNip = (context as AppCompatActivity).intent.getStringExtra(TEACHER_NIP)
        mUsername = (context as AppCompatActivity).intent.getStringExtra(USERNAME)
        mPostContent = (context as AppCompatActivity).intent.getStringExtra(POST_CONTENT)
        mPostTitle = (context as AppCompatActivity).intent.getStringExtra(POST_TITLE)
        mLinkUrl = (context as AppCompatActivity).intent.getStringExtra(LINK_URL)
    }

    private fun setAssignmentData() {
        GlideApp.with(this)
            .load(mLinkUrl)
            .placeholder(R.drawable.profile_placeholder)
            .into(imgPhoto)

        tvUsername.text = mUsername
        tvTeacherId.text = mTeacherNip
        tvAssignmentTitle.text = mPostTitle
        tvDescription.text = mPostContent

        btnViewAssignment.setOnClickListener {
            try {
                val pdfUrl = Uri.parse(mLinkUrl)
                val intent = Intent(Intent.ACTION_VIEW, pdfUrl)
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    "Tidak ada aplikasi yang dapat menangani permintaan ini. Silakan instal browser web",
                    Toast.LENGTH_SHORT
                ).show()
                ex.printStackTrace()
            }
        }
    }
}

package com.unpas.masteringmaths.teacher.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.adapter.FirestorePostAdapter
import com.unpas.masteringmaths.teacher.PostItemListener
import com.unpas.masteringmaths.teacher.activity.CreatePostActivity
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.GET_POST
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_ASSIGNMENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_EDITED
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment(), PostItemListener {

    private lateinit var teacherId: String
    private lateinit var codeClass: String
    private lateinit var className: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh?.isEnabled = false

        teacherId = (context as AppCompatActivity).intent?.getStringExtra(TEACHER_ID).toString()
        codeClass = (context as AppCompatActivity).intent?.getStringExtra(CLASS_ID).toString()
        className = (context as AppCompatActivity).intent?.getStringExtra(CLASS_NAME).toString()

        setupDatabse()
        getDataCount()
    }

    override fun onUpdateClick(
        teacherUid: String,
        toolbarName: String,
        isEdited: Boolean,
        post: String,
        postId: String,
        isAssignment: Boolean
    ) {
        val intent = Intent(context, CreatePostActivity::class.java)

        intent.putExtra(TOOLBAR_TITLE, toolbarName)
        intent.putExtra(IS_EDITED, isEdited)
        intent.putExtra(GET_POST, post)
        intent.putExtra(POST_ID, postId)
        intent.putExtra(CLASS_ID, codeClass)
        intent.putExtra(CLASS_NAME, className)
        intent.putExtra(IS_ASSIGNMENT, isAssignment)
        startActivity(intent)
    }

    override fun onDeletePost(postId: String) {
        swipe_refresh?.isRefreshing = true
        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(codeClass)
            .collection("posts")
            .document(postId)
            .delete()
            .addOnSuccessListener {
                swipe_refresh?.isRefreshing = false
                Toast.makeText(context, getString(R.string.post_has_deleted), Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                swipe_refresh?.isRefreshing = false
                Toast.makeText(context, it.localizedMessage?.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(codeClass)
            .collection("posts")

        val options = FirestoreRecyclerOptions.Builder<PostData>()
            .setQuery(query, PostData::class.java)
            .setLifecycleOwner(this)
            .build()

        rv_post_list?.layoutManager = LinearLayoutManager(context)
        rv_post_list?.setHasFixedSize(true)

        val adapter = FirestorePostAdapter(options, this)
        rv_post_list?.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(codeClass)
            .collection("posts")

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rv_post_list?.visibility = View.VISIBLE
                tv_nothing_posts?.visibility = View.GONE
            } else {
                rv_post_list?.visibility = View.GONE
                tv_nothing_posts?.visibility = View.VISIBLE
            }
        }
    }
}
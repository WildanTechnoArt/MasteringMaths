package com.unpas.masteringmaths.teacher.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.PostItemListener
import com.unpas.masteringmaths.teacher.activity.CreateAssignmentActivity
import com.unpas.masteringmaths.teacher.activity.PostActivity
import com.unpas.masteringmaths.main.adapter.FirestorePostAdapter
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.GET_POST
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.IS_EDITED
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TOOLBAR_TITLE

class PostsFragment : Fragment(), PostItemListener {

    private lateinit var rvPostList: RecyclerView
    private lateinit var tvNoPost: TextView
    private lateinit var userId: String
    private lateinit var codeClass: String
    private lateinit var className: String
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvPostList = view.findViewById(R.id.rv_post_list)
        tvNoPost = view.findViewById(R.id.tv_nothing_posts)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.isEnabled = false

        userId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        codeClass = (context as AppCompatActivity).intent?.getStringExtra(CLASS_ID).toString()
        className = (context as AppCompatActivity).intent?.getStringExtra(CLASS_NAME).toString()
        setupDatabse()
        getDataCount()
    }

    override fun onUpdateClick(
        teacherId: String, toolbarName: String, isEdited: Boolean, isAssignment: Boolean, post: String,
        postTitle: String?, postId: String
    ) {

        val intent: Intent? = if (isAssignment) {
            Intent(context, CreateAssignmentActivity::class.java)
        } else {
            Intent(context, PostActivity::class.java)
        }

        intent?.putExtra(TOOLBAR_TITLE, toolbarName)
        intent?.putExtra(IS_EDITED, isEdited)
        intent?.putExtra(GET_POST, post)
        intent?.putExtra(POST_ID, postId)
        intent?.putExtra(CLASS_ID, codeClass)
        intent?.putExtra(CLASS_NAME, className)
        intent?.putExtra(POST_TITLE, postTitle)
        startActivity(intent)
    }

    override fun onDeletePost(postId: String) {
        swipeRefresh.isRefreshing = true
        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("posts")
            .document(postId)
            .delete()
            .addOnSuccessListener {
                swipeRefresh.isRefreshing = false
                Toast.makeText(context, getString(R.string.post_has_editted), Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                swipeRefresh.isRefreshing = false
                Toast.makeText(context, getString(R.string.error_request), Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("posts")

        val options = FirestoreRecyclerOptions.Builder<PostData>()
            .setQuery(query, PostData::class.java)
            .setLifecycleOwner(this)
            .build()

        rvPostList.layoutManager = LinearLayoutManager(context)
        rvPostList.setHasFixedSize(true)

        val adapter = FirestorePostAdapter(options, this)
        adapter.setUserId(userId)
        rvPostList.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("posts")

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rvPostList.visibility = View.VISIBLE
                tvNoPost.visibility = View.GONE
            } else {
                rvPostList.visibility = View.GONE
                tvNoPost.visibility = View.VISIBLE
            }
        }
    }
}
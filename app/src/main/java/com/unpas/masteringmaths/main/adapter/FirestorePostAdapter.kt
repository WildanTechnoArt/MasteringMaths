package com.unpas.masteringmaths.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.AssignmentViewActivity
import com.unpas.masteringmaths.teacher.PostItemListener
import com.unpas.masteringmaths.teacher.activity.ViewAssignmentActivity
import com.unpas.masteringmaths.teacher.model.PostData
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.ASSIGNMENT_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.LINK_URL
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.POST_CONTENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_NIP
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TYPE_POST_ASSIGNMENT
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.USERNAME
import kotlinx.android.synthetic.main.assignment_item.view.*
import kotlinx.android.synthetic.main.post_item.view.*

class FirestorePostAdapter(
    private val options: FirestoreRecyclerOptions<PostData>,
    private val listener: PostItemListener
) :
    FirestoreRecyclerAdapter<PostData, RecyclerView.ViewHolder>(options) {

    private var userId: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val view: View

        if (viewType == TYPE_POST_ASSIGNMENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.assignment_item, parent, false)
            viewHolder = PostAssignmentViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_item, parent, false)
            viewHolder = PostContentViewHolder(view)
        }

        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (options.snapshots[position].postType == TYPE_POST_ASSIGNMENT) {
            0
        } else {
            1
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: PostData) {
        val context = holder.itemView.context
        val postId = snapshots.getSnapshot(position).id
        val post = getItem(position)
        val isTeacher = SharedPrefManager.getInstance(context).getUserStatus.toString()
        userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        if (post.postType == TYPE_POST_ASSIGNMENT) {
            val view = holder.itemView
            val getUsername = item.username.toString()
            val getPostContent = item.postContent.toString()
            val getTeacherNip = "NIP: ${item.nip}"
            val getTeacherUserId = item.userId.toString()
            val getLinkUrl = item.fileUrl.toString()

            view.tv_assig_username.text = getUsername
            view.tv_assignment_desc.text = getPostContent
            view.tv_assig_id.text = getTeacherNip

            GlideApp.with(context.applicationContext)
                .load(item.urlPhoto.toString())
                .placeholder(R.drawable.profile_placeholder)
                .into(view.img_assig_profile)

            if (post.userId == userId) {
                view.btn_assig_menu.visibility = View.VISIBLE
            } else {
                view.btn_assig_menu.visibility = View.GONE
            }

            view.btn_assig_menu.setOnClickListener {
                val menuItem = arrayOf("Edit", "Hapus")

                val alert = MaterialAlertDialogBuilder(context)
                    .setTitle("Pilih Tindakan")
                    .setItems(menuItem) { _, menu ->
                        when (menu) {
                            0 -> {
                                listener.onUpdateClick(
                                    userId.toString(),
                                    "Edit Task",
                                    true,
                                    item.postContent.toString(),
                                    postId,
                                    true
                                )
                            }
                            1 -> {
                                listener.onDeletePost(postId)
                            }
                        }
                    }

                alert.create()
                alert.show()
            }

            view.btn_view_assignment.setOnClickListener {
                if (isTeacher == "Guru") {
                    val intent = Intent(context, ViewAssignmentActivity::class.java)
                    intent.putExtra(USERNAME, getUsername)
                    intent.putExtra(POST_CONTENT, getPostContent)
                    intent.putExtra(ASSIGNMENT_ID, postId)
                    intent.putExtra(TEACHER_ID, getTeacherUserId)
                    intent.putExtra(TEACHER_NIP, getTeacherNip)
                    intent.putExtra(LINK_URL, getLinkUrl)
                    context.startActivity(intent)
                } else {
                    val intent = Intent(context, AssignmentViewActivity::class.java)
                    intent.putExtra(USERNAME, getUsername)
                    intent.putExtra(POST_CONTENT, getPostContent)
                    intent.putExtra(ASSIGNMENT_ID, postId)
                    intent.putExtra(TEACHER_ID, getTeacherUserId)
                    intent.putExtra(TEACHER_NIP, getTeacherNip)
                    intent.putExtra(LINK_URL, getLinkUrl)
                    context.startActivity(intent)
                }
            }

        } else {
            val view = holder.itemView

            GlideApp.with(context.applicationContext)
                .load(item.urlPhoto.toString())
                .placeholder(R.drawable.profile_placeholder)
                .into(view.img_profile)

            view.tv_username.text = item.username.toString()
            view.tv_nomor_induk.text = "NIP: ${item.nip.toString()}"
            view.tv_post_caption.text = item.postContent.toString()

            if (post.userId == userId) {
                view.btn_menu.visibility = View.VISIBLE
            } else {
                view.btn_menu.visibility = View.GONE
            }

            view.btn_menu.setOnClickListener {
                val menuItem = arrayOf("Edit", "Hapus")

                val alert = MaterialAlertDialogBuilder(context)
                    .setTitle("Pilih Tindakan")
                    .setItems(menuItem) { _, menu ->
                        when (menu) {
                            0 -> {
                                listener.onUpdateClick(
                                    userId.toString(),
                                    "Edit Postingan",
                                    true,
                                    item.postContent.toString(),
                                    postId,
                                    false
                                )
                            }
                            1 -> {
                                listener.onDeletePost(postId)
                            }
                        }
                    }

                alert.create()
                alert.show()
            }
        }
    }

    inner class PostContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class PostAssignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
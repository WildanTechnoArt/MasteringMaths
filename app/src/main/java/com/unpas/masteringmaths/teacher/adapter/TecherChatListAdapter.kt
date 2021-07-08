package com.unpas.masteringmaths.teacher.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.teacher.activity.TeacherChatActivity
import com.unpas.masteringmaths.teacher.model.ListChat
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.STUDENT_ID
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.chat_list_item.view.*

class TecherChatListAdapter (options: FirestoreRecyclerOptions<ListChat>)
    : FirestoreRecyclerAdapter<ListChat, TecherChatListAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_list_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: ListChat) {

        //val getClassKey = snapshots.getSnapshot(position).id
        val context = holder.itemView.context
        val getUserId = item.userId.toString()

        holder.apply {
            containerView.tv_username.text = "Nama: ${item.username.toString()}"
            containerView.tv_user_id.text = "NISN: ${item.nomorInduk.toString()}"

            val db = FirebaseFirestore.getInstance()
            db.collection("photos")
                .document(getUserId)
                .get()
                .addOnSuccessListener {
                    val photoUrl = it.getString("photoUrl").toString()
                    GlideApp.with(context)
                        .load(photoUrl)
                        .placeholder(R.drawable.profile_placeholder)
                        .into(containerView.img_photo)
                }

            containerView.cl_item_view.setOnClickListener {
                val intent = Intent(context, TeacherChatActivity::class.java)
                intent.putExtra(STUDENT_ID, getUserId)
                context.startActivity(intent)
            }
        }

    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
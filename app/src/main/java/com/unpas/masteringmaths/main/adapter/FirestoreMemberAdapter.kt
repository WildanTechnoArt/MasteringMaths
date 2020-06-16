package com.unpas.masteringmaths.main.adapter

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
import com.unpas.masteringmaths.main.activity.ProfileUserActivity
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.USER_ID
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.member_item.view.*
import kotlinx.android.synthetic.main.post_item.view.img_profile
import kotlinx.android.synthetic.main.post_item.view.tv_username

class FirestoreMemberAdapter(options: FirestoreRecyclerOptions<MemberData>) :
    FirestoreRecyclerAdapter<MemberData, FirestoreMemberAdapter.ViewHolder>(options) {

    companion object{
        val memberIdList = arrayListOf<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: MemberData) {

        val context = holder.itemView.context
        val userId = item.userId.toString()

        memberIdList.add(snapshots.getSnapshot(position).id)

        holder.apply {
            containerView.tv_username.text = item.username.toString()
            containerView.tv_status.text = item.status.toString()

            val db = FirebaseFirestore.getInstance()
            db.collection("photos")
                .document(userId)
                .get()
                .addOnSuccessListener {
                    val photoUrl = it.getString("photoUrl").toString()
                    GlideApp.with(context.applicationContext)
                        .load(photoUrl)
                        .placeholder(R.drawable.profile_placeholder)
                        .into(containerView.img_profile)
                }

            containerView.setOnClickListener {
                val intent = Intent(context, ProfileUserActivity::class.java)
                intent.putExtra(USER_ID, userId)
                context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
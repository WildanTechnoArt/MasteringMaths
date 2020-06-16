package com.unpas.masteringmaths.teacher.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.adapter.FirestoreMemberAdapter
import com.unpas.masteringmaths.teacher.model.MemberData
import com.unpas.masteringmaths.utils.UtilsConstant

class MembersFragment : Fragment() {

    private lateinit var rvMemberList: RecyclerView
    private lateinit var tvNoMember: TextView
    private lateinit var userId: String
    private lateinit var codeClass: String
    private lateinit var className: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_members, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMemberList = view.findViewById(R.id.rv_member_list)
        tvNoMember = view.findViewById(R.id.tv_nothing_members)
        userId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        codeClass = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_ID).toString()
        className = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()
        setupDatabse()
        getDataCount()
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("members")

        val options = FirestoreRecyclerOptions.Builder<MemberData>()
            .setQuery(query, MemberData::class.java)
            .setLifecycleOwner(this)
            .build()

        rvMemberList.layoutManager = LinearLayoutManager(context)
        rvMemberList.setHasFixedSize(true)

        val adapter = FirestoreMemberAdapter(options)
        rvMemberList.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("members")

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rvMemberList.visibility = View.VISIBLE
                tvNoMember.visibility = View.GONE
            } else {
                rvMemberList.visibility = View.GONE
                tvNoMember.visibility = View.VISIBLE
            }
        }
    }
}
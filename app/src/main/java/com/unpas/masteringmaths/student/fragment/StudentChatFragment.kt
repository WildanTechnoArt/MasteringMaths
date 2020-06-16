package com.unpas.masteringmaths.student.fragment

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.adapter.StudentChatListAdapter
import com.unpas.masteringmaths.teacher.model.ListChat

class StudentChatFragment : Fragment() {

    private lateinit var chatList: RecyclerView
    private lateinit var txtInfo: TextView
    private lateinit var mUserId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        prepare(view)
        setupDatabse()
        getDataCount()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_chat, menu)
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("users")
            .document("chat")
            .collection(mUserId)

        val options = FirestoreRecyclerOptions.Builder<ListChat>()
            .setQuery(query, ListChat::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = StudentChatListAdapter(options)
        chatList.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("users")
            .document("chat")
            .collection(mUserId)

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                chatList.visibility = View.VISIBLE
                txtInfo.visibility = View.GONE
            } else {
                chatList.visibility = View.GONE
                txtInfo.visibility = View.VISIBLE
            }
        }
    }

    private fun prepare(view: View) {
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        chatList = view.findViewById(R.id.rv_chat_list)
        txtInfo = view.findViewById(R.id.tv_nothing_chat)
        (view.context as AppCompatActivity).setSupportActionBar(toolbar)

        mUserId = SharedPrefManager.getInstance(context).getUserId.toString()

        chatList.layoutManager = LinearLayoutManager(context)
        chatList.setHasFixedSize(true)
    }
}

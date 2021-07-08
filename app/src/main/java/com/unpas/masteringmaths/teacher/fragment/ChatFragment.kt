package com.unpas.masteringmaths.teacher.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.adapter.TecherChatListAdapter
import com.unpas.masteringmaths.teacher.model.ListChat
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ChatFragment : Fragment() {

    private lateinit var mUserId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        prepare(view)
        setupDatabse()
        getDataCount()
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

        val adapter = TecherChatListAdapter(options)
        rv_chat_list?.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("users")
            .document("chat")
            .collection(mUserId)

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rv_chat_list?.visibility = View.VISIBLE
                tv_nothing_chat?.visibility = View.GONE
            } else {
                rv_chat_list?.visibility = View.GONE
                tv_nothing_chat?.visibility = View.VISIBLE
            }
        }
    }

    private fun prepare(view: View) {
        (view.context as AppCompatActivity).setSupportActionBar(toolbar)
        (view.context as AppCompatActivity).title = "Chat"

        mUserId = SharedPrefManager.getInstance(context).getUserId.toString()

        rv_chat_list?.layoutManager = LinearLayoutManager(context)
        rv_chat_list?.setHasFixedSize(true)
    }
}
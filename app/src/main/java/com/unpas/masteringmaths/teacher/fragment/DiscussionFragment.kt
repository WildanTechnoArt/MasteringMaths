package com.unpas.masteringmaths.teacher.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
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
import com.unpas.masteringmaths.main.adapter.FirestoreDiscussionAdapter
import com.unpas.masteringmaths.main.model.ForumMessage
import com.unpas.masteringmaths.utils.UtilsConstant
import java.util.*

class DiscussionFragment : Fragment() {

    private lateinit var rvChatList: RecyclerView
    private lateinit var inputMessage: EditText
    private lateinit var btnSendMessage: ImageButton
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var adapter: FirestoreDiscussionAdapter? = null

    private lateinit var userId: String
    private lateinit var codeClass: String
    private lateinit var className: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepare(view)
        getChatMessage()

        inputMessage.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnSendMessage.isEnabled = s.toString().isNotEmpty()
            }

        })

        btnSendMessage.setOnClickListener {
            val imm: InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(swipeRefresh.windowToken, 0)
            sendChatMessage()
        }
    }

    private fun getChatMessage(){
        val query = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("chatRoom")
            .orderBy("date")

        val options = FirestoreRecyclerOptions.Builder<ForumMessage>()
            .setQuery(query, ForumMessage::class.java)
            .setLifecycleOwner(this)
            .build()

        rvChatList.layoutManager = LinearLayoutManager(context)
        rvChatList.setHasFixedSize(true)

        adapter = context?.let { FirestoreDiscussionAdapter(it, options) }
        rvChatList.adapter = adapter
    }

    private fun prepare(view: View) {
        inputMessage = view.findViewById(R.id.input_message)
        btnSendMessage = view.findViewById(R.id.btn_send_message)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        swipeRefresh.isEnabled = false
        rvChatList = view.findViewById(R.id.rv_chat_list)
        rvChatList.layoutManager = LinearLayoutManager(view.context)
        rvChatList.setHasFixedSize(true)

        userId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        codeClass = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_ID).toString()
        className = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()
    }

    private fun sendChatMessage() {
        swipeRefresh.isRefreshing = true

        val data = ForumMessage()
        data.username = SharedPrefManager.getInstance(context).getUserName.toString()
        data.photoUrl = SharedPrefManager.getInstance(context).getUserPhoto.toString()
        data.text = inputMessage.text.toString()
        data.status = SharedPrefManager.getInstance(context).getUserStatus.toString()
        data.userId = userId
        data.date =  Calendar.getInstance().time

        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(userId)
            .document(codeClass)
            .collection("chatRoom")
            .document()
            .set(data)
            .addOnSuccessListener {
                inputMessage.setText("")
                swipeRefresh.isRefreshing = false
                rvChatList.post { adapter?.itemCount?.minus(1)?.let {
                        it1 -> rvChatList.smoothScrollToPosition(it1) } }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Pesan gagal dikirim", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false

            }
    }
}
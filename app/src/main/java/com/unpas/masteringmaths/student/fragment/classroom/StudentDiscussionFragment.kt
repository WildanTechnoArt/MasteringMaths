package com.unpas.masteringmaths.student.fragment.classroom

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.adapter.FirestoreDiscussionAdapter
import com.unpas.masteringmaths.main.model.ForumMessage
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.fragment_discussion.*
import java.util.*

class StudentDiscussionFragment : Fragment() {

    private var adapter: FirestoreDiscussionAdapter? = null

    private lateinit var teacherId: String
    private lateinit var userId: String
    private lateinit var codeClass: String
    private lateinit var className: String
    private lateinit var photoUrl: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_discussion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepare(view)
        getChatMessage()

        input_message?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_send_message.isEnabled = s.toString().isNotEmpty()
            }

        })

        btn_send_message?.setOnClickListener {
            val imm: InputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(swipe_refresh?.windowToken, 0)
            sendChatMessage()
        }

        val mAuth = FirebaseAuth.getInstance().currentUser
        val database = FirebaseFirestore.getInstance()
        database.collection("photos").document(mAuth?.uid.toString())
            .get()
            .addOnSuccessListener {
                photoUrl = it.getString("photoUrl").toString()

            }.addOnFailureListener {
            }
    }

    private fun getChatMessage(){
        val query = FirebaseFirestore.getInstance()
            .collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(codeClass)
            .collection("chatRoom")
            .orderBy("date")

        val options = FirestoreRecyclerOptions.Builder<ForumMessage>()
            .setQuery(query, ForumMessage::class.java)
            .setLifecycleOwner(this)
            .build()

        rv_chat_list?.layoutManager = LinearLayoutManager(context)
        rv_chat_list?.setHasFixedSize(true)

        adapter = context?.let { FirestoreDiscussionAdapter(it, options) }
        rv_chat_list?.adapter = adapter
    }

    private fun prepare(view: View) {
        swipe_refresh?.isEnabled = false
        rv_chat_list?.layoutManager = LinearLayoutManager(view.context)
        rv_chat_list?.setHasFixedSize(true)

        teacherId = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.TEACHER_ID).toString()
        codeClass = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_ID).toString()
        className = (context as AppCompatActivity).intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()
    }

    private fun sendChatMessage() {
        swipe_refresh?.isRefreshing = true

        userId = SharedPrefManager.getInstance(context).getUserId.toString()

        val data = ForumMessage()
        data.username = SharedPrefManager.getInstance(context).getUserName.toString()
        data.photoUrl = photoUrl
        data.text = input_message.text.toString()
        data.status = SharedPrefManager.getInstance(context).getUserStatus.toString()
        data.userId = userId
        data.date = Calendar.getInstance().time

        val db = FirebaseFirestore.getInstance()
        db.collection("classRooms")
            .document(className)
            .collection(teacherId)
            .document(codeClass)
            .collection("chatRoom")
            .document()
            .set(data)
            .addOnSuccessListener {
                input_message.setText("")
                swipe_refresh?.isRefreshing = false
                rv_chat_list.post { adapter?.itemCount?.minus(1)?.let {
                        it1 -> rv_chat_list?.smoothScrollToPosition(it1) } }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Message failed to send", Toast.LENGTH_SHORT).show()
                swipe_refresh?.isRefreshing = false
            }
    }
}
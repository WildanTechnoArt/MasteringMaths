package com.unpas.masteringmaths.student.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.student.StudentClassListener
import com.unpas.masteringmaths.student.activity.InsideClassActivity
import com.unpas.masteringmaths.student.activity.StudentClassRoomActivity
import com.unpas.masteringmaths.student.adapter.FirestoreStudentClassAdapter
import com.unpas.masteringmaths.student.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import kotlinx.android.synthetic.main.fragment_student_kelas.*

class ClassFragment : Fragment(), StudentClassListener {

    private lateinit var mUserId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_kelas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (context as AppCompatActivity).setSupportActionBar(toolbar)

        rv_class_list?.layoutManager = LinearLayoutManager(view.context)
        rv_class_list?.setHasFixedSize(true)

        mUserId = SharedPrefManager.getInstance(view.context).getUserId.toString()

        setupDatabse()
        getDataCount()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.class_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.join_class -> startActivity(Intent(context, InsideClassActivity::class.java))
        }
        return true
    }

    override fun onClickListener(
        classTitle: String,
        className: String,
        teacherId: String,
        key: String
    ) {
        val intent = Intent(context, StudentClassRoomActivity::class.java)
        intent.putExtra(CLASS_TITLE, classTitle)
        intent.putExtra(TEACHER_ID, teacherId)
        intent.putExtra(CLASS_ID, key)
        intent.putExtra(CLASS_NAME, className)
        startActivity(intent)
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("students")
            .document(mUserId)
            .collection("classList")

        val options = FirestoreRecyclerOptions.Builder<DataClass>()
            .setQuery(query, DataClass::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = FirestoreStudentClassAdapter(options, this)
        rv_class_list?.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("students")
            .document(mUserId)
            .collection("classList")

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rv_class_list?.visibility = View.VISIBLE
                tv_nothing_class?.visibility = View.GONE
            } else {
                rv_class_list?.visibility = View.GONE
                tv_nothing_class?.visibility = View.VISIBLE
            }
        }
    }
}

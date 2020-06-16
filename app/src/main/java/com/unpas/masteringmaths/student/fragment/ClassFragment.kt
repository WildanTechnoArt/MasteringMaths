package com.unpas.masteringmaths.student.fragment

import android.content.Intent
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
import com.unpas.masteringmaths.student.StudentClassListener
import com.unpas.masteringmaths.student.activity.InsideClassActivity
import com.unpas.masteringmaths.student.activity.StudentClassRoomActivity
import com.unpas.masteringmaths.student.adapter.FirestoreStudentClassAdapter
import com.unpas.masteringmaths.student.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID

class ClassFragment : Fragment(), StudentClassListener {

    private lateinit var rvClassList: RecyclerView
    private lateinit var tvNothingClass: TextView
    private lateinit var mUserId: String
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_kelas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mToolbar = view.findViewById(R.id.toolbar)
        (context as AppCompatActivity).setSupportActionBar(mToolbar)

        tvNothingClass = view.findViewById(R.id.tv_nothing_class)
        rvClassList = view.findViewById(R.id.rv_class_list)
        rvClassList.layoutManager = LinearLayoutManager(view.context)
        rvClassList.setHasFixedSize(true)

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
        rvClassList.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("students")
            .document(mUserId)
            .collection("classList")

        db.addSnapshotListener { snapshot, _ ->
            if ((snapshot?.size() ?: 0) > 0) {
                rvClassList.visibility = View.VISIBLE
                tvNothingClass.visibility = View.GONE
            } else {
                rvClassList.visibility = View.GONE
                tvNothingClass.visibility = View.VISIBLE
            }
        }
    }
}

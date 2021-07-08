package com.unpas.masteringmaths.teacher.fragment

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
import com.unpas.masteringmaths.teacher.ClassListListener
import com.unpas.masteringmaths.teacher.activity.ClassRoomActivity
import com.unpas.masteringmaths.teacher.activity.CreateClassActivity
import com.unpas.masteringmaths.teacher.adapter.FirestoreClassAdapter
import com.unpas.masteringmaths.teacher.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_GRADE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.TEACHER_ID
import kotlinx.android.synthetic.main.fragment_class_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class ClassListFragment : Fragment(), ClassListListener {

    private lateinit var mUserId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_class_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (context as AppCompatActivity).setSupportActionBar(toolbar)
        (context as AppCompatActivity).title = "Class List"
        prepare(view)
        setupDatabse()
        getDataCount()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.class_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.join_class -> startActivity(Intent(context, CreateClassActivity::class.java))
        }
        return true
    }

    override fun onClickListener(
        className: String,
        classTitle: String,
        classGrade: String,
        key: String
    ) {
        val intent = Intent(context, ClassRoomActivity::class.java)
        intent.putExtra(CLASS_TITLE, classTitle)
        intent.putExtra(CLASS_GRADE, classGrade)
        intent.putExtra(CLASS_ID, key)
        intent.putExtra(CLASS_NAME, className)
        intent.putExtra(TEACHER_ID, mUserId)
        startActivity(intent)
    }

    private fun setupDatabse() {
        val query = FirebaseFirestore.getInstance()
            .collection("teacher")
            .document("classList")
            .collection(mUserId)

        val options = FirestoreRecyclerOptions.Builder<DataClass>()
            .setQuery(query, DataClass::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = FirestoreClassAdapter(options, this)
        adapter.setDataClass(mUserId)
        rv_class_list?.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("teacher")
            .document("classList")
            .collection(mUserId)

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

    private fun prepare(view: View) {
        mUserId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        rv_class_list?.layoutManager = LinearLayoutManager(view.context)
        rv_class_list?.setHasFixedSize(true)
    }
}
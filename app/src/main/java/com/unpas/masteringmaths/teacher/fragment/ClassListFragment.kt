package com.unpas.masteringmaths.teacher.fragment

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
import com.unpas.masteringmaths.teacher.ClassListListener
import com.unpas.masteringmaths.teacher.activity.ClassRoomActivity
import com.unpas.masteringmaths.teacher.activity.CreateClassActivity
import com.unpas.masteringmaths.teacher.adapter.FirestoreClassAdapter
import com.unpas.masteringmaths.teacher.model.DataClass
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_GRADE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE

class ClassListFragment : Fragment(), ClassListListener {

    private lateinit var mUserId: String
    private lateinit var mToolbar: Toolbar
    private lateinit var rvClassList: RecyclerView
    private lateinit var tvNothingClass: TextView

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
        mToolbar = view.findViewById(R.id.toolbar)
        rvClassList = view.findViewById(R.id.rv_class_list)
        tvNothingClass = view.findViewById(R.id.tv_nothing_class)
        (context as AppCompatActivity).setSupportActionBar(mToolbar)
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

    override fun onClickListener(className: String, classTitle: String, classGrade: String, key: String) {
        val intent = Intent(context, ClassRoomActivity::class.java)
        intent.putExtra(CLASS_TITLE, classTitle)
        intent.putExtra(CLASS_GRADE, classGrade)
        intent.putExtra(CLASS_ID, key)
        intent.putExtra(CLASS_NAME, className)
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
        rvClassList.adapter = adapter
    }

    private fun getDataCount() {
        val db = FirebaseFirestore.getInstance()
            .collection("teacher")
            .document("classList")
            .collection(mUserId)

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

    private fun prepare(view: View) {
        mUserId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        rvClassList.layoutManager = LinearLayoutManager(view.context)
        rvClassList.setHasFixedSize(true)
    }
}

package com.unpas.masteringmaths.teacher.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.ClassListListener
import com.unpas.masteringmaths.teacher.model.DataClass
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.class_item.view.*

class FirestoreClassAdapter(options: FirestoreRecyclerOptions<DataClass>, private var listener: ClassListListener) :
    FirestoreRecyclerAdapter<DataClass, FirestoreClassAdapter.ViewHolder>(options) {

    private lateinit var userId: String

    fun setDataClass(userId: String){
        this.userId = userId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: DataClass) {

        val getClassKey = snapshots.getSnapshot(position).id
        val className = item.className.toString()
        val classTitle = item.classTitle.toString()

        holder.apply {
            containerView.tv_class_name.text = item.className.toString()
            containerView.tv_class_title.text = item.classTitle.toString()
            containerView.tv_class_grade.text = item.classGrade.toString()

            val db = FirebaseFirestore.getInstance()
            db.collection("classRooms")
                .document(className)
                .collection(userId)
                .document(getClassKey)
                .collection("members")
                .get()
                .addOnSuccessListener {
                    val memberCount = it.documents.size
                    containerView.tv_student_count.text = "$memberCount Anggota"
                }

            containerView.class_item.setOnClickListener {
                listener.onClickListener(className, classTitle,
                    item.classGrade.toString(), getClassKey)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
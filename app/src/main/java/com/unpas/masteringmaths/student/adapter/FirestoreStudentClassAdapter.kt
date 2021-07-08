package com.unpas.masteringmaths.student.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.StudentClassListener
import com.unpas.masteringmaths.student.model.DataClass
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.class_student_item.view.*

class FirestoreStudentClassAdapter(options: FirestoreRecyclerOptions<DataClass>, private var listener: StudentClassListener) :
    FirestoreRecyclerAdapter<DataClass, FirestoreStudentClassAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_student_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: DataClass) {

        val getClassKey = item.classCode.toString()
        val teacherId = item.teacherId.toString()
        val className = item.className.toString()
        val classTitle = item.classTitle.toString()

        holder.apply {
            containerView.tv_class_name.text = item.className.toString()
            containerView.tv_class_title.text = item.classTitle.toString()
            containerView.tv_teacher_name.text = "Guru: ${item.teacherName.toString()}"

            val db = FirebaseFirestore.getInstance()
            db.collection("classRooms")
                .document(className)
                .collection(teacherId)
                .document(getClassKey)
                .collection("members")
                .get()
                .addOnSuccessListener {
                    val memberCount = it.documents.size
                    containerView.tv_student_count.text = "$memberCount Anggota"
                }

            containerView.class_item.setOnClickListener {
                listener.onClickListener(classTitle, className, teacherId, getClassKey)
            }
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}
package com.unpas.masteringmaths.teacher.presenter

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.main.adapter.FirestoreMemberAdapter.Companion.memberIdList
import com.unpas.masteringmaths.teacher.view.EditClassView

class EditClassPresenter(
    private val context: Context,
    private val view: EditClassView.View
) : EditClassView.Presenter {

    private lateinit var db: FirebaseFirestore

    override fun requestDeleteClass(className: String, userId: String, codeClass: String) {
        var index = 1
        db = FirebaseFirestore.getInstance()
        for (data in memberIdList) {
            db.collection("teacher")
                .document("classList")
                .collection(data)
                .document(codeClass)
                .delete()
                .addOnSuccessListener {
                    if (index >= memberIdList.size) {
                        deleteMyClass(className, codeClass, userId)
                    }
                    index++
                }
                .addOnFailureListener {
                    view.hideProgressBar()
                    view.handleResponse(it.localizedMessage?.toString().toString())
                }
        }
    }

    private fun deleteMyClass(className: String, codeClass: String, userId: String){
        var index = 1
        val db = FirebaseFirestore.getInstance()
        for (data in memberIdList) {
            db.collection("classList")
                .document(className)
                .collection(data)
                .document(codeClass)
                .delete()
                .addOnSuccessListener {
                    if (index >= memberIdList.size) {
                        deleteStudentClass(codeClass, className, userId, codeClass)
                    }
                    index++
                }
                .addOnFailureListener {
                    view.hideProgressBar()
                    view.handleResponse(it.localizedMessage?.toString().toString())
                }
        }
    }

    private fun deleteStudentClass(classCode: String, className: String, userId: String, codeClass: String) {
        var index = 1
        val db = FirebaseFirestore.getInstance()
        for (data in memberIdList) {
            db.collection("students")
                .document(data)
                .collection("classList")
                .document(classCode)
                .delete()
                .addOnSuccessListener {
                    if (index >= memberIdList.size) {
                        deleteDataClass(className, userId, codeClass)
                    }
                    index++
                }
                .addOnFailureListener {
                    view.hideProgressBar()
                    view.handleResponse(it.localizedMessage?.toString().toString())
                }
        }
    }

    private fun deleteDataClass(className: String, userId: String, codeClass: String) {
        var index = 1
        val db = FirebaseFirestore.getInstance()
        for (data in memberIdList) {
            db.collection("classRooms")
                .document(className)
                .collection(userId)
                .document(codeClass)
                .delete()
                .addOnSuccessListener {
                    if (index >= memberIdList.size) {
                        view.onSuccessDeleteData(context.getString(R.string.success_delete_class))
                    }
                    index++
                }
                .addOnFailureListener {
                    view.hideProgressBar()
                    view.handleResponse(it.localizedMessage?.toString().toString())
                }
        }
    }

    override fun requestEditClass(
        className: String, classTitle: String,
        classGrade: String, userId: String, codeClass: String
    ) {
        view.showProgressBar()

        db = FirebaseFirestore.getInstance()
        db.collection("teacher")
            .document("classList")
            .collection(userId)
            .document(codeClass)
            .update(
                "classTitle", classTitle,
                "classGrade", classGrade
            )
            .addOnSuccessListener {
                view.onSuccessEditData(
                    context.getString(R.string.success_edit_class),
                    classTitle,
                    classGrade
                )
            }
            .addOnFailureListener {
                view.hideProgressBar()
                view.handleResponse(it.localizedMessage?.toString().toString())
            }
    }
}
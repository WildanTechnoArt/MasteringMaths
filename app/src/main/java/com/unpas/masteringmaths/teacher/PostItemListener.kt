package com.unpas.masteringmaths.teacher

interface PostItemListener {
    fun onUpdateClick(teacherUid: String, toolbarName: String, isEdited: Boolean,
                      post: String, postId: String, isAssignment: Boolean)
    fun onDeletePost(postId: String)
}
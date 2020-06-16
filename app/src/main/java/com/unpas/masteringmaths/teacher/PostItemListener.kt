package com.unpas.masteringmaths.teacher

interface PostItemListener {
    fun onUpdateClick(teacherId: String, toolbarName:String, isEdited: Boolean, isAssignment: Boolean,
                      post: String, postTitle: String?, postId: String)
    fun onDeletePost(postId: String)
}
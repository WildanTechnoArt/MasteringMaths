package com.unpas.masteringmaths.teacher

interface ClassListListener {
    fun onClickListener(className: String, classTitle: String, classGrade: String, key: String)
}
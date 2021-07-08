package com.unpas.masteringmaths.student

interface StudentClassListener {
    fun onClickListener(classTitle: String, className: String, teacherId: String, key: String)
}
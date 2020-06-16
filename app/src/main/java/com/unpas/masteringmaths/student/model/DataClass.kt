package com.unpas.masteringmaths.student.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataClass (
    var className: String? = null,
    var classTitle: String? = null,
    var classCode: String? = null,
    var teacherName: String? = null,
    var teacherId: String? = null,
    var classGrade: String ? = null,
    var studentCount: Long ? = null
): Parcelable
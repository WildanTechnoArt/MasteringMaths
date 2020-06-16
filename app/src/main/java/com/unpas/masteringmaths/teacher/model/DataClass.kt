package com.unpas.masteringmaths.teacher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataClass (
    var className: String? = null,
    var classTitle: String? = null,
    var teacherName: String? = null,
    var classGrade: String ? = null,
    var studentCount: Int ? = null
): Parcelable
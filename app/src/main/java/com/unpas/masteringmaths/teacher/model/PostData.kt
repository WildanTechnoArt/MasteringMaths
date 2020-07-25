package com.unpas.masteringmaths.teacher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostData (
    var urlPhoto: String? = null,
    var userId: String? = null,
    var username: String ? = null,
    var nip: String ? = null,
    var postType: Int? = null,
    var fileUrl: String? = null,
    var isTeacher: Boolean? = null,
    var postContent: String ? = null
): Parcelable
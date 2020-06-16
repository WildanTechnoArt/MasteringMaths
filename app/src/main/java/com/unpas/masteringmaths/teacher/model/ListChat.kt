package com.unpas.masteringmaths.teacher.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListChat(
    var photoUrl: String? = null,
    var userId: String? = null,
    var username: String? = null,
    var nomorInduk: String? = null
) : Parcelable
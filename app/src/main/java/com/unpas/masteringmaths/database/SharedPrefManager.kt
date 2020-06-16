package com.unpas.masteringmaths.database

import android.annotation.SuppressLint
import android.content.Context

class  SharedPrefManager private constructor(context: Context) {

    init {
        mContext = context
    }

    companion object {

        //Nama File untuk SharedPreferenxe
        private const val TOKEN = "token"
        private const val PROFILE = "profile"

        //Key untuk mengambil Value pada SharedPreference
        private const val USER_ID = "userId"
        private const val USER_NAME = "userName"
        private const val USER_PHOTO = "userPhoto"
        private const val USER_STATUS = "userStatus"

        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(context: Context?): SharedPrefManager {
            if (mInstance == null)
                mInstance = context?.let { SharedPrefManager(it) }
            return mInstance!!
        }
    }

    val getUserId: String?
        get() {
            val preferences = mContext.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
            return preferences.getString(USER_ID, null)
        }

    val getUserPhoto: String?
        get() {
            val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
            return preferences.getString(USER_PHOTO, null)
        }

    fun saveUserPhoto(photoUrl: String): Boolean {
        val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(USER_PHOTO, photoUrl)
        editor.apply()
        return true
    }

    val getUserStatus: String?
        get() {
            val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
            return preferences.getString(USER_STATUS, null)
        }

    fun saveUserStatus(status: String): Boolean {
        val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(USER_STATUS, status)
        editor.apply()
        return true
    }

    fun deleteUserId(): Boolean {
        val preferences = mContext.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        return editor.commit()
    }

    fun saveUserId(userId: String): Boolean {
        val preferences = mContext.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(USER_ID, userId)
        editor.apply()
        return true
    }

    val getUserName: String?
        get() {
            val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
            return preferences.getString(USER_NAME, null)
        }

    fun deleteUserProfile(): Boolean {
        val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        return editor.commit()
    }

    fun saveUserName(userName: String): Boolean {
        val preferences = mContext.getSharedPreferences(PROFILE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
        return true
    }
}
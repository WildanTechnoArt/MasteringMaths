package com.unpas.masteringmaths.teacher.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.presenter.EditProfilePresenter
import com.unpas.masteringmaths.teacher.view.EditProfileView
import kotlinx.android.synthetic.main.activity_edit_profile_teacher.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class EditProfileActivity : AppCompatActivity(), EditProfileView.View {

    private lateinit var mNewNip: String
    private lateinit var mNewUsername: String
    private lateinit var mNewEmail: String
    private lateinit var mNewSchool: String
    private lateinit var mNewCity: String
    private lateinit var mNewPhoneNumber: String

    private lateinit var presenter: EditProfileView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_teacher)

        prepare()

        presenter.requestDataUser()

        swipe_refresh.setOnRefreshListener {
            presenter.requestDataUser()
        }

        btn_save.setOnClickListener {
            mNewNip = input_nip.text.toString()
            mNewUsername = input_name.text.toString()
            mNewEmail = input_email.text.toString()
            mNewSchool = input_address.text.toString()
            mNewCity = input_city.text.toString()
            mNewPhoneNumber = input_phone.text.toString()
            presenter.requestEditProfile(mNewNip, mNewUsername, mNewEmail, mNewSchool, mNewCity, mNewPhoneNumber)
        }
    }

    override fun onSuccessSaveData(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
        SharedPrefManager.getInstance(this).saveUserName(mNewUsername)
        finish()
    }

    override fun showProfileUser(nip: String, name: String, email: String, school: String, city: String, phone: String) {
        input_nip.setText(nip)
        input_name.setText(name)
        input_email.setText(email)
        input_address.setText(school)
        input_city.setText(city)
        input_phone.setText(phone)
    }

    override fun handleResponse(message: String) {
        btn_save.hideProgress(R.string.btn_save)
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        swipe_refresh.isRefreshing = false
    }

    override fun showProgressBar() {
        btn_save.showProgress { progressColor = Color.WHITE }
    }

    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Edit Profil"
        }

        swipe_refresh.isRefreshing = true
        presenter = EditProfilePresenter(this, this)

        bindProgressButton(btn_save)
        btn_save.attachTextChangeAnimator()
    }
}

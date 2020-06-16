package com.unpas.masteringmaths.student.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.presenter.EditProfilePresenter
import com.unpas.masteringmaths.student.view.EditProfileView
import kotlinx.android.synthetic.main.activity_edit_profile_student.*
import kotlinx.android.synthetic.main.activity_edit_profile_student.btn_save
import kotlinx.android.synthetic.main.activity_edit_profile_student.input_city
import kotlinx.android.synthetic.main.activity_edit_profile_student.input_email
import kotlinx.android.synthetic.main.activity_edit_profile_student.input_name
import kotlinx.android.synthetic.main.activity_edit_profile_student.input_nisn
import kotlinx.android.synthetic.main.activity_edit_profile_student.input_phone
import kotlinx.android.synthetic.main.activity_edit_profile_student.swipe_refresh

class EditProfileStudentActivity : AppCompatActivity(), EditProfileView.View {

    private lateinit var mNewNisn: String
    private lateinit var mNewUsername: String
    private lateinit var mNewEmail: String
    private lateinit var mNewCity: String
    private lateinit var mNewPhoneNumber: String

    private lateinit var presenter: EditProfileView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_student)

        prepare()

        presenter.requestDataUser()

        swipe_refresh.setOnRefreshListener {
            presenter.requestDataUser()
        }

        btn_save.setOnClickListener {
            mNewNisn = input_nisn.text.toString()
            mNewUsername = input_name.text.toString()
            mNewEmail = input_email.text.toString()
            mNewCity = input_city.text.toString()
            mNewPhoneNumber = input_phone.text.toString()
            presenter.requestEditProfile(mNewNisn, mNewUsername, mNewEmail, mNewCity, mNewPhoneNumber)
        }
    }

    override fun onSuccessSaveData(message: String) {
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    override fun showProfileUser(nisn: String, name: String, email: String, city: String, phone: String) {
        input_nisn.setText(nisn)
        input_name.setText(name)
        input_email.setText(email)
        input_city.setText(city)
        input_phone.setText(phone)
    }

    override fun handleResponse(message: String) {
        btn_save.hideProgress(R.string.btn_simpan)
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
        }

        swipe_refresh.isRefreshing = true
        presenter = EditProfilePresenter(this, this)

        bindProgressButton(btn_save)
        btn_save.attachTextChangeAnimator()
    }
}

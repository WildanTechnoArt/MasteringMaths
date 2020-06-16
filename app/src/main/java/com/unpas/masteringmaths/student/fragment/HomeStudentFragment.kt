package com.unpas.masteringmaths.student.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.StudentLessonActivity
import com.unpas.masteringmaths.student.presenter.HomeFragmentPresenter
import com.unpas.masteringmaths.student.view.HomeFragmentView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.NUMBER_LESSON
import kotlinx.android.synthetic.main.fragment_student_home.*
import kotlinx.android.synthetic.main.junior_lesson_layout.*
import kotlinx.android.synthetic.main.junior_lesson_layout.view.*
import kotlinx.android.synthetic.main.senior_lesson_layout.*

class HomeStudentFragment : Fragment(), HomeFragmentView.View {

    private lateinit var presenter: HomeFragmentView.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepare(view)

        presenter.requestDatabase()
        presenter.getPhotoFromStorage()
        swipe_refresh.isEnabled = false

        getClassCount(view)

        sp_level_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            @SuppressLint("InflateParams")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (parent?.getItemAtPosition(position)) {
                    "SMP" -> {
                        sp_class_list.adapter = view?.context?.let {
                            ArrayAdapter.createFromResource(
                                it, R.array.junior_high_school,
                                android.R.layout.simple_spinner_dropdown_item
                            )
                        }
                        juniorLesson()
                    }
                    "SMA" -> {
                        sp_class_list.adapter = view?.context?.let {
                            ArrayAdapter.createFromResource(
                                it, R.array.senior_high_school,
                                android.R.layout.simple_spinner_dropdown_item
                            )
                        }
                        seniorLesson()
                    }
                }
            }

        }
    }

    override fun onSuccess(name: String, nisn: String) {
        tv_name?.text = name
        tv_nisn?.text = nisn
    }

    @SuppressLint("SetTextI18n")
    private fun getClassCount(view: View) {
        val userId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        val db = FirebaseFirestore.getInstance()
            .collection("students")
            .document(userId)
            .collection("classList")
            .get()

        db.addOnSuccessListener {
            val classCount = it.documents.size
            view.tv_class1_count?.text = "$classCount Kelas Yang Diikuti"
        }
    }

    // Mata Pelajaran Untuk SMP
    @SuppressLint("InflateParams")
    private fun juniorLesson() {
        senior_lesson?.visibility = View.GONE
        junior_lesson?.visibility = View.VISIBLE

        card_relasi_fungsi.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_relasi_fungsi))
            intent.putExtra(NUMBER_LESSON, 0)
            startActivity(intent)
        }

        card_phytagoras.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_teorema_pyithagoras))
            intent.putExtra(NUMBER_LESSON, 1)
            startActivity(intent)
        }

        card_pola_bilangan.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_pola_bilangan))
            intent.putExtra(NUMBER_LESSON, 2)
            startActivity(intent)
        }

        card_persamaan.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_persamaan_garis))
            intent.putExtra(NUMBER_LESSON, 3)
            startActivity(intent)
        }

        card_koordinat.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_koordinat_cartesius))
            intent.putExtra(NUMBER_LESSON, 4)
            startActivity(intent)
        }

        card_spldv.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_spldv))
            intent.putExtra(NUMBER_LESSON, 5)
            startActivity(intent)
        }
    }

    // Mata Pelajaran Untuk SMA
    @SuppressLint("InflateParams")
    private fun seniorLesson() {
        junior_lesson?.visibility = View.GONE
        senior_lesson?.visibility = View.VISIBLE

        card_matrik_pencacahan.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_relasi_fungsi))
            intent.putExtra(NUMBER_LESSON, 0)
            startActivity(intent)
        }
    }

    override fun handleResponse(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        swipe_refresh.isRefreshing = false
    }

    override fun showProgressBar() {
        swipe_refresh.isRefreshing = true
    }

    override fun showPhotoProfile(photoUrl: String) {
        SharedPrefManager.getInstance(context).saveUserPhoto(photoUrl)
        activity?.let {
            GlideApp.with(it)
                .load(photoUrl)
                .placeholder(R.drawable.profile_placeholder)
                .into(img_profile)
        }
    }

    private fun prepare(view: View) {
        presenter = HomeFragmentPresenter(view.context, this)
    }
}
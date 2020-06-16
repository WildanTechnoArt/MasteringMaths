package com.unpas.masteringmaths.teacher.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.student.activity.StudentLessonActivity
import com.unpas.masteringmaths.teacher.presenter.HomeFragmentPresenter
import com.unpas.masteringmaths.teacher.view.HomeFragmentView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.NUMBER_LESSON
import kotlinx.android.synthetic.main.dashboard_main_menu.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeFragmentView.View {

    private lateinit var userId: String
    private lateinit var presenter: HomeFragmentView.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepare(view)
        lessonList()

        presenter.requestDatabase()
        presenter.getPhotoFromStorage()

        swipe_refresh.isEnabled = false
        swipe_refresh.setOnRefreshListener {
            presenter.requestDatabase()
        }

        getClass1Count()
        getClass2Count()
        getClass3Count()
        getClass4Count()
        getClass5Count()
        getClass6Count()
    }

    @SuppressLint("SetTextI18n")
    private fun getClass1Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.class_relasi_fungsi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class1_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClass2Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.teorema_pythagoras))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class2_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClass3Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.pola_bilangan))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class3_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClass4Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.persamaan_garis_lurus))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class4_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClass5Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.koordinat_cartesius))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class5_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClass6Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.spldv))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class6_count.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(name: String, teacherId: String) {
        tv_teacher_name.text = name
        tv_teacher_nip.text = "NIP: $teacherId"
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

    @SuppressLint("SetTextI18n")
    private fun prepare(view: View) {
        userId = SharedPrefManager.getInstance(view.context).getUserId.toString()
        presenter = HomeFragmentPresenter(view.context, this)

        val gradeAdapter = ArrayAdapter(
            view.context, R.layout.support_simple_spinner_dropdown_item,
            view.context.resources.getStringArray(R.array.grade_level_list)
        )
        (add_grade as? AutoCompleteTextView)?.setAdapter(gradeAdapter)

        add_grade.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    val classList = arrayOf("Kelas VIII")
                    val builder = MaterialAlertDialogBuilder(view.context)
                        .setTitle("Pilih Kelas")
                        .setItems(
                            classList
                        ) { _, id ->
                            add_grade.setText("${gradeAdapter.getItem(id)} ${classList[id]}")
                        }

                    val dialog = builder.create()
                    dialog.show()
                }

                1 -> {
                    val classList = arrayOf("Kelas X")
                    val builder = MaterialAlertDialogBuilder(view.context)
                        .setTitle("Pilih Kelas")
                        .setItems(
                            classList
                        ) { _, id ->
                            add_grade.setText("${gradeAdapter.getItem(id)} ${classList[id]}")
                        }
                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    private fun lessonList() {
        card_relasi_fungsi.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_relasi_fungsi))
            intent.putExtra(NUMBER_LESSON, 0)
            startActivity(intent)
        }

        card_teorema_pythagoras.setOnClickListener {
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

        card_persamaan_garis_lurus.setOnClickListener {
            val intent = Intent(context, StudentLessonActivity::class.java)
            intent.putExtra(CLASS_NAME, getString(R.string.class_persamaan_garis))
            intent.putExtra(NUMBER_LESSON, 3)
            startActivity(intent)
        }

        card_koordinat_cartesius.setOnClickListener {
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
}

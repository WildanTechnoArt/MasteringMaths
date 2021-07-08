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
import com.google.firebase.firestore.FirebaseFirestore
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.main.GlideApp
import com.unpas.masteringmaths.main.activity.JuniorLessonActivity
import com.unpas.masteringmaths.main.activity.SeniorLessonActivity
import com.unpas.masteringmaths.teacher.presenter.HomeFragmentPresenter
import com.unpas.masteringmaths.teacher.view.HomeFragmentView
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_NAME
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.NUMBER_LESSON
import kotlinx.android.synthetic.main.fragment_teacher_home.*
import kotlinx.android.synthetic.main.fragment_teacher_home.img_profile
import kotlinx.android.synthetic.main.fragment_teacher_home.junior_lesson
import kotlinx.android.synthetic.main.fragment_teacher_home.senior_lesson
import kotlinx.android.synthetic.main.fragment_teacher_home.swipe_refresh
import kotlinx.android.synthetic.main.junior_lesson_layout.*
import kotlinx.android.synthetic.main.senior_lesson_layout.*

class TeacherHomeFragment : Fragment(), HomeFragmentView.View {

    private lateinit var userId: String
    private lateinit var presenter: HomeFragmentView.Presenter
    private lateinit var gradeAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepare(view)

        presenter.requestDatabase()
        presenter.getPhotoFromStorage()

        swipe_refresh?.isEnabled = false
        swipe_refresh?.setOnRefreshListener {
            presenter.requestDatabase()
        }

        getClass1Count()
        getClass2Count()
        getClass3Count()
        getClass4Count()
        getClass5Count()
        getClass6Count()
        getClassMatriksCount()
        getClassPencacahanCount()
        getClassPeluangCount()
        getClassBarisDeretCount()
        getClassLimitFungsiCount()
        getClassTurunanFungsiCount()
        getClassTigaDimensiCount()
        getClassTrigometriCount()
        getClassTransformasiCount()
    }

    @SuppressLint("SetTextI18n")
    private fun getClass1Count() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.class_relasi_fungsi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_class1_count?.text = "$classCount Kelas"
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
                tv_class2_count?.text = "$classCount Kelas"
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
                tv_class3_count?.text = "$classCount Kelas"
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
                tv_class4_count?.text = "$classCount Kelas"
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
                tv_class5_count?.text = "$classCount Kelas"
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
                tv_class6_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassMatriksCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.matriks))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_matriks_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassPencacahanCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.kaidah_pencacahan))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_pencacahan_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassPeluangCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.peluang))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_peluang_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassBarisDeretCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.baris_dan_deret))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_baris_dan_deret?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassLimitFungsiCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.limit_fungsi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_limit_fungsi_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassTurunanFungsiCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.turunan_fungsi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_turunan_fungsi_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassTigaDimensiCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.tiga_dimensi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_tiga_dimensi_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassTrigometriCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.trigonometri))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_trigonometri_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    private fun getClassTransformasiCount() {
        val db = FirebaseFirestore.getInstance()
        db.collection("classList")
            .document(getString(R.string.transformasi))
            .collection(userId)
            .addSnapshotListener { snapshot, _ ->
                val classCount = snapshot?.documents?.size
                tv_transformasi_count?.text = "$classCount Kelas"
            }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccess(name: String, teacherId: String) {
        tv_teacher_name?.text = name
        tv_teacher_nip?.text = "NIP: $teacherId"
    }

    override fun handleResponse(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun hideProgressBar() {
        swipe_refresh?.isRefreshing = false
    }

    override fun showProgressBar() {
        swipe_refresh?.isRefreshing = true
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
        add_grade?.setText("SMP Kelas VIII")
        juniorLesson()

        setGradeList(view)

        add_grade.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    juniorLesson()
                }

                1 -> {
                    seniorLesson()
                }
            }
        }
    }

    // Mata Pelajaran Untuk SMP
    private fun juniorLesson() {
        senior_lesson?.visibility = View.GONE
        junior_lesson?.visibility = View.VISIBLE

        card_relasi_fungsi.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_relasi_fungsi), 0)
        }
        card_teorema_pythagoras.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_teorema_pyithagoras), 1)
        }
        card_pola_bilangan.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_pola_bilangan), 2)
        }
        card_persamaan_garis_lurus.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_persamaan_garis), 3)
        }
        card_koordinat_cartesius.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_koordinat_cartesius), 4)
        }
        card_spldv.setOnClickListener {
            toJuniorLessonActivity(getString(R.string.class_spldv), 5)
        }
    }

    private fun toJuniorLessonActivity(className: String, number: Int) {
        val intent = Intent(context, JuniorLessonActivity::class.java)
        intent.putExtra(CLASS_NAME, className)
        intent.putExtra(NUMBER_LESSON, number)
        startActivity(intent)
    }

    // Mata Pelajaran Untuk SMA
    private fun seniorLesson() {
        junior_lesson?.visibility = View.GONE
        senior_lesson?.visibility = View.VISIBLE

        card_kaidah_pencacahan.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_matriks.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 1)
        }
        card_peluang.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_baris_dan_deret.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_limit_fungsi.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_turunan_fungsi.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_tiga_dimensi.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_trigonometri.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
        card_transformasi.setOnClickListener {
            toSeniorActivity(getString(R.string.kaidah_pencacahan), 0)
        }
    }

    private fun toSeniorActivity(className: String, number: Int){
        val intent = Intent(context, SeniorLessonActivity::class.java)
        intent.putExtra(CLASS_NAME, className)
        intent.putExtra(NUMBER_LESSON,  number)
        startActivity(intent)
    }

    private fun setGradeList(view: View) {
        gradeAdapter = ArrayAdapter(
            view.context, R.layout.support_simple_spinner_dropdown_item,
            view.context.resources.getStringArray(R.array.grade_level_list)
        )

        (add_grade as? AutoCompleteTextView)?.setAdapter(gradeAdapter)
    }
}
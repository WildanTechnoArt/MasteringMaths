package com.unpas.masteringmaths.teacher.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.database.SharedPrefManager
import com.unpas.masteringmaths.teacher.presenter.EditClassPresenter
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_GRADE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_ID
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLASS_TITLE
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.CLOSE_ACTIVITY
import com.unpas.masteringmaths.teacher.view.EditClassView
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_setting_class.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class SettingClassActivity : AppCompatActivity(), EditClassView.View {

    private lateinit var classId: String
    private lateinit var classTitle: String
    private lateinit var className: String
    private lateinit var newclassTitle: String
    private lateinit var classGrade: String
    private lateinit var userId: String
    private var isEdited = false

    private lateinit var gradeAdapter: ArrayAdapter<String>

    private lateinit var presenter: EditClassView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_class)
        prepare()
        getCodeClass()
        btnClickListener()
    }

    @SuppressLint("SetTextI18n")
    private fun prepare() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Pengaturan Kelas"
        }

        className = intent?.getStringExtra(UtilsConstant.CLASS_NAME).toString()

        presenter = EditClassPresenter(this, this)

        bindProgressButton(btn_edit_class)
        btn_edit_class.attachTextChangeAnimator()

        bindProgressButton(btn_delete_class)
        btn_delete_class.attachTextChangeAnimator()
    }

    override fun onSuccessEditData(message: String, className: String, classGrade: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        btn_edit_class.hideProgress(getString(R.string.edit_kelas))
        btn_edit_class.text = getString(R.string.edit_kelas)
        dissableView()
        input_class_name.setText(className)
        add_grade.setText(classGrade)
        isEdited = false
        finish()
    }

    override fun onSuccessDeleteData(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ClassRoomActivity::class.java)
        intent.putExtra(CLOSE_ACTIVITY, true)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun handleResponse(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        btn_edit_class.hideProgress(getString(R.string.btn_save))
        btn_delete_class.hideProgress(getString(R.string.hapus_kelas))
    }

    override fun hideProgressBar() {
        btn_edit_class.hideProgress(getString(R.string.btn_save))
        btn_delete_class.hideProgress(getString(R.string.hapus_kelas))
    }

    override fun showProgressBar() {
        btn_edit_class.showProgress { progressColor = Color.WHITE }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun btnClickListener() {
        btn_copy_id.setOnClickListener {
            copyToClipBoard(userId)
            Toast.makeText(this, "User ID berhasil disalin", Toast.LENGTH_SHORT).show()
        }

        btn_copy_class.setOnClickListener {
            copyToClipBoard(classId)
            Toast.makeText(this, "Kode Kelas berhasil disalin", Toast.LENGTH_SHORT).show()
        }

        btn_edit_class.setOnClickListener {
            if (isEdited) {
                newclassTitle = input_class_name.text.toString()
                presenter.requestEditClass(
                    className,
                    newclassTitle,
                    add_grade.text.toString(),
                    userId,
                    classId
                )
            } else {
                isEdited = true
                btn_edit_class.text = getString(R.string.btn_save)
                enableView()

                setGradeList()

                add_grade.setOnItemClickListener { _, _, position, _ ->
                    when (position) {
                        0 -> {
                            val classList = arrayOf("Kelas VIII")
                            val builder = MaterialAlertDialogBuilder(this)
                                .setTitle("Pilih Kelas")
                                .setItems(
                                    classList
                                ) { _, id ->
                                    add_grade.setText("${gradeAdapter.getItem(id)} ${classList[id]}")
                                    setGradeList()
                                }

                            val dialog = builder.create()
                            dialog.setCancelable(false)
                            dialog.show()
                        }

                        1 -> {
                            val classList = arrayOf("Kelas X")
                            val builder = MaterialAlertDialogBuilder(this)
                                .setTitle("Pilih Kelas")
                                .setItems(
                                    classList
                                ) { _, id ->
                                    add_grade.setText("${gradeAdapter.getItem(id + 1)} ${classList[id]}")
                                    setGradeList()
                                }
                            val dialog = builder.create()
                            dialog.setCancelable(false)
                            dialog.show()
                        }
                    }
                }
            }
        }

        btn_delete_class.setOnClickListener {
            val alert = AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Anda yakin ingin menghapusnya?")
                .setPositiveButton("YA") { _, _ ->
                    btn_delete_class.showProgress { progressColor = Color.WHITE }
                    presenter.requestDeleteClass(className, userId, classId)
                }
                .setNegativeButton("TIDAK") { dialog, _ ->
                    dialog.dismiss()
                }
            alert.create()
            alert.show()
        }
    }

    private fun copyToClipBoard(content: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copy Code", content)
        clipboard.setPrimaryClip(clip)
    }

    private fun enableView() {
        class_name_layout.isCounterEnabled = true
        input_class_name.isEnabled = true
        input_class_name.isFocusable = true
        input_class_name.isFocusableInTouchMode = true

        grade_layout.isCounterEnabled = true
        add_grade.isEnabled = true
        add_grade.isFocusable = true
        add_grade.isFocusableInTouchMode = true
    }

    private fun dissableView() {
        class_name_layout.isCounterEnabled = false
        input_class_name.isEnabled = false
        input_class_name.isFocusable = false
        input_class_name.isFocusableInTouchMode = false

        grade_layout.isCounterEnabled = false
        add_grade.isEnabled = false
        add_grade.isFocusable = false
        add_grade.isFocusableInTouchMode = false
    }

    private fun getCodeClass() {
        userId = SharedPrefManager.getInstance(this).getUserId.toString()
        classTitle = intent?.getStringExtra(CLASS_TITLE).toString()
        classGrade = intent?.getStringExtra(CLASS_GRADE).toString()
        classId = intent?.getStringExtra(CLASS_ID).toString()
        tv_user_id.text = userId
        tv_class_code.text = classId
        input_class_name.setText(classTitle)
        add_grade.setText(classGrade)
    }

    private fun setGradeList() {
        gradeAdapter = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.grade_level_list)
        )

        (add_grade as? AutoCompleteTextView)?.setAdapter(gradeAdapter)
    }
}
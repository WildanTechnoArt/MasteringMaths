package com.unpas.masteringmaths.student.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxn.OnBubbleClickListener
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.fragment.ClassFragment
import com.unpas.masteringmaths.student.fragment.HomeStudentFragment
import com.unpas.masteringmaths.student.fragment.StudentChatFragment
import com.unpas.masteringmaths.student.fragment.StudentProfileFragment
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_teacher_dashboard.*
import java.lang.Exception

class StudentDashboardActivity : AppCompatActivity() {

    private var lastBackPressTime: Long = 0
    private var pageContent: Fragment? = HomeStudentFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_student_dashboard)
            init(savedInstanceState)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            bottom_navigation.addBubbleListener(object : OnBubbleClickListener {
                override fun onBubbleClick(id: Int) {
                    when (id) {
                        R.id.menu_home -> pageContent = HomeStudentFragment()
                        R.id.menu_chat -> pageContent = StudentChatFragment()
                        R.id.menu_class -> pageContent = ClassFragment()
                        R.id.menu_profile -> pageContent = StudentProfileFragment()
                    }

                    pageContent?.let {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, it)
                            .commit()
                    }
                }
            })
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        pageContent?.let {
            supportFragmentManager.putFragment(
                outState,
                UtilsConstant.KEY_FRAGMENT, it
            )
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onBackPressed() {
        var toast: Toast? = null
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toast = Toast.makeText(
                this,
                "Tekan tombol kembali lagi untuk keluar",
                Toast.LENGTH_SHORT
            )
            toast.show()
            this.lastBackPressTime = System.currentTimeMillis()
        } else {
            toast?.cancel()
            super.onBackPressed()
        }
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
            }
        } else {
            pageContent = supportFragmentManager.getFragment(
                savedInstanceState,
                UtilsConstant.KEY_FRAGMENT
            )
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
            }
        }
    }
}
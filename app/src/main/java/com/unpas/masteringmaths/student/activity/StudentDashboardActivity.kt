package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxn.OnBubbleClickListener
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.fragment.ClassFragment
import com.unpas.masteringmaths.student.fragment.HomeStudentFragment
import com.unpas.masteringmaths.student.fragment.StudentChatFragment
import com.unpas.masteringmaths.student.fragment.StudentProfileFragment
import com.unpas.masteringmaths.teacher.fragment.TeacherHomeFragment
import com.unpas.masteringmaths.utils.UtilsConstant
import kotlinx.android.synthetic.main.activity_teacher_dashboard.*

class StudentDashboardActivity : AppCompatActivity() {

    private var pageContent: Fragment? = TeacherHomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
        init(savedInstanceState)
        bottom_navigation.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.home -> pageContent = HomeStudentFragment()
                    R.id.chat -> pageContent = StudentChatFragment()
                    R.id.clas -> pageContent = ClassFragment()
                    R.id.profil -> pageContent = StudentProfileFragment()
                }

                pageContent?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, it)
                        .commit()
                }
            }
        })
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
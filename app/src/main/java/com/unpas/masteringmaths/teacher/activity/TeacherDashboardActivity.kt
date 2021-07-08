package com.unpas.masteringmaths.teacher.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fxn.OnBubbleClickListener
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.fragment.ChatFragment
import com.unpas.masteringmaths.teacher.fragment.ClassListFragment
import com.unpas.masteringmaths.teacher.fragment.TeacherHomeFragment
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.KEY_FRAGMENT
import kotlinx.android.synthetic.main.activity_teacher_dashboard.*

class TeacherDashboardActivity : AppCompatActivity() {

    private var lastBackPressTime: Long = 0
    private var pageContent: Fragment? = TeacherHomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_teacher_dashboard)
            init(savedInstanceState)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            bottom_navigation.addBubbleListener(object : OnBubbleClickListener {
                override fun onBubbleClick(id: Int) {
                    when (id) {
                        R.id.menu_home -> pageContent = TeacherHomeFragment()
                        R.id.menu_chat -> pageContent = ChatFragment()
                        R.id.menu_class -> pageContent = ClassListFragment()
                        R.id.menu_profile -> pageContent = TeacherProfileFragment()
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
        pageContent?.let { supportFragmentManager.putFragment(outState, KEY_FRAGMENT, it) }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onBackPressed() {
        var toast: Toast? = null
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toast = Toast.makeText(
                this@TeacherDashboardActivity,
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
            pageContent = supportFragmentManager.getFragment(savedInstanceState, KEY_FRAGMENT)
            pageContent?.let {
                supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
            }
        }
    }
}
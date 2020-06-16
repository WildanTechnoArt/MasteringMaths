package com.unpas.masteringmaths.teacher.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.teacher.fragment.ChatFragment
import com.unpas.masteringmaths.teacher.fragment.ClassListFragment
import com.unpas.masteringmaths.teacher.fragment.HomeFragment
import com.unpas.masteringmaths.teacher.fragment.TeacherProfileFragment
import com.unpas.masteringmaths.utils.UtilsConstant.Companion.KEY_FRAGMENT
import kotlinx.android.synthetic.main.activity_dashboard.*

class TeacherDashboardActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var lastBackPressTime: Long = 0
    private var pageContent: Fragment? = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        pageContent?.let { supportFragmentManager.putFragment(outState, KEY_FRAGMENT, it) }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> pageContent = HomeFragment()
            R.id.chat -> pageContent = ChatFragment()
            R.id.clas -> pageContent = ClassListFragment()
            R.id.profile -> pageContent = TeacherProfileFragment()
        }

        pageContent?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, it)
                .commit()
        }
        return true
    }

    override fun onBackPressed() {
        var toast: Toast? = null
        if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
            toast = Toast.makeText(this@TeacherDashboardActivity, "Tekan tombol kembali lagi untuk keluar", Toast.LENGTH_SHORT)
            toast.show()
            this.lastBackPressTime = System.currentTimeMillis()
        } else {
            toast?.cancel()
            super.onBackPressed()
        }
    }

    private fun init(savedInstanceState: Bundle?) {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
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
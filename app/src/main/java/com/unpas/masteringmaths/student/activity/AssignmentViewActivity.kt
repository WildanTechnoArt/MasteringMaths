package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.adapter.PagerAdapterAssignment
import kotlinx.android.synthetic.main.activity_assignment_view.*

class AssignmentViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_view)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)

        val pagerAdapterAssignment =
            PagerAdapterAssignment(supportFragmentManager, 1)
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs_assignment))

        tabs_assignment.addTab(tabs_assignment.newTab().setText("Instructions"))
        tabs_assignment.addTab(tabs_assignment.newTab().setText("Submissions"))

        container.adapter = pagerAdapterAssignment

        tabs_assignment.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                container.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
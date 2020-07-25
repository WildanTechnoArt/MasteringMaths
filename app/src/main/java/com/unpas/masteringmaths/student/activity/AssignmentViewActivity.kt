package com.unpas.masteringmaths.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.unpas.masteringmaths.R
import com.unpas.masteringmaths.student.adapter.PagerAdapterAssignment
import kotlinx.android.synthetic.main.activity_assignment_view.*

class AssignmentViewActivity : AppCompatActivity() {

    private val tabMenus = arrayListOf("Intruksi", "Pengajuan")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment_view)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)

        val pagerAdapterAssignment = PagerAdapterAssignment(this)

        container.adapter = pagerAdapterAssignment

        TabLayoutMediator(
            tab_layout, container, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabMenus[position]
            }
        )
    }
}
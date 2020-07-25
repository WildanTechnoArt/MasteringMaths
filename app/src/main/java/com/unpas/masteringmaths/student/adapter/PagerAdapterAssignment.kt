package com.unpas.masteringmaths.student.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unpas.masteringmaths.student.fragment.InstructionsFragment
import com.unpas.masteringmaths.student.fragment.SubmissionsFragment

class PagerAdapterAssignment(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    private val page = listOf(InstructionsFragment(), SubmissionsFragment())

    override fun getItemCount(): Int {
        return page.size
    }

    override fun createFragment(position: Int): Fragment {
        return page[position]
    }
}

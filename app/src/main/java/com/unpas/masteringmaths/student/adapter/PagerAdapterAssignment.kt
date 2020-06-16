package com.unpas.masteringmaths.student.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import com.unpas.masteringmaths.student.fragment.InstructionsFragment
import com.unpas.masteringmaths.student.fragment.SubmissionsFragment

class PagerAdapterAssignment(fm: FragmentManager, BEHAVIOUR: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOUR) {

    private val page = listOf(InstructionsFragment(), SubmissionsFragment())

    override fun getItem(position: Int): Fragment {
        return page[position]
    }

    override fun getCount(): Int {
        return page.size
    }
}

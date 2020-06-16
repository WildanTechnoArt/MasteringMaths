package com.unpas.masteringmaths.student.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unpas.masteringmaths.student.fragment.classroom.StudentDiscussionFragment
import com.unpas.masteringmaths.student.fragment.classroom.StudentMembersFragment
import com.unpas.masteringmaths.student.fragment.classroom.StudentPostsFragment

class StudentClassRoomAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private val pages = listOf(
        StudentPostsFragment(), StudentMembersFragment(),
        StudentDiscussionFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
}
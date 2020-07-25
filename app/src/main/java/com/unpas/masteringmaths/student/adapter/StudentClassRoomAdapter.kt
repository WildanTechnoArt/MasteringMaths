package com.unpas.masteringmaths.student.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unpas.masteringmaths.student.fragment.classroom.StudentDiscussionFragment
import com.unpas.masteringmaths.student.fragment.classroom.StudentMembersFragment
import com.unpas.masteringmaths.teacher.fragment.PostsFragment

class StudentClassRoomAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    private val pages = listOf(
        PostsFragment(), StudentMembersFragment(),
        StudentDiscussionFragment()
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}
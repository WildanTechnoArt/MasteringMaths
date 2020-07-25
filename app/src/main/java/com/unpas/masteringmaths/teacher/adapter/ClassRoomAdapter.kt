package com.unpas.masteringmaths.teacher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.unpas.masteringmaths.teacher.fragment.DiscussionFragment
import com.unpas.masteringmaths.teacher.fragment.MembersFragment
import com.unpas.masteringmaths.teacher.fragment.PostsFragment

class ClassRoomAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    private val pages = listOf(
        PostsFragment(), MembersFragment(),
        DiscussionFragment()
    )

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}
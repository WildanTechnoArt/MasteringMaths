package com.unpas.masteringmaths.teacher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unpas.masteringmaths.teacher.fragment.DiscussionFragment
import com.unpas.masteringmaths.teacher.fragment.MembersFragment
import com.unpas.masteringmaths.teacher.fragment.PostsFragment

class ClassRoomAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    private val pages = listOf(PostsFragment(), MembersFragment(),
        DiscussionFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
}
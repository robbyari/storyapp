package com.robbyari.storyapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.robbyari.storyapp.view.maps.MapsFragment
import com.robbyari.storyapp.view.story.StoryFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = StoryFragment()
            1 -> fragment = MapsFragment()
        }
        return fragment as Fragment
    }
}
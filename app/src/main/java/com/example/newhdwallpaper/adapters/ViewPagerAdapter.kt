package com.example.newhdwallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newhdwallpaper.fragments.InnerRvFragment

class ViewPagerAdapter(var list: ArrayList<String>, fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {

return InnerRvFragment.newInstance(list[position])

    }

}
package com.example.newhdwallpaper.ui.popular

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.newhdwallpaper.R
import androidx.lifecycle.Observer
import com.example.newhdwallpaper.adapters.ViewPagerAdapter
import com.example.newhdwallpaper.databinding.FragmentPopularBinding
import com.example.newhdwallpaper.databinding.FragmentSlideshowBinding
import com.example.newhdwallpaper.databinding.RvItemBinding
import com.example.newhdwallpaper.ui.slideshow.SlideshowViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class PopularFragment : Fragment() {


    private lateinit var popularViewModel: PopularViewModel
    private var _binding: FragmentPopularBinding? = null
    lateinit var categoryList: ArrayList<String>
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        popularViewModel =
            ViewModelProvider(this).get(PopularViewModel::class.java)
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        val root: View = binding.root
        loadCategory()
        binding.viewPager.adapter = ViewPagerAdapter(categoryList, requireActivity())
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = loadCategory()[position]
        }.attach()
        setTabs()
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                val tab_item = RvItemBinding.bind(customView!!)

                tab_item.indicator.visibility = View.VISIBLE
                tab_item.tv.setTextColor(Color.parseColor("#FFFFFF"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                val customView = tab!!.customView
                val tab_item = RvItemBinding.bind(customView!!)

                tab_item.indicator.visibility = View.INVISIBLE
                tab_item.tv.setTextColor(Color.parseColor("#747D85"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        popularViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return binding.root
    }
    private fun setTabs() {

        for (i in 0 until binding.tab.tabCount) {

            val tab_item: RvItemBinding =
                RvItemBinding.inflate(LayoutInflater.from(requireContext()))
            tab_item.tv.text = categoryList[i]
            binding.tab.getTabAt(i)?.customView = tab_item.root
            if (i == 0) {
                tab_item.indicator.visibility = View.VISIBLE
                tab_item.tv.setTextColor(Color.parseColor("#FFFFFF"))

            } else {
                tab_item.indicator.visibility = View.INVISIBLE
                tab_item.tv.setTextColor(Color.parseColor("#747D85"))
            }

        }

    }

    private fun loadCategory(): ArrayList<String> {
        categoryList = ArrayList()
        categoryList.add("All")
        categoryList.add("New")
        categoryList.add("Football")
        categoryList.add("BMW")
        categoryList.add("Animals")
        categoryList.add("Technology")
        categoryList.add("Cities")
        categoryList.add("Buildings")
        categoryList.add("Nature")
        categoryList.add("Mercedes")
        categoryList.add("Flowers")
        categoryList.add("Mansions")
        categoryList.add("Art")
        categoryList.add("Cars")
        categoryList.add("Phones")

        return categoryList
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
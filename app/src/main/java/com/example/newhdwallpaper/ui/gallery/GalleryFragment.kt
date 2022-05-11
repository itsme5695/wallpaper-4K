package com.example.newhdwallpaper.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newhdwallpaper.MainActivity
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.adapters.LikedRvAdapter
import com.example.newhdwallpaper.adapters.RvAdapter
import com.example.newhdwallpaper.databinding.FragmentGalleryBinding
import com.example.newhdwallpaper.room.AppDataBase
import com.example.newhdwallpaper.room.entity.WallpaperEntity

class GalleryFragment : Fragment() {
    lateinit var db: AppDataBase
    private var _binding: FragmentGalleryBinding? = null
    lateinit var adapter: LikedRvAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        db = AppDataBase.getInstance(requireContext())

        adapter = LikedRvAdapter(
            db.dao().getAll() as ArrayList<WallpaperEntity>,
            object : LikedRvAdapter.itemOnCLick {
                override fun itemClick(photo: WallpaperEntity, position: Int) {
                    var bundle = Bundle()
                    bundle.putSerializable("param1", photo)
                    findNavController().navigate(R.id.oneImageFragment, bundle)
                }

            })
//        supportActionBar?.setDisplayShowTitleEnabled(true)
//        supportActionBar?.title = "Wallpaper"
        binding.rv.adapter=adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
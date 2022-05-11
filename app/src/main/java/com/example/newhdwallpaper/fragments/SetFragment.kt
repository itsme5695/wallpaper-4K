package com.example.newhdwallpaper.fragments

import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.databinding.FragmentSetBinding
import com.squareup.picasso.Picasso

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SetFragment : Fragment() {
    lateinit var binding: FragmentSetBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetBinding.inflate(inflater, container, false)
        Picasso.get().load(param1).error(R.drawable.holder2).into(binding.image)

        binding.home.setOnClickListener {
            val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
            val wallManager = WallpaperManager.getInstance(context)
            try {
                wallManager.setBitmap(bmpImg, null, true, WallpaperManager.FLAG_SYSTEM)
                findNavController().popBackStack()
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.lock.setOnClickListener   {
            val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
            val wallManager = WallpaperManager.getInstance(context)
            try {
                wallManager.setBitmap(bmpImg, null, true, WallpaperManager.FLAG_LOCK)
                findNavController().popBackStack()
                Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.lockAndHome.setOnClickListener {
            val bmpImg = (binding.image.drawable as BitmapDrawable).bitmap
            val wallManager = WallpaperManager.getInstance(context)
            wallManager.setBitmap(bmpImg, null, true, WallpaperManager.FLAG_LOCK)
            wallManager.setBitmap(bmpImg, null, true, WallpaperManager.FLAG_SYSTEM)
            findNavController().popBackStack()

            Toast.makeText(context, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
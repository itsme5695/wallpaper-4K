package com.example.newhdwallpaper.fragments

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newhdwallpaper.MainActivity
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.databinding.FragmentOneImageBinding
import com.example.newhdwallpaper.room.AppDataBase
import com.example.newhdwallpaper.room.entity.WallpaperEntity
import com.example.newhdwallpaper.utils.NetworkHelper
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.FadingCircle
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OneImageFragment : Fragment() {
    lateinit var db: AppDataBase
    lateinit var binding: FragmentOneImageBinding
    lateinit var networkHelper: NetworkHelper
    lateinit var outputStream: FileOutputStream
    private var param1: WallpaperEntity? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as WallpaperEntity
            param2 = it.getString(ARG_PARAM2)
        }
        (activity as MainActivity).hideBottomNav()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOneImageBinding.inflate(inflater, container, false)

        db = AppDataBase.getInstance(requireContext())
        networkHelper = NetworkHelper(requireContext())
        val all = db.dao().getAll()
        val image = WallpaperEntity(
            id = param1?.id!!,
            webformatURL = param1?.webformatURL.toString(),
            likeable = false
        )
        val progressBar = binding.spinKit as ProgressBar
        val doubleBounce: Sprite = FadingCircle()
        progressBar.indeterminateDrawable = doubleBounce
        binding.spinKit.visibility = View.VISIBLE
        for (i in all) {
            if (i.id == image.id) {
                image.likeable = i.likeable
                break
            } else {
                image.likeable = false
            }
        }

        if (image.likeable) {
            binding.likable.setImageResource(R.drawable.ic_wallpaper_liked)
        }
        Picasso.get().load(param1?.webformatURL).error(R.drawable.holder2).into(binding.image)
        binding.spinKit.visibility = View.INVISIBLE
        binding.setToScreen.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("param1", param1!!.webformatURL)
            findNavController().navigate(R.id.setFragment, bundle)
        }
        binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, param1)
                type = "image/jpeg"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
        binding.like1.setOnClickListener {
            if (image.likeable) {
                image.likeable = false
                db.dao().deleteSelected(image.id)
                binding.likable.setImageResource(R.drawable.ic_like_wallpaper)
            } else {
                image.likeable = true
                db.dao().add(image)
                binding.likable.setImageResource(R.drawable.ic_wallpaper_liked)
            }


        }
        binding.download.setOnClickListener {
            Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val root = Environment.getExternalStorageDirectory().toString()
                        val myDir = File("$root/Download")
                        if (!myDir.exists()) {
                            myDir.mkdirs()
                        }
                        val image1 = binding.image.drawable as BitmapDrawable
                        var bitmap = image1.bitmap
                        var file = File(myDir, image.id.toString() + ".jpg")
                        outputStream = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream.flush()
                        outputStream.close()
                        Toast.makeText(
                            requireContext(),
                            "Successfully downloaded",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        Toast.makeText(
                            requireContext(),
                            "Please grand the permission",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token?.continuePermissionRequest()
                    }
                }).check()


        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).revealBottomNav()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
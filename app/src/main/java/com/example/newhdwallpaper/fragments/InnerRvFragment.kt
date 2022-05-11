package com.example.newhdwallpaper.fragments

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newhdwallpaper.MainActivity
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.adapters.RvAdapter
import com.example.newhdwallpaper.databinding.FragmentInnerRvBinding
import com.example.newhdwallpaper.models.Hit
import com.example.newhdwallpaper.retrofit.ApiClient
import com.example.newhdwallpaper.retrofit.ApiService
import com.example.newhdwallpaper.room.AppDataBase
import com.example.newhdwallpaper.room.entity.WallpaperEntity
import com.example.newhdwallpaper.utils.NetworkHelper
import com.example.newhdwallpaper.viewmodel.PhotoViewModel
import com.example.newhdwallpaper.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InnerRvFragment : Fragment() {
    lateinit var binding: FragmentInnerRvBinding
    private var param1: String? = null
    private var param2: String? = null

    lateinit var photoViewModel: PhotoViewModel
    lateinit var adapter: RvAdapter
    lateinit var db: AppDataBase
    lateinit var networkHelper: NetworkHelper
    var connec: ConnectivityManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        db = AppDataBase.getInstance(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        connec = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        binding = FragmentInnerRvBinding.inflate(inflater, container, false)
        binding.refreshLayout.isRefreshing = true
        networkHelper = NetworkHelper(requireContext())
        setData()
        binding.refreshLayout.isRefreshing = false
        binding.refreshLayout.setOnRefreshListener {
            setData()
            binding.refreshLayout.isRefreshing = false
        }
        return binding.root
    }

    fun setData() {
        if (networkHelper.isNetworkConnected()) {
            photoViewModel =
                ViewModelProviders.of(
                    this,
                    ViewModelFactory(ApiClient.apiService, param1!!)
                )[PhotoViewModel::class.java]
            adapter = RvAdapter(object : RvAdapter.setOnClick {
                override fun itemClicked(photo: Hit, position: Int) {
                    var bundle = Bundle()
                    var wallpaper = WallpaperEntity(photo.id, photo.webformatURL, photo.likable)
                    bundle.putSerializable("param1", wallpaper)

                    findNavController().navigate(R.id.oneImageFragment, bundle)
                }
            })
            lifecycleScope.launch {
                photoViewModel.photos.collectLatest { list ->
                    adapter.submitData(list)
//                            db.dao().add(list)

                }
            }
            binding.rv.adapter = adapter

        } else {
            val alertDialog = AlertDialog.Builder(requireContext()).create()
            alertDialog.apply {
                setTitle("No Internet connection !")
                setCancelable(false)
                setMessage("Please enable internet")
                setButton("Ok") { dialog, which ->
                    if (connec?.getNetworkInfo(0)?.state == NetworkInfo.State.CONNECTED ||
                        connec?.getNetworkInfo(1)?.state == NetworkInfo.State.CONNECTED
                    ) {
                        setData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            alertDialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            InnerRvFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }


}
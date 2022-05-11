package com.example.newhdwallpaper.ui.random

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newhdwallpaper.R
import com.example.newhdwallpaper.adapters.RvAdapter
import com.example.newhdwallpaper.databinding.FragmentRandomBinding
import com.example.newhdwallpaper.fragments.InnerRvFragment
import com.example.newhdwallpaper.models.Hit
import com.example.newhdwallpaper.retrofit.ApiClient
import com.example.newhdwallpaper.room.AppDataBase
import com.example.newhdwallpaper.room.entity.WallpaperEntity
import com.example.newhdwallpaper.utils.NetworkHelper
import com.example.newhdwallpaper.viewmodel.PhotoViewModel
import com.example.newhdwallpaper.viewmodel.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RandomFragment : Fragment() {


    private var param1: String? = null
    private var param2: String? = null
    private lateinit var randomViewModel: RandomViewModel
    private var _binding: FragmentRandomBinding? = null
    lateinit var adapter: RvAdapter
    lateinit var categoryList: ArrayList<String>

    lateinit var photoViewModel: PhotoViewModel
    lateinit var db: AppDataBase
    lateinit var networkHelper: NetworkHelper
    var connec: ConnectivityManager? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        db = AppDataBase.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        randomViewModel = ViewModelProvider(this).get(RandomViewModel::class.java)
        connec = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        binding.randomLayout.isRefreshing = true
        networkHelper = NetworkHelper(requireContext())
        setData()
        binding.randomLayout.isRefreshing = false
        binding.randomLayout.setOnRefreshListener {
            setData()
            binding.randomLayout.isRefreshing = false
        }


        return binding.root
    }

    fun setData() {
        val category_string = loadCategory()
        if (networkHelper.isNetworkConnected()) {
            photoViewModel = ViewModelProviders.of(
                this, ViewModelFactory(ApiClient.apiService, category_string)
            )[PhotoViewModel::class.java]
            adapter = RvAdapter(object : RvAdapter.setOnClick {
                override fun itemClicked(photo: Hit, position: Int) {
                    var bundle = Bundle()
                    var wallpaper = WallpaperEntity(photo.id, photo.webformatURL, photo.likable)
                    bundle.putSerializable("param1", wallpaper)
                    findNavController().navigate(R.id.oneImageFragment, bundle)
                }
            })
            GlobalScope.launch {
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
                    if (connec?.getNetworkInfo(0)?.state == NetworkInfo.State.CONNECTED || connec?.getNetworkInfo(1)?.state == NetworkInfo.State.CONNECTED) {
                        setData()
                    } else {
                        Toast.makeText(
                            requireContext(), "No Internet Connection", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            alertDialog.show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) = InnerRvFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*fun restartFragment(fragmentId: Int) {
        val currentFragment = requireActivity().supportFragmentManager.findFragmentById(fragmentId)!!

        requireActivity().supportFragmentManager.beginTransaction()
            .detach(currentFragment)
            .commit()
        requireActivity().supportFragmentManager.beginTransaction()
            .attach(currentFragment)
            .commit()
    }*/

    private fun loadCategory(): String {
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
        var random = Random.nextInt(1, 13)

        return categoryList[random]
    }
}
package com.example.newhdwallpaper

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newhdwallpaper.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_popular,  R.id.nav_random, R.id.nav_liked, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val container = binding.appBarMain.container
        container.apply {
            homeBottom.setOnClickListener {
                navController.popBackStack()
                binding.appBarMain.title.text = "Home"
                binding.appBarMain.randomBtn.visibility = View.GONE
                navController.navigate(R.id.nav_home)
                hideCircle()
                circleHome.visibility = View.VISIBLE
            }
            popularBottom.setOnClickListener {
                navController.popBackStack()
                binding.appBarMain.title.text = "Popular"
                binding.appBarMain.randomBtn.visibility = View.GONE
                hideCircle()
                circlePopular.visibility = View.VISIBLE
                navController.navigate(R.id.nav_popular)
            }
            randomBottom.setOnClickListener {
                navController.popBackStack()
                binding.appBarMain.title.text = "Random"
                binding.appBarMain.randomBtn.visibility = View.GONE
                navController.navigate(R.id.nav_random)
                hideCircle()
                circleRandom.visibility = View.VISIBLE
            }
            likedBottom.setOnClickListener {
                navController.popBackStack()
                binding.appBarMain.title.text = "Favourite"
                binding.appBarMain.randomBtn.visibility = View.GONE
                navController.navigate(R.id.nav_liked)
                hideCircle()
                circleLike.visibility = View.VISIBLE
            }
        }

        supportActionBar!!.setDisplayShowTitleEnabled(true)
    }

    fun hideBottomNav() {
        binding.appBarMain.container.blurLayout.visibility = View.GONE
    }
    fun revealBottomNav(){
        binding.appBarMain.container.blurLayout.visibility = View.VISIBLE
    }

    fun hideCircle() {
        binding.appBarMain.container.circleHome.visibility = View.GONE
        binding.appBarMain.container.circlePopular.visibility = View.GONE
        binding.appBarMain.container.circleRandom.visibility = View.GONE
        binding.appBarMain.container.circleLike.visibility = View.GONE
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
package com.mindeurfou.golfbook.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.ActivityMainBinding
import com.mindeurfou.golfbook.ui.hillView.HillPosition
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HillActivity {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        bottomNavigation.setupWithNavController(navController)

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.tournamentsFragment, R.id.playersFragment, R.id.coursesFragment, R.id.gamesFragment -> {
                        bottomNavigation.visibility = View.VISIBLE
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                    }
                    R.id.playerDetailsFragment -> {
                        bottomNavigation.visibility = View.VISIBLE
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_TOP_LEFT)
                    }
                    R.id.modifyPlayerFragment -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                        bottomNavigation.visibility = View.GONE
                    }
                    R.id.courseDetailsFragment -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_MEDIUM)
                        bottomNavigation.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                        bottomNavigation.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    fun navigateToStartActivity() {
        val intent = Intent(this, StartActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getAnimatorToHillPosition(hillPosition: HillPosition, startDelay: Long) : ValueAnimator? =
            binding.hillView.getAnimatorToHillPosition(hillPosition, startDelay)

}
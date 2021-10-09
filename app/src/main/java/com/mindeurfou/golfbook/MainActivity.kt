package com.mindeurfou.golfbook

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mindeurfou.golfbook.databinding.ActivityMainBinding
import com.mindeurfou.golfbook.ui.hillView.HillPosition
import com.mindeurfou.golfbook.ui.hillView.HillView
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import com.simform.custombottomnavigation.Model
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<SSCustomBottomNavigation>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)

        setupCustomBottomNavigation(bottomNavigation, navController)

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment -> binding.hillView.animateToHillPosition(HillPosition.POSITION_BOTTOM_LEFT)
                    R.id.connectionFragment -> {
                        bottomNavigation.visibility = View.GONE
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_BOTTOM_RIGHT)
                    }
                    R.id.createPlayerFragment -> {
                        bottomNavigation.visibility = View.GONE
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                    }
                    R.id.tournamentsFragment, R.id.playersFragment, R.id.coursesFragment, R.id.gamesFragment -> {
                        bottomNavigation.visibility = View.VISIBLE
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                    }
                    else -> bottomNavigation.visibility = View.VISIBLE // TODO faire les bails mieux
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun getAnimatorToHillPosition(hillPosition: HillPosition, startDelay: Long = 0) : ValueAnimator? =
            binding.hillView.getAnimatorToHillPosition(hillPosition, startDelay)

    private fun setupCustomBottomNavigation(bottomNavigation: SSCustomBottomNavigation, navController: NavController) {

        val activeIndex = ID_TOURNAMENT

        val menuItems = arrayOf(
            Model(
                icon = R.drawable.ic_player,
                destinationId = R.id.playersFragment,
                id = ID_PLAYER,
                text = R.string.players,
            ),
            Model(
                icon = R.drawable.ic_game,
                destinationId = R.id.gamesFragment,
                id = ID_GAME,
                text = R.string.games,
            ),
            Model(
                icon = R.drawable.ic_tournament,
                destinationId = R.id.tournamentsFragment,
                id = ID_TOURNAMENT,
                text = R.string.tournaments,
            ),
            Model(
                icon = R.drawable.ic_course,
                destinationId = R.id.coursesFragment,
                id = ID_COURSE,
                text = R.string.courses,
            )
        )

        bottomNavigation.apply {
            setMenuItems(menuItems, activeIndex)
            setupWithNavController(navController)
        }
    }

    companion object {
        const val ID_PLAYER     = 0
        const val ID_GAME       = 1
        const val ID_TOURNAMENT = 2
        const val ID_COURSE     = 3
    }

}
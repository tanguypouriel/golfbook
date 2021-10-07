package com.mindeurfou.golfbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.show
import com.simform.custombottomnavigation.Model
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<SSCustomBottomNavigation>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)

        setupCustomBottomNavigation(bottomNavigation, navController)

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment -> {}
                    R.id.connectionFragment, R.id.createPlayerFragment -> bottomNavigation.visibility = View.GONE
                    else -> bottomNavigation.visibility = View.VISIBLE // TODO faire les bails mieux
                }
            }
        }
    }

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
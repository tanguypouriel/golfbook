package com.mindeurfou.golfbook.ui

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.ActivityStartBinding
import com.mindeurfou.golfbook.ui.hillView.HillPosition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : AppCompatActivity(), HillActivity {

    private var _binding: ActivityStartBinding? = null
    private val binding: ActivityStartBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        lifecycleScope.launchWhenResumed {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.splashFragment -> {
                        launch {
                            delay(500)
                            binding.hillView.animateToHillPosition(HillPosition.POSITION_BOTTOM_LEFT)
                        }
                    }
                    R.id.connectionFragment -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_BOTTOM_RIGHT)
                    }
                    R.id.createPlayerFragment -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                    }
                    else -> {
                        binding.hillView.animateToHillPosition(HillPosition.POSITION_FLAT)
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun getAnimatorToHillPosition(hillPosition: HillPosition, startDelay: Long) : ValueAnimator? =
            binding.hillView.getAnimatorToHillPosition(hillPosition, startDelay)

}
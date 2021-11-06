package com.mindeurfou.golfbook.ui.inGame

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindeurfou.golfbook.ui.individualScore.IndividualScoreFragment
import com.mindeurfou.golfbook.ui.scoreCard.ScoreCardFragment

class InGameAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            ScoreCardFragment()
        else
            IndividualScoreFragment()
    }
}
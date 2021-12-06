package com.mindeurfou.golfbook.ui.inGame

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindeurfou.golfbook.ui.scoreInput.ScoreInputFragment
import com.mindeurfou.golfbook.ui.scoreBook.ScoreBookFragment
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalAnimationApi
@ExperimentalSerializationApi
class InGameAdapter(
    fragment: Fragment,
    private val gameId: Int
) : FragmentStateAdapter(fragment){

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            ScoreBookFragment(gameId)
        else
            ScoreInputFragment(gameId)
    }
}
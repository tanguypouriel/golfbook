package com.mindeurfou.golfbook.ui.createPlayer

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.hillView.HillPosition

class AvatarImageClickListener(
    private val context: Context,
    private val container: View,
    private val gridContainer: View? = null,
    private val isMainImage: Boolean = false,
    private val fragment: CreatePlayerFragment,
    private val changeAvatarImage: (() -> Unit)? = null,
) : View.OnClickListener {

    private val animatorSet = AnimatorSet()

    override fun onClick(p0: View?) {
        if (isMainImage && !fragment.backdropShown)
            makeAnimation()
        else if (fragment.backdropShown && !isMainImage) {
            changeAvatarImage?.invoke()
            makeAnimation()
        }
    }

    private fun makeAnimation() {
        fragment.backdropShown = ! fragment.backdropShown
        val animatorList = if (!fragment.backdropShown)  // hiding animations
            listOf(
                AnimatorInflater.loadAnimator(context, R.animator.hide_backdrop_anim)
                    .apply {
                        setTarget(container)
                        startDelay = 200
                           },
                AnimatorInflater.loadAnimator(context, R.animator.alpha_hide_animator).apply { setTarget(gridContainer) },
                fragment.activity.getAnimatorToHillPosition(HillPosition.POSITION_FLAT, 200)
            )
        else  // showing animations
            listOf(
                AnimatorInflater.loadAnimator(context, R.animator.show_backdrop_anim).apply{
                    setTarget(container) },
                fragment.activity.getAnimatorToHillPosition(HillPosition.POSITION_CREATE_PLAYER_HIGH),
                AnimatorInflater.loadAnimator(context, R.animator.alpha_show_animator)
                    .apply {
                        setTarget(gridContainer)
                        startDelay = 800
                           }
            )

        animatorSet.playTogether(animatorList)
        animatorSet.start()
    }
}
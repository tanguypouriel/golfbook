package com.mindeurfou.golfbook.ui.createPlayer

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.mindeurfou.golfbook.R

class AvatarImageClickListener(
    private val context: Context,
    private val container: View,
    private val isMainImage: Boolean = false,
//    private val event : () -> Unit,
    private val fragment: CreatePlayerFragment
) : View.OnClickListener {

    private val animatorSet = AnimatorSet()
    private val height: Int

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
    }

    override fun onClick(p0: View?) {
        if (isMainImage)
            makeAnimation()
        else if (fragment.backdropShown)
            makeAnimation()
    }

    private fun makeAnimation() {
        fragment.backdropShown = ! fragment.backdropShown
        val translateY = -(context.resources.getDimensionPixelSize(R.dimen.avatar_images_reveal))

        val animator = ObjectAnimator.ofFloat(container, "translationY", (if (fragment.backdropShown) translateY else 0).toFloat())
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()

        animatorSet.play(animator)
        animator.start()
    }
}
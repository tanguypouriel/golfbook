package com.mindeurfou.golfbook.ui.createPlayer

import android.animation.AnimatorInflater
import android.content.Context
import android.view.View
import com.mindeurfou.golfbook.R

class AvatarImageClickListener(
    private val context: Context,
    private val container: View,
    private val isMainImage: Boolean = false,
    private val fragment: CreatePlayerFragment,
    private val changeAvatarImage: (() -> Unit)? = null,
) : View.OnClickListener {

    override fun onClick(p0: View?) {
        if (isMainImage)
            makeAnimation()
        else if (fragment.backdropShown) {
            changeAvatarImage?.invoke()
            makeAnimation()
        }
    }

    private fun makeAnimation() {
        fragment.backdropShown = ! fragment.backdropShown
        val animator = if (!fragment.backdropShown)
            AnimatorInflater.loadAnimator(context, R.animator.hide_backdrop_anim)
        else
            AnimatorInflater.loadAnimator(context, R.animator.show_backdrop_anim)
        animator.setTarget(container)
        animator.start()
    }
}
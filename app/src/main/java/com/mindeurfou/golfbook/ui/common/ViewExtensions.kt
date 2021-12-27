package com.mindeurfou.golfbook.utils

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.animation.doOnEnd
import androidx.core.view.drawToBitmap
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.ui.common.CustomEdgeTreatment

fun BottomNavigationView.show() {
    if (visibility == View.VISIBLE) return

    val parent = parent as ViewGroup
    // View needs to be laid out to create a snapshot & know position to animate. If view isn't
    // laid out yet, need to do this manually.
    if (!isLaidOut) {
        measure(
            View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.AT_MOST)
        )
        layout(parent.left, parent.height - measuredHeight, parent.right, parent.height)
    }

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    drawable.setBounds(left, parent.height, right, parent.height + height)
    parent.overlay.add(drawable)
    ValueAnimator.ofInt(parent.height, top).apply {
        startDelay = 100L
        duration = 300L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.linear_out_slow_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
            visibility = View.VISIBLE
        }
        start()
    }
}

fun BottomNavigationView.hide() {
    if (visibility == View.GONE) return

    val drawable = BitmapDrawable(context.resources, drawToBitmap())
    val parent = parent as ViewGroup
    drawable.setBounds(left, top, right, bottom)
    parent.overlay.add(drawable)
    visibility = View.GONE
    ValueAnimator.ofInt(top, parent.height).apply {
        startDelay = 100L
        duration = 200L
        interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
        )
        addUpdateListener {
            val newTop = it.animatedValue as Int
            drawable.setBounds(left, newTop, right, newTop + height)
        }
        doOnEnd {
            parent.overlay.remove(drawable)
        }
        start()
    }
}

fun ProgressBar.show() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    if (visibility != View.GONE)
        visibility = View.GONE
}
fun LinearProgressIndicator.setSmoothProgress(progress: Int) {
    val start = this.progress
    ValueAnimator.ofInt(start, progress).apply {
        duration = 300L
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            this@setSmoothProgress.progress = it.animatedValue as Int
        }

        start()
    }
}

fun Path.scale(width: Int, height: Int) {
    val drawMatrix = Matrix()
    drawMatrix.setScale(width / 100f, height / 100f)
    transform(drawMatrix)
}

fun ImageView.setAvatarResource(avatarId: Int) {
    if (avatarId >= R.drawable.man_1 && avatarId <= R.drawable.woman_8)
        setImageResource(avatarId)
    else
        setImageResource(R.drawable.man_1)
}

fun MaterialCardView.cropTopEdge() {
    shapeAppearanceModel =  ShapeAppearanceModel.Builder()
        .setAllCornerSizes(50f)
        .setTopEdge(CustomEdgeTreatment())
        .build()
}

fun List<View>.reveal(context: Context, startDelay: Long = 0) {
    val set: MutableList<Animator> = mutableListOf()

    this.forEach {
        AnimatorInflater.loadAnimator(context, R.animator.alpha_show_animator).apply {
            setTarget(it)
            this.startDelay = startDelay
            set.add(this)
        }
    }

    val animatorSet = AnimatorSet()
    animatorSet.playTogether(set)
    animatorSet.start()
}

fun makeSnackbar(view: View, errors: List<ErrorMessages>) {
    var message = ""
    errors.forEachIndexed { index, errorMessage ->
        message += if (index == 0)
            "$errorMessage"
        else
            "\n $errorMessage"
    }
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
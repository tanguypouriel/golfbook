package com.mindeurfou.golfbook.utils

import android.animation.ValueAnimator
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
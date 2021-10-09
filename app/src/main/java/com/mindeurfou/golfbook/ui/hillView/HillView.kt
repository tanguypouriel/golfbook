package com.mindeurfou.golfbook.ui.hillView

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.utils.scale

class HillView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val borderPaint: Paint
    private val paint: Paint
    private val valueAnimator : ValueAnimator

    private var firstDraw = true
    private var actualPosition = HillPosition.POSITION_FLAT
    private var newPosition = HillPosition.POSITION_FLAT

    private var progress = 0f

    private val path = Path()
    private val borderPath = Path()

    init {
        isClickable = false

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
        }

        paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
            color = context.getColor(R.color.transparent)
        }
        borderPaint = Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            color = context.getColor(R.color.colorArdoise)
            strokeWidth = 15f
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (firstDraw) {
            path.moveTo(0f, 100f)
            firstDraw = false
        } else {
            path.reset()
            borderPath.reset()
            val points = computeWavePoints(actualPosition, newPosition, progress)
            path.moveTo(points[0].x, points[0].y)
            path.cubicTo(points[1].x, points[1].y, points[2].x, points[2].y, points[3].x, points[3].y)
            borderPath.moveTo(points[0].x, points[0].y)
            borderPath.cubicTo(points[1].x, points[1].y, points[2].x, points[2].y, points[3].x, points[3].y)
        }


        path.lineTo(100f, 100f)
        path.lineTo(0f, 100f)
        path.close()
        path.scale(width, height)
        borderPath.scale(width, height)
        paint.color = context.getColor(newPosition.color)
        canvas.drawPath(path, paint)
        canvas.drawPath(borderPath, borderPaint)
    }

    fun animateToHillPosition(hillPosition: HillPosition) {
        getAnimatorToHillPosition(hillPosition)?.start()
    }

    fun getAnimatorToHillPosition(hillPosition: HillPosition, startDelay: Long = 0) : ValueAnimator? {
        if (hillPosition == actualPosition) return null
        newPosition = hillPosition
        valueAnimator.apply {
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener {
                progress = it.animatedValue as Float
                invalidate()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    this@HillView.actualPosition = hillPosition
                }
            } )
            this.startDelay = startDelay
        }
        return valueAnimator
    }

    private fun computeWavePoints(previousPosition: HillPosition, actualPosition: HillPosition, progress: Float) : List<PointF> {
        val points = actualPosition.list.mapIndexed { index, pointF ->
            PointF(
                previousPosition.list[index].x + (pointF.x - previousPosition.list[index].x) * progress,
                previousPosition.list[index].y + (pointF.y - previousPosition.list[index].y) * progress
            )
        }
        return points
    }

}
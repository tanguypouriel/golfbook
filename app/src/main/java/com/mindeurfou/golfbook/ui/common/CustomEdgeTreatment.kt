package com.mindeurfou.golfbook.ui.common

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import com.mindeurfou.golfbook.utils.dp
import com.mindeurfou.golfbook.utils.px
import kotlin.math.atan
import kotlin.math.sqrt

class CustomEdgeTreatment: EdgeTreatment() {

    private val cradleRoundedCornerRadius: Float = 25.px.toFloat()
    private val radius: Float = 25.px.toFloat()

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {


        val roundedCornerOffset = interpolation * cradleRoundedCornerRadius
        val middle = length - 60.px  //horizontalBias
        val verticalOffset = (1.0f - interpolation) * radius

        val verticalOffsetRatio = verticalOffset / radius
        if (verticalOffsetRatio >= 1.0f) {
            shapePath.lineTo(length, 0.0f)
        } else {

            val distanceBetweenCenters = radius + roundedCornerOffset
            val distanceBetweenCentersSquared = distanceBetweenCenters * distanceBetweenCenters
            val distanceY = verticalOffset + roundedCornerOffset
            val distanceX =
                sqrt((distanceBetweenCentersSquared - distanceY * distanceY).toDouble()).toFloat()

            val leftRoundedCornerCircleX = middle - distanceX
            val rightRoundedCornerCircleX = middle + distanceX
            val cornerRadiusArcLength =
                Math.toDegrees(atan((distanceX / distanceY).toDouble())).toFloat()
            val cutoutArcOffset = 90.0f - cornerRadiusArcLength
            shapePath.lineTo(leftRoundedCornerCircleX - roundedCornerOffset, 0.0f)
            shapePath.addArc(
                leftRoundedCornerCircleX - roundedCornerOffset,
                0.0f,
                leftRoundedCornerCircleX + roundedCornerOffset,
                roundedCornerOffset * 2.0f,
                270.0f,
                cornerRadiusArcLength
            )
            shapePath.addArc(
                middle - radius,
                -radius - verticalOffset,
                middle + radius,
                radius - verticalOffset,
                180.0f - cutoutArcOffset,
                cutoutArcOffset * 2.0f - 180.0f
            )
            shapePath.addArc(
                rightRoundedCornerCircleX - roundedCornerOffset,
                0.0f,
                rightRoundedCornerCircleX + roundedCornerOffset,
                roundedCornerOffset * 2.0f,
                270.0f - cornerRadiusArcLength,
                cornerRadiusArcLength
            )
            shapePath.lineTo(length, 0.0f)
        }
    }
}
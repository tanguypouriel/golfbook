package com.mindeurfou.golfbook.ui.hillView

import android.graphics.PointF
import androidx.annotation.ColorRes
import com.mindeurfou.golfbook.R

enum class HillPosition (
    val list: List<PointF>,
    @ColorRes val color: Int
    ) {
    POSITION_FLAT(
        listOf(PointF(0f, 100f), PointF(33f,100f), PointF(66f,100f), PointF(100f,100f)),
        R.color.colorHill
    ),
    POSITION_TOP_RIGHT(
        listOf(PointF(0f, 50f), PointF(55f, 26.5f), PointF(60f, 49.5f), PointF(100f, 50f)),
        R.color.colorHill
    ),
    POSITION_TOP_LEFT(
        listOf(PointF(0f, 50f), PointF(60f, 49.5f), PointF(55f, 26.5f), PointF(100f, 50f)),
        R.color.colorHill
    ),
    POSITION_BOTTOM_RIGHT(
        listOf(PointF(0f, 90f), PointF(60f, 89.5f), PointF(55f, 76.5f), PointF(100f, 90f)),
        R.color.colorHill
    ),
    POSITION_BOTTOM_LEFT(
        listOf(PointF(0f, 80f), PointF(55f, 56.5f), PointF(60f, 79.5f), PointF(100f, 80f)),
        R.color.colorHill
    ),
    POSITION_CREATE_PLAYER_HIGH(
        listOf(PointF(0f, 30f), PointF(60f, 29.5f), PointF(55f, 10f), PointF(100f, 30f)),
        R.color.colorHill
    ),
}
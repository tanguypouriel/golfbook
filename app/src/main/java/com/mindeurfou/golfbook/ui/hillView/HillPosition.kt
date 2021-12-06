package com.mindeurfou.golfbook.ui.hillView

import android.graphics.PointF
import androidx.annotation.ColorRes
import com.mindeurfou.golfbook.R

enum class HillPosition (
    val list: List<PointF>
    ) {

    POSITION_FLAT(
        listOf(PointF(0f, 100f), PointF(33f,100f), PointF(66f,100f), PointF(100f,100f)),
    ),
    POSITION_TOP_LEFT(
        listOf(PointF(0f, 20f), PointF(40f, 0f), PointF(50f, 50f), PointF(100f, 35f)),
    ),
    POSITION_TOP_RIGHT(
        listOf(PointF(0f, 50f), PointF(60f, 49.5f), PointF(55f, 26.5f), PointF(100f, 50f)),
    ),
    POSITION_BOTTOM_RIGHT(
        listOf(PointF(0f, 90f), PointF(60f, 89.5f), PointF(55f, 76.5f), PointF(100f, 90f)),
    ),
    POSITION_BOTTOM_LEFT(
        listOf(PointF(0f, 80f), PointF(55f, 56.5f), PointF(60f, 79.5f), PointF(100f, 80f)),
    ),
    POSITION_CREATE_PLAYER_HIGH(
        listOf(PointF(0f, 30f), PointF(60f, 29.5f), PointF(55f, 10f), PointF(100f, 30f)),
    ),
    POSITION_MEDIUM(
        listOf(PointF(0f, 43f), PointF(50f, 52.5f), PointF(55f, 35f), PointF(100f, 47f)),
    )
}
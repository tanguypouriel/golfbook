package com.mindeurfou.golfbook.ui

import android.animation.ValueAnimator
import com.mindeurfou.golfbook.ui.hillView.HillPosition

interface HillActivity {
    fun getAnimatorToHillPosition(hillPosition: HillPosition, startDelay: Long = 0): ValueAnimator?
}

package com.mindeurfou.golfbook.ui.common

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment(layoutRes) {


}
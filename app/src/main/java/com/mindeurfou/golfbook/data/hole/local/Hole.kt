package com.mindeurfou.golfbook.data.hole.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Hole(
    val id: Int,
    val holeNumber: Int,
    val par: Int
) : Parcelable

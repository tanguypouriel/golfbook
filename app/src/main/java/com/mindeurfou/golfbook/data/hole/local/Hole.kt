package com.mindeurfou.golfbook.data.hole.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Hole(
    val id: Int,
    val par: Int
) : Parcelable {

    companion object {
        fun computePar(holes: List<Hole>): Int {
            var par = 0
            holes.forEach {
                par += it.par
            }
            return par
        }

        @JvmName("computePar1")
        fun computePar(holes: List<Int>): Int {
            var par = 0
            holes.forEach {
                par += it
            }
            return par
        }
    }
}
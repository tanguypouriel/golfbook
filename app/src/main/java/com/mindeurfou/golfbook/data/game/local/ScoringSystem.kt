package com.mindeurfou.golfbook.data.game.local

import android.content.Context
import com.mindeurfou.golfbook.R

enum class ScoringSystem {
    STROKE_PLAY,
    STABLEFORD,
    MATCH_PLAY;

    companion object {
        fun toList(context: Context) = listOf(context.getString(R.string.strokePlay), context.getString(R.string.stableFord), context.getString(R.string.matchPlay))
    }

    override fun toString(): String {
        return when(name) {
            STROKE_PLAY.name -> "Stroke Play"
            STABLEFORD.name -> "Stableford"
            MATCH_PLAY.name -> "Match Play"
            else -> "type inconnu"
        }
    }
}
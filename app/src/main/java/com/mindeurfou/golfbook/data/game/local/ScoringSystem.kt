package com.mindeurfou.golfbook.data.game.local

import android.content.Context
import com.mindeurfou.golfbook.R

enum class ScoringSystem {
    STROKE_PLAY,
    STABLEFORD,
    MATCH_PLAY;

    override fun toString(): String {
        return when(name) {
            STROKE_PLAY.name -> "Stroke Play"
            STABLEFORD.name -> "Stableford"
            MATCH_PLAY.name -> "Match Play"
            else -> "type inconnu"
        }
    }

    companion object {

        fun toList(context: Context) = listOf(context.getString(R.string.strokePlay), context.getString(R.string.stableFord), context.getString(R.string.matchPlay))

        fun toScoringSystem(string: String) =
            when (string) {
                STROKE_PLAY.toString() -> STROKE_PLAY
                STABLEFORD.toString() -> STABLEFORD
                MATCH_PLAY.toString() -> MATCH_PLAY
                else -> throw IllegalArgumentException()
            }

    }
}
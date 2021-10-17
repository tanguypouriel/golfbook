package com.mindeurfou.golfbook.utils

import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import java.time.LocalDate

object FakeData {

    fun players() = listOf(
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5),
        Player(2, "Romane", "Philbert", "Roro", R.drawable.woman_2),
        Player(3, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4),
        Player(4, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5),
        Player(5, "Romane", "Philbert", "Roro", R.drawable.woman_2),
        Player(6, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4),
    )

    fun player() = Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5)


    fun tournaments() = listOf(
        Tournament(1, "Tournois du cul", GBState.DONE, LocalDate.now()),
        Tournament(1, "Tournois de l'année", GBState.DONE, LocalDate.now()),
        Tournament(1, "Tournois sournois", GBState.DONE, LocalDate.now()),
        Tournament(1, "Tournooooooois", GBState.DONE, LocalDate.now()),
        Tournament(1, "Tournois", GBState.PENDING, LocalDate.now()),
        Tournament(1, "Tournois", GBState.PENDING, LocalDate.now()),
        Tournament(1, "Tournois", GBState.PENDING, LocalDate.now()),
        Tournament(1, "Tournois", GBState.PENDING, LocalDate.now())
    )

}
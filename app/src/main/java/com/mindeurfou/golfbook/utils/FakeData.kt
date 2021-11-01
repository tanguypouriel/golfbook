package com.mindeurfou.golfbook.utils

import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.game.local.Game
import com.mindeurfou.golfbook.data.player.local.Player
import com.mindeurfou.golfbook.data.player.remote.GetPlayersResponse
import com.mindeurfou.golfbook.data.tournament.local.Tournament
import java.time.LocalDate

object FakeData {

    fun getPlayersResponse() = GetPlayersResponse(
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2),
        listOf(
            Player(2, "Romane", "Philbert", "Roro", R.drawable.woman_2),
            Player(3, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4),
            Player(5, "Romane", "Philbert", "Roro", R.drawable.woman_2),
            Player(6, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4),
            Player(2, "Romane", "Philbert", "Roro", R.drawable.woman_2),
            Player(3, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4),
            Player(5, "Romane", "Philbert", "Roro", R.drawable.woman_2),
            Player(6, "Lisa", "Debrincat", "Petrouchka", R.drawable.woman_4)
        )
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

    fun courses() = listOf(
        Course(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 2, LocalDate.now()),
        Course(1, "Parcours du chêne", 18, 72, 3, 2, LocalDate.now()),
    )

    fun courseDetails() = CourseDetails(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now(), listOf())

    fun games() = listOf(
        Game(1, "Parcours du 17/08/21", GBState.DONE, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 )
        ), "Parcours de la belette des sables", LocalDate.now()),
        Game(2, "Parcours du 24/06/21", GBState.PENDING, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours de la peugot 207", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 ),
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 )
        ), "Parcours trop court", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 ),
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 )
        ), "Parcours", LocalDate.now())
    )
}
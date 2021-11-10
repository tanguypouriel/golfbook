package com.mindeurfou.golfbook.utils

import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.game.local.*
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
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 )
        ), "Parcours de la belette des sables", LocalDate.now()),
        Game(2, "Parcours du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours de la peugot 207", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 ),
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 )
        ), "Parcours trop court", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 ),
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 )
        ), "Parcours", LocalDate.now())
    )
    fun pendingGames() = listOf(
        Game(1, "Partie du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_4 )
        ), "Parcours de la belette des sables", LocalDate.now()),
        Game(2, "Partie du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 ),
            Player(1, "Tanguy", "Pouriel", "Frere le boss", R.drawable.man_7 )
        ), "Parcours de la peugot 207", LocalDate.now()))

    fun game() = Game(2, "Partie du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2 ),
            Player(1, "Roro", "Pouriel", "Frere le boss", R.drawable.woman_2 ),
            Player(1, "Lisa", "Pouriel", "Frere le boss", R.drawable.woman_4 )
        ), "Parcours de la peugot 207", LocalDate.now())

    fun emptyScoreBook() = ScoreBook(
        par = listOf(2, 4, 3, 5, 3, 4, 2, 4, 3, 5, 3, 4, 3, 3, 5, 3, 4, 3),
        playerScores = listOf(
            PlayerScore("Tanguy", List(18) { null }, null, ""),
            PlayerScore("Romane", List(18) { null }, null, ""),
        )
    )

    fun scoreBook() = ScoreBook(
        par = listOf(2, 4, 3, 5, 3, 4, 2, 4, 3, 5, 3, 4, 3, 3, 5, 3, 4, 3),
        playerScores = listOf(
            PlayerScore(
                "Tanguy",
                listOf(
                    ScoreDetails(3, ScoreType.BIRDIE,"2"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.BOGEY,"3"),
                    ScoreDetails(4, ScoreType.DOUBLE_BOGEY, "3"),
                    ScoreDetails(4, ScoreType.PAR, "5"),
                    ScoreDetails(4, ScoreType.PAR,"4"),
                    ScoreDetails(4, ScoreType.PAR, "2"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.EAGLE, "4"),
                    ScoreDetails(4, ScoreType.PAR,"2"),
                    ScoreDetails(4, ScoreType.PAR,"4"),
                    ScoreDetails(4, ScoreType.PAR,"4"),
                    ScoreDetails(4, ScoreType.PAR, "2"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.EAGLE, "4"),
                    ScoreDetails(4, ScoreType.PAR,"2"),
                    ScoreDetails(4, ScoreType.PAR,"4")
                ),
                72,
                "-4"
            ),
            PlayerScore(
                "Tanguy",
                listOf(
                    ScoreDetails(3, ScoreType.PAR,"2"),
                    ScoreDetails(4, ScoreType.BIRDIE, "4"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.PAR, "3"),
                    ScoreDetails(4, ScoreType.PAR, "5"),
                    ScoreDetails(4, ScoreType.BOGEY,"4"),
                    ScoreDetails(4, ScoreType.PAR, "2"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.DOUBLE_BOGEY, "4"),
                    ScoreDetails(4, ScoreType.PAR,"2"),
                    ScoreDetails(4, ScoreType.PAR,"4"),
                    ScoreDetails(4, ScoreType.BIRDIE, "4"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.PAR, "3"),
                    ScoreDetails(4, ScoreType.PAR, "5"),
                    ScoreDetails(4, ScoreType.BOGEY,"4"),
                ),
                73,
                "-3"
            )
        )
    )

    fun gameDetails() = GameDetails(
        1,
        "Partie du swag",
        GBState.DONE,
        ScoringSystem.STROKE_PLAY,
        "Parcours du tube",
        1,
        listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5),
            Player(1, "Romane", "Philbert", "Roro", R.drawable.woman_2)
        ),
        scoreBook = scoreBook()
    )
}
package com.mindeurfou.golfbook.utils

import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.GBState
import com.mindeurfou.golfbook.data.course.local.Course
import com.mindeurfou.golfbook.data.course.local.CourseDetails
import com.mindeurfou.golfbook.data.game.local.*
import com.mindeurfou.golfbook.data.hole.local.Hole
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

    fun course() = Course(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now())

    fun courseDetails() = CourseDetails(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now(), listOf(
        Hole(1, 1, 3),
        Hole(2, 2, 4),
        Hole(3, 3, 4),
        Hole(4, 4, 3),
        Hole(5, 5, 3),
        Hole(6, 6, 5),
        Hole(7, 7, 3),
        Hole(8, 8, 3),
        Hole(9, 9, 5),
        Hole(10, 10, 3),
        Hole(11, 11, 3),
        Hole(12, 12, 3),
        Hole(13, 13, 4),
        Hole(14, 14, 3),
        Hole(15, 15, 4),
        Hole(16, 16, 3),
        Hole(17, 17, 5),
        Hole(18, 18, 3)
    ))

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
        playerScores = listOf(
            PlayerScore("Tanguy", List(18) { null }, null, ""),
            PlayerScore("Romane", List(18) { null }, null, ""),
        )
    )

    fun scoreBook() = ScoreBook(
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
                "Romane",
                listOf(
                    ScoreDetails(3, ScoreType.PAR,"2"),
                    ScoreDetails(2, ScoreType.BIRDIE, "4"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.PAR, "3"),
                    ScoreDetails(5, ScoreType.PAR, "5"),
                    ScoreDetails(4, ScoreType.BOGEY,"4"),
                    ScoreDetails(4, ScoreType.PAR, "2"),
                    ScoreDetails(3, ScoreType.BIRDIE,"3"),
                    ScoreDetails(4, ScoreType.DOUBLE_BOGEY, "4"),
                    ScoreDetails(4, ScoreType.PAR,"2"),
                    ScoreDetails(3, ScoreType.PAR,"4"),
                    ScoreDetails(4, ScoreType.BIRDIE, "4"),
                    ScoreDetails(4, ScoreType.PAR, "4"),
                    ScoreDetails(4, ScoreType.BIRDIE,"3"),
                    ScoreDetails(3, ScoreType.PAR, "3"),
                    ScoreDetails(4, ScoreType.PAR, "5"),
                    ScoreDetails(4, ScoreType.BOGEY,"4"),
                ),
                73,
                "-3"
            ),
                    PlayerScore(
                    "Romain",
            listOf(
                ScoreDetails(3, ScoreType.PAR,"2"),
                ScoreDetails(4, ScoreType.BIRDIE, "4"),
                ScoreDetails(5, ScoreType.PAR, "4"),
                ScoreDetails(4, ScoreType.BIRDIE,"3"),
                ScoreDetails(4, ScoreType.PAR, "3"),
                ScoreDetails(4, ScoreType.PAR, "5"),
                ScoreDetails(3, ScoreType.BOGEY,"4"),
                ScoreDetails(4, ScoreType.PAR, "2"),
                ScoreDetails(4, ScoreType.BIRDIE,"3"),
                ScoreDetails(4, ScoreType.PAR, "4"),
                ScoreDetails(4, ScoreType.BOGEY,"2"),
                ScoreDetails(3, ScoreType.PAR,"4"),
                ScoreDetails(4, ScoreType.BIRDIE, "4"),
                ScoreDetails(4, ScoreType.PAR, "4"),
                ScoreDetails(4, ScoreType.PAR,"3"),
                ScoreDetails(5, ScoreType.PAR, "3"),
                ScoreDetails(4, ScoreType.PAR, "5"),
                ScoreDetails(4, ScoreType.BIRDIE,"4"),
            ),
            73,
            "-3"
        )
    )
    )

    fun gameDetails(state: GBState? = null, playersReady: List<String>? = null) = GameDetails(
        1,
        "Partie du swag",
        state ?: GBState.DONE,
        LocalDate.now(),
        ScoringSystem.STROKE_PLAY,
        "Parcours du tube",
        listOf(2, 4, 3, 5, 3, 4, 2, 4, 3, 5, 3, 4, 3, 3, 5, 3, 4, 3),
        listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5),
            Player(1, "Romane", "Philbert", "Roro", R.drawable.woman_2),
            Player(1, "Romsko", "Prasil", "Bobby", R.drawable.man_8)
        ),
        playersReady ?: listOf("Roro", "MindeurFou"),
        scoreSummaries = listOf(
            ScoreSummary("1.", "MindeurFou", "2 up"),
            ScoreSummary("T2.", "Roro", "1 up"),
            ScoreSummary("T2.", "Boby", "1 up"),
        ),
        scoreBook = scoreBook()
    )
}
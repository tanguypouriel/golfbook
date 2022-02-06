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
        Player(1, "Jérome", "Purin", "Jérôme P.", 3, true),
        listOf(
            Player(2, "Eddy", "Michel", "Eddy M.", 2, true),
            Player(3, "Fabienne", "Gordeau", "Fabi", 13, true),
            Player(5, "Aude", "Armani", "Aude A.", 10, true),
            Player(6, "Sarah", "Yasma", "Sarah Y.", 15, true),
            Player(2, "Marion", "Mori", "Momo", 16, true),
            Player(3, "Adrien", "Aloué", "Adri", 6, true),
            Player(5, "Patrick", "Dubosse", "Patrick D.", 1, true),
            Player(6, "Manon", "Erztatz", "Manon E.", 12, true)
        )
    )

    fun player() = Player(1, "Jérome", "Purin", "Jérôme P.", 3, true)



    fun players() = listOf(
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "Tanguy", R.drawable.man_4, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "Tanguy", R.drawable.man_4, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "Tanguy", R.drawable.man_4, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),
        Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_5, true),

    )

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
        Course(1, "Parcours du cygne", 18, 72, 3, 4, LocalDate.now()),
        Course(1, "Parcours du chevreuil", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours du lac", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours de la marmotte", 18, 72, 3, 3, LocalDate.now()),
        Course(1, "Parcours du bassin", 18, 72, 3, 2, LocalDate.now()),
        Course(1, "Parcours du marcassin", 18, 72, 3, 2, LocalDate.now()),
    )

    fun course() = Course(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now())

    fun courseDetails() = CourseDetails(1, "Parcours du chêne", 18, 72, 3, 4, LocalDate.now(), listOf(
        Hole(1, 3),
        Hole(2, 4),
        Hole(3, 4),
        Hole(4, 3),
        Hole(5, 3),
        Hole(6, 5),
        Hole(7, 3),
        Hole(8, 3),
        Hole(9, 5),
        Hole(10, 3),
        Hole(11, 3),
        Hole(12, 3),
        Hole(13, 4),
        Hole(14, 3),
        Hole(15, 4),
        Hole(16, 3),
        Hole(17, 5),
        Hole(18, 3)
    ))

    fun games() = listOf(
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Patrick", 1, true),
            Player(1, "Tanguy", "Pouriel", "Jérôme", 3, true)
        ), "Parcours du cygne", LocalDate.now()),
        Game(2, "Parcours du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Adrien", 6, true),
            Player(1, "Tanguy", "Pouriel", "Fabienne", 13, true),
            Player(1, "Tanguy", "Pouriel", "Eddy", 2, true)
        ), "Parcours du chêne", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Jérémy", 5, true),
            Player(1, "Tanguy", "Pouriel", "Aude", 10, true),
            Player(1, "Tanguy", "Pouriel", "Sarah", 15,true)
        ), "Parcours du lac", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Jérôme", 3, true),
            Player(1, "Tanguy", "Pouriel", "Patrick", 1, true),
            Player(1, "Tanguy", "Pouriel", "Marion", 16, true)
        ), "Parcours du lac", LocalDate.now()),
        Game(2, "Parcours du 17/08/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Manon", 12, true),
            Player(1, "Tanguy", "Pouriel", "Patrick", 1, true),
            Player(1, "Tanguy", "Pouriel", "Fabienne", 13, true)
        ), "Parcours du cygne", LocalDate.now()),
        Game(1, "Parcours du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Adrien", 6, true),
            Player(1, "Tanguy", "Pouriel", "Eddy", 2, true),
            Player(1, "Tanguy", "Pouriel", "Jérôme", 3, true)
        ), "Parcours du chêne", LocalDate.now())
    )
    fun pendingGames() = listOf(
        Game(1, "Partie du 17/08/21", GBState.DONE, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Patrick", 1, true),
            Player(1, "Tanguy", "Pouriel", "Eddy", 2, true)
        ), "Parcours de la belette des sables", LocalDate.now()),
        Game(2, "Partie du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "Aude", 10, true),
            Player(1, "Tanguy", "Pouriel", "Marion", 16, true),
            Player(1, "Tanguy", "Pouriel", "Sarah", 15, true)
        ), "Parcours de la peugot 207", LocalDate.now()))

    fun game() = Game(2, "Partie du 24/06/21", GBState.PENDING, ScoringSystem.STROKE_PLAY, listOf(
            Player(1, "Tanguy", "Pouriel", "MindeurFou", R.drawable.man_2, true),
            Player(1, "Roro", "Pouriel", "Frere le boss", R.drawable.woman_2, true),
            Player(1, "Lisa", "Pouriel", "Frere le boss", R.drawable.woman_4, true)
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
                "Jérôme P.",
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
                "Eddy M.",
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
                    "Sarah",
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

    fun gameDetails(state: GBState? = null) = GameDetails(
        1,
        "Partie des copains",
        state ?: GBState.DONE,
        LocalDate.now(),
        ScoringSystem.STROKE_PLAY,
        "Parcours du tube",
        listOf(2, 4, 3, 5, 3, 4, 2, 4, 3, 5, 3, 4, 3, 3, 5, 3, 4, 3),
        listOf(
            Player(1, "Jérôme", "Purin", "Jérôme P.", 3, true),
            Player(1, "Eddy", "", "Eddy M.", 2, true),
            Player(1, "Sarah", "Prasil", "Sarah", 15, true)
        ),
        scoreSummaries = listOf(
            ScoreSummary("1.", "Jérôme P.", "2 up"),
            ScoreSummary("T2.", "Eddy M.", "1 up"),
            ScoreSummary("T2.", "Sarah", "1 up"),
        ),
        scoreBook = scoreBook()
    )
}
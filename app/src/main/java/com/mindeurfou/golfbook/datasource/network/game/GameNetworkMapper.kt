package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.*
import com.mindeurfou.golfbook.data.game.remote.GameDetailsNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PlayerScoreNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.ScoreBookNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.ScoreDetailsNetworkEntity

object GameNetworkMapper {

    fun toGameDetails(gameDetailsNetworkEntity: GameDetailsNetworkEntity) : GameDetails {

        return GameDetails(
            gameDetailsNetworkEntity.id,
            gameDetailsNetworkEntity.name,
            gameDetailsNetworkEntity.state,
            gameDetailsNetworkEntity.date,
            gameDetailsNetworkEntity.scoringSystem,
            gameDetailsNetworkEntity.courseName,
            gameDetailsNetworkEntity.par,
            gameDetailsNetworkEntity.players,
            listOf(
                ScoreSummary("1", "Tanguy", "1"),
                ScoreSummary("2", "Romane", "0")
            ),
            toScoreBook(gameDetailsNetworkEntity.par.size, gameDetailsNetworkEntity.scoreBook, parList = gameDetailsNetworkEntity.par)
        )
    }

    fun toScoreBook(numberOfHoles: Int, scoreBookNetworkEntity: ScoreBookNetworkEntity, parList: List<Int>) : ScoreBook {
        return ScoreBook(
            scoreBookNetworkEntity.playerScores.map { toPlayerScore(numberOfHoles, it, parList) }
        )
    }

    fun toScoreBookNetworkEntity(scoreBook: ScoreBook) : ScoreBookNetworkEntity {
        return ScoreBookNetworkEntity(
            scoreBook.playerScores.map { toPlayerScoreNetworkEntity(it) }
        )
    }

    private fun toPlayerScoreNetworkEntity(playerScore: PlayerScore) : PlayerScoreNetworkEntity {

        val scores: MutableList<ScoreDetailsNetworkEntity> = mutableListOf()

        playerScore.scores.forEach { scoreDetails ->
            scoreDetails?.let {
                scores.add(
                    ScoreDetailsNetworkEntity(
                        score = it.score,
                        net = it.net
                    )
                )
            }
        }

        return PlayerScoreNetworkEntity(
            playerScore.name,
            scores,
            playerScore.scoreSum?.toString() ?: "",
            playerScore.netSum ?: ""
        )
    }

    private fun toPlayerScore(numberOfHoles: Int, playerScoreNetworkEntity: PlayerScoreNetworkEntity, parList: List<Int>) : PlayerScore {

        val scores: MutableList<ScoreDetails?> = mutableListOf()
        val size = playerScoreNetworkEntity.scores.size

        for (i in 0 until numberOfHoles) {
            if (i < size)
                scores.add(toScoreDetails(playerScoreNetworkEntity.scores[i], parList[i]))
            else
                scores.add(null)
        }

        val scoreSum = if (playerScoreNetworkEntity.scoreSum.isBlank())
            null
        else
            playerScoreNetworkEntity.scoreSum.toInt()

        val netSum = if (playerScoreNetworkEntity.netSum.isBlank())
            null
        else
            playerScoreNetworkEntity.netSum

        return PlayerScore(
            playerScoreNetworkEntity.name,
            scores,
            scoreSum,
            netSum
        )
    }

    private fun toScoreDetails(scoreDetailsNetworkEntity: ScoreDetailsNetworkEntity, par: Int) : ScoreDetails {

        val scoreType = when (scoreDetailsNetworkEntity.score - par) {
            -2 -> ScoreType.EAGLE
            -1 -> ScoreType.BIRDIE
            0 -> ScoreType.PAR
            1 -> ScoreType.BOGEY
            2 -> ScoreType.DOUBLE_BOGEY
            3 -> ScoreType.HIGHER_THAN_DB
            else -> ScoreType.PAR
        }
        return ScoreDetails(
            score = scoreDetailsNetworkEntity.score,
            scoreType = scoreType,
            net = scoreDetailsNetworkEntity.net
        )
    }
}
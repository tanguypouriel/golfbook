package com.mindeurfou.golfbook.datasource.network.game

import com.mindeurfou.golfbook.data.game.local.*
import com.mindeurfou.golfbook.data.game.remote.GameDetailsNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.PlayerScoreNetworkEntity
import com.mindeurfou.golfbook.data.game.remote.ScoreBookNetworkEntity

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
            toScoreBook(gameDetailsNetworkEntity.par.size, gameDetailsNetworkEntity.scoreBook)
        )
    }

    fun toScoreBook(numberOfHoles: Int, scoreBookNetworkEntity: ScoreBookNetworkEntity) : ScoreBook {
        return ScoreBook(
            scoreBookNetworkEntity.playerScores.map { toPlayerScore(numberOfHoles, it) }
        )
    }

    fun toScoreBookNetworkEntity(scoreBook: ScoreBook) : ScoreBookNetworkEntity {
        return ScoreBookNetworkEntity(
            scoreBook.playerScores.map { toPlayerScoreNetworkEntity(it) }
        )
    }

    private fun toPlayerScoreNetworkEntity(playerScore: PlayerScore) : PlayerScoreNetworkEntity {

        val scores: MutableList<ScoreDetails> = mutableListOf()

        playerScore.scores.forEach { scoreDetails ->
            scoreDetails?.let {
                scores.add(it)
            }
        }

        return PlayerScoreNetworkEntity(
            playerScore.name,
            scores,
            playerScore.scoreSum?.toString() ?: "",
            playerScore.netSum ?: ""
        )
    }

    private fun toPlayerScore(numberOfHoles: Int, playerScoreNetworkEntity: PlayerScoreNetworkEntity) : PlayerScore {

        val scores: MutableList<ScoreDetails?> = mutableListOf()
        val size = playerScoreNetworkEntity.scores.size

        for (i in 0 until numberOfHoles) {
            if (i < size)
                scores.add(playerScoreNetworkEntity.scores[i])
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
}
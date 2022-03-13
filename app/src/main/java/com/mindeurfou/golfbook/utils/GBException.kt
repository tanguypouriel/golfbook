package com.mindeurfou.golfbook.utils

class GBException(override val message: String) : Exception() {

    companion object {
        const val GAME_NOT_FIND_MESSAGE        = "Game hasn't been found"
        const val COURSE_NOT_FIND_MESSAGE      = "Course hasn't been found"
        const val PLAYER_NOT_FIND_MESSAGE      = "Player hasn't been found"
        const val TOURNAMENT_NOT_FIND_MESSAGE  = "Tournament hasn't been found"
        const val SCOREBOOK_NOT_FIND_MESSAGE   = "Scorebook hasn't been found"
        const val LEADERBOARD_NOT_FIND_MESSAGE = "Leaderboard hasn't been found"
        const val BAD_CREDENTIALS              = "Bad credentials"

        const val INVALID_OPERATION_MESSAGE    = "Invalid operation"
        const val TOURNAMENT_DONE_MESSAGE      = "This tournament is finished"
        const val NO_RESOURCES_MESSAGE         = "There isn't any resources matching your criteria"
        const val USERNAME_ALREADY_TAKEN_MESSAGE = "This username is already taken"
        const val NAME_ALREADY_TAKEN_MESSAGE   = "This name is already taken"
        const val UNAUTHORIZED                 = "You can't access this ressource"
        const val SERVER_ERROR                 = "Server error"
    }
}

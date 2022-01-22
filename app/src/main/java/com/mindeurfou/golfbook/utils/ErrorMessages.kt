package com.mindeurfou.golfbook.utils

enum class ErrorTag {SNACK, SPECIFIC}

enum class ErrorMessages(
    val tag: ErrorTag
) {
    NAME_EMPTY(ErrorTag.SPECIFIC),
    HOLES_UNCOMPLETED(ErrorTag.SPECIFIC),
    BAD_CREDENTIALS(ErrorTag.SNACK),
    NETWORK_ERROR(ErrorTag.SNACK),
    USERNAME_TAKEN(ErrorTag.SPECIFIC),
    EMPTY_RESSOURCE(ErrorTag.SNACK),
    NO_GAMES(ErrorTag.SPECIFIC),
    BAD_INPUT(ErrorTag.SNACK),
    INTERNAL_ERROR(ErrorTag.SNACK),
    LASTNAME_EMPTY(ErrorTag.SPECIFIC),
    USERNAME_EMPTY(ErrorTag.SPECIFIC),
    COURSE_EMPTY(ErrorTag.SPECIFIC),
    SCORING_SYSTEM_EMPTY(ErrorTag.SPECIFIC),
    UNKNOWN_SCORING_SYSTEM(ErrorTag.SPECIFIC),
    NAME_ALREADY_TAKEN(ErrorTag.SPECIFIC),
    PLAYER_NOT_IN_GAME(ErrorTag.SNACK),
    GAME_FULL(ErrorTag.SNACK),
    PASSWORD_EMPTY(ErrorTag.SPECIFIC);

    override fun toString(): String {
        return when(name){
            NAME_EMPTY.name -> "Le champs prénom est vide"
            HOLES_UNCOMPLETED.name -> "Les trous n'ont pas tous été renseigné"
            BAD_CREDENTIALS.name -> "L'identifiant ou le mot de passe est incorrect"
            NETWORK_ERROR.name -> "Erreur réseau"
            USERNAME_TAKEN.name -> "Ce pseudo est déjà pris"
            EMPTY_RESSOURCE.name -> "La ressource est vide pour l'instant"
            BAD_INPUT.name -> "Les informations rentrées sont incorrectes"
            INTERNAL_ERROR.name -> "Erreur interne"
            NO_GAMES.name -> "Aucune partie a été trouvée"
            LASTNAME_EMPTY.name -> "Le champs nom est vide"
            USERNAME_EMPTY.name -> "Le champs pseudo est vide"
            COURSE_EMPTY.name -> "Le champs parcours est vide"
            SCORING_SYSTEM_EMPTY.name -> "Le champs Formule de jeu est vide"
            UNKNOWN_SCORING_SYSTEM.name -> "La formule de jeu n'a pas été reconnue"
            NAME_ALREADY_TAKEN.name -> "Ce nom est déjà pris"
            PLAYER_NOT_IN_GAME.name -> "Le joueur n'est pas dans la partie"
            GAME_FULL.name -> "La partie est déjà pleine"
            PASSWORD_EMPTY.name -> "Le champs mot de passe est vide"
            else -> "Erreur"
        }
    }

    companion object {

        const val snack = "SNACK"
        const val specific = "SPECIFIC"

        fun sort(errors: List<ErrorMessages>) : Map<String, List<ErrorMessages>> {
            val snackErrors = errors.filter { it.tag == ErrorTag.SNACK }
            val specificErrors = errors.filter { it.tag == ErrorTag.SPECIFIC }
            return mapOf(snack to snackErrors, specific to specificErrors)
        }
    }
}
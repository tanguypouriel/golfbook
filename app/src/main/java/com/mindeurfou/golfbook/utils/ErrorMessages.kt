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
    BAD_INPUT(ErrorTag.SNACK);

    override fun toString(): String {
        return when(name){
            NAME_EMPTY.name -> "Le champs nom est vide"
            HOLES_UNCOMPLETED.name -> "Les trous n'ont pas tous été renseigné"
            BAD_CREDENTIALS.name -> "L'identifiant ou le mot de passe est incorrect"
            NETWORK_ERROR.name -> "Erreur réseau"
            USERNAME_TAKEN.name -> "Ce pseudo est déjà pris"
            EMPTY_RESSOURCE.name -> "La ressource est vide pour l'instant"
            BAD_INPUT.name -> "Les informations rentrées sont incorrectes"
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
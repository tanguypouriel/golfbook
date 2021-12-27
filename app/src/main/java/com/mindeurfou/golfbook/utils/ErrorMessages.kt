package com.mindeurfou.golfbook.utils

enum class ErrorMessages {
    NAME_EMPTY,
    HOLES_UNCOMPLETED,
    BAD_CREDENTIALS,
    NETWORK_ERROR,
    USERNAME_TAKEN,
    EMPTY_RESSOURCE;

    override fun toString(): String {
        return when(name){
            NAME_EMPTY.name -> "Le champs nom est vide"
            HOLES_UNCOMPLETED.name -> "Les trous n'ont pas tous été renseigné"
            BAD_CREDENTIALS.name -> "L'identifiant ou le mot de passe est incorrect"
            NETWORK_ERROR.name -> "Erreur réseau"
            USERNAME_TAKEN.name -> "Ce pseudo est déjà pris"
            EMPTY_RESSOURCE.name -> "La ressource est vide pour l'instant"
            else -> "Erreur"
        }
    }}
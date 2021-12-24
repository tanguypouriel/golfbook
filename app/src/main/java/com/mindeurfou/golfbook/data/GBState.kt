package com.mindeurfou.golfbook.data

enum class GBState {
    INIT,
    STARTING,
    PENDING,
    DONE;

    override fun toString(): String {
        return when(name) {
            INIT.name -> "Etat initial"
            STARTING.name -> "Démarre"
            PENDING.name -> "En cours"
            DONE.name -> "Terminé"
            else -> "Etat inconnu"
        }
    }}
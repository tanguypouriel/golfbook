package com.mindeurfou.golfbook.data

enum class GBState {
    WAITING,
    PENDING,
    DONE;

    override fun toString(): String {
        return when(name) {
            WAITING.name -> "Démarre"
            PENDING.name -> "En cours"
            DONE.name -> "Terminé"
            else -> "Etat inconnu"
        }
    }}
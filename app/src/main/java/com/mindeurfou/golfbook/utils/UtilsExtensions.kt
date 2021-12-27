package com.mindeurfou.golfbook.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.print(): String =
    this.format(DateTimeFormatter.ofPattern("dd/MM/yy"))

fun MutableList<String>.addError(error: ErrorMessages): Boolean =
        this.add(error.toString())
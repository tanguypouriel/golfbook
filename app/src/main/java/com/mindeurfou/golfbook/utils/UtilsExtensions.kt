package com.mindeurfou.golfbook.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.print(): String =
    this.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
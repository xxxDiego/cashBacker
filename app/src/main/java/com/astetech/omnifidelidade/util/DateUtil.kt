package com.astetech.omnifidelidade.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter



        fun obterDataCorrente(): LocalDate {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var date = current.format(formatter)
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

        fun stringToLocalDate (data: String): LocalDate{
            return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }


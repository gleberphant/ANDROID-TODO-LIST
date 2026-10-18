package com.github.gleberphant.pucpr_devmobile_as1

data class Tarefa(
    val id: String,
    val nome: String,
    var status: Boolean = false
)
package com.github.gleberphant.pucpr_devmobile_as1

data class Tarefa(
    var id: String,
    var nome: String,
    var status: Boolean = false
)
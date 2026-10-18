package com.github.gleberphant.pucpr_devmobile_as1

class TarefaRepositorio {
    var listaTarefa: MutableList<Tarefa> = mutableListOf()


    fun adicionar(novaTarefa : Tarefa){
        val ultimoID = listaTarefa.lastOrNull()?.id?.toInt()?:0

        listaTarefa.add(
            Tarefa(
                id = ultimoID.toString(),
                nome = novaTarefa.nome,
                status = novaTarefa.status
            )
        )
    }

    fun exibir(index: Int) : Tarefa{
        if (index >= listaTarefa.size || index < 0) return listaTarefa.first()
        return listaTarefa[index]
    }

    fun tamanho(): Int{
        return listaTarefa.size
    }

    fun procurarPorId(id: String): Tarefa{
        for(i: Int in listaTarefa.indices){
           if (listaTarefa[i].id == id)return listaTarefa[i]
        }
        return listaTarefa[0]
    }

    fun remover(index: Int){
        if (index >= listaTarefa.size || index < 0) return
        listaTarefa.removeAt(index)
    }

    fun editar(index: Int, novaTarefa: Tarefa){
        if (index >= listaTarefa.size || index < 0) return
        listaTarefa[index] =  novaTarefa

    }

}
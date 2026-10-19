package com.github.gleberphant.pucpr_devmobile_as1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// classe que recebe repositorio de dados e faz o binding com o componente visual
class TarefaAdaptador(
    val repositorioTarefa: TarefaRepositorio,
    val callbackEditar:(index: Int, tarefa: Tarefa) -> Unit,
    val callbackRemover:(id: String, index: Int) -> Unit
)  : RecyclerView.Adapter<TarefaAdaptador.TarefaRefs>(){

    private lateinit var parentView: ViewGroup
    // classe interna que guarda ponteiro para os elementos visuais
    inner class TarefaRefs(tarefaView: View) : RecyclerView.ViewHolder(tarefaView){
        val txtId: TextView = tarefaView.findViewById<TextView>(R.id.txtId)
        val txtNome: TextView = tarefaView.findViewById<TextView>(R.id.txtTarefa)
        val btnExcluir: ImageButton = tarefaView.findViewById<ImageButton>(R.id.btnExcluir)
        val btnEditar: ImageButton = tarefaView.findViewById<ImageButton>(R.id.btnEditar)
    }


    // cria as referências para o componente tarefa
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaRefs {
        parentView = parent

        val tarefaView = LayoutInflater.from(parentView.context)
            .inflate(R.layout.tarefa_view, parentView, false)


        return TarefaRefs(tarefaView)

    }

    // faz o bind dos dados com o componente tarefa
    override fun onBindViewHolder(tarefaRefs: TarefaRefs, index: Int) {
        val tarefa = repositorioTarefa.buscarPorIndice(index)

        tarefaRefs.txtId.text = tarefa.id
        tarefaRefs.txtNome.text = tarefa.nome

        tarefaRefs.btnExcluir.setOnClickListener {
            val currentPosition = tarefaRefs.bindingAdapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            callbackRemover(tarefa.id, currentPosition)
            //removerItem(currentPosition)

        }
        tarefaRefs.btnEditar.setOnClickListener {
            val currentPosition = tarefaRefs.bindingAdapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            callbackEditar(currentPosition, tarefa)
            //editarItem(currentPosition, tarefa)

        }
    }

    override fun getItemCount(): Int {
        return repositorioTarefa.tamanho()
    }






}
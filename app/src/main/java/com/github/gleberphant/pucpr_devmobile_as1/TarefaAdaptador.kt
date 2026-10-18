package com.github.gleberphant.pucpr_devmobile_as1

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.material3.AlertDialog
import androidx.recyclerview.widget.RecyclerView


// classe que recebe repositorio de dados e faz o binding com o componente visual
class TarefaAdaptador(
    val repositorioTarefa: TarefaRepositorio,
    val callbackEditar:(index: Int, tarefa: Tarefa) -> Unit,
    val callbackRemover:(index: Int) -> Unit
)  : RecyclerView.Adapter<TarefaAdaptador.TarefaRefs>(){

    private lateinit var parentView: ViewGroup
    // classe interna que guarda ponteiro para os elementos visuais
    inner class TarefaRefs(tarefaView: View) : RecyclerView.ViewHolder(tarefaView){
        val txtNome: TextView = tarefaView.findViewById<TextView>(R.id.txtTarefa)
        val btnExcluir: Button = tarefaView.findViewById<Button>(R.id.btnExcluir)
        val btnEditar: Button = tarefaView.findViewById<Button>(R.id.btnEditar)
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
        val tarefa = repositorioTarefa.exibir(index)

        tarefaRefs.txtNome.text = tarefa.nome

        tarefaRefs.btnExcluir.setOnClickListener {
            val currentPosition = tarefaRefs.bindingAdapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            callbackRemover(currentPosition)
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
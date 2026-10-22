package com.github.gleberphant.pucpr_devmobile_as1

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// classe que recebe repositorio de dados e faz o binding com o componente visual
class TarefaAdaptador(
    val repositorioTarefa: TarefaRepositorio
)  : RecyclerView.Adapter<TarefaAdaptador.TarefaHolder>(){

    private lateinit var parentView: ViewGroup
    // classe interna que guarda ponteiro para os elementos visuais
    inner class TarefaHolder(tarefaView: View) : RecyclerView.ViewHolder(tarefaView){
        //val txtId: TextView = tarefaView.findViewById<TextView>(R.id.txtId)
        val txtNome: TextView = tarefaView.findViewById<TextView>(R.id.txtTarefa)
        val btnExcluir: ImageButton = tarefaView.findViewById<ImageButton>(R.id.btnExcluir)
        val btnEditar: ImageButton = tarefaView.findViewById<ImageButton>(R.id.btnEditar)
    }


    // cria as referências para o componente tarefa.
    // view holder é a estrutura que guarda os ponteiros para os elementos visuais
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaHolder {
        parentView = parent


        val tarefaView = LayoutInflater.from(parentView.context)
            .inflate(R.layout.tarefa_view, parentView, false)


        return TarefaHolder(tarefaView)

    }

    // faz o bind dos dados com o componente tarefa
    override fun onBindViewHolder(tarefaHolder: TarefaHolder, index: Int) {
        val tarefa = repositorioTarefa.buscarPorIndice(index)

        //tarefaHolder.txtId.text = tarefa.id
        tarefaHolder.txtNome.text = tarefa.nome

        tarefaHolder.btnExcluir.setOnClickListener {
            val currentPosition = tarefaHolder.bindingAdapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            btnEventRemoverItem(currentPosition, tarefa.id)


        }
        tarefaHolder.btnEditar.setOnClickListener {
            val currentPosition = tarefaHolder.bindingAdapterPosition

            if (currentPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            btnEventEditarItem(currentPosition, tarefa)
            //editarItem(currentPosition, tarefa)

        }
    }

    override fun getItemCount(): Int {
        return repositorioTarefa.tamanho()
    }


    //CRUD
    fun btnEventAdicionarItem(tarefaTexto: String){

        if (tarefaTexto.isEmpty()) return

        repositorioTarefa.adicionar(Tarefa("", tarefaTexto, true))

        notifyItemInserted(repositorioTarefa.tamanho()-1)
    }

    fun btnEventEditarItem(index: Int, tarefaSelecionada: Tarefa){
        val inputEdicao = EditText(parentView.context).apply{
            setText(tarefaSelecionada.nome)
            setSelection(tarefaSelecionada.nome.length)
        }

        AlertDialog.Builder(parentView.context)
            .setTitle("Editar Tarefa")
            .setMessage("Atualizar a tarefa")
            .setView(inputEdicao)
            .setPositiveButton("Salvar",{ _, _ ->

                val novoTexto = inputEdicao.text.toString().trim()
                repositorioTarefa.editarPorId(tarefaSelecionada.id, Tarefa(
                    tarefaSelecionada.id,
                    novoTexto,
                    tarefaSelecionada.status
                ))
                // notifica a adição do item para o View
                notifyItemChanged(index)
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }
    fun btnEventRemoverItem(index: Int, id: String){
        repositorioTarefa.removerPorId(id)
        // Notifica a remoção do item para o View
        notifyItemRemoved(index)
        // Atualiza as posições dos itens subsequentes para recalcular bindings
        notifyItemRangeChanged(index, repositorioTarefa.tamanho())
    }




}
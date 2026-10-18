package com.github.gleberphant.pucpr_devmobile_as1

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    class MainRefs(
        val recycleView: RecyclerView,
        val inputTarefa: EditText,
        val btnAdicionar: Button
    )
    private lateinit var refs : MainRefs
    private lateinit var repositorio : TarefaRepositorio
    private lateinit var adaptador: TarefaAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // configura referências aos componentes
        refs = MainRefs(
            findViewById<RecyclerView>(R.id.rvTarefas),
            findViewById<EditText>(R.id.inputNovaTarefa),
            findViewById<Button>(R.id.btnAdicionar)
        )
        // configura repositorio
        repositorio = TarefaRepositorio()

        // configura adaptador
        adaptador= TarefaAdaptador(
            repositorio,
            { index, tarefa -> editarItem (index, tarefa) },
            { index -> removerItem(index) })

        repositorio.adicionar(Tarefa("1", "tarefa 1", true))
        repositorio.adicionar(Tarefa("2", "tarefa 2", true))
        repositorio.adicionar(Tarefa("3", "tarefa 3", true))

        // configura o recycleView

        refs.recycleView.layoutManager = LinearLayoutManager(this)
        refs.recycleView.adapter = adaptador

        // configurar listener dos botões do menu principal
        refs.btnAdicionar.setOnClickListener {
            adicionarItem()
        }

    }

    //CRUD
    fun adicionarItem(){
        val tarefaTexto = refs.inputTarefa.text.toString().trim()
        if (tarefaTexto.isEmpty()) return

        repositorio.adicionar(Tarefa("", tarefaTexto, true))
        refs.inputTarefa.text.clear()
        adaptador.notifyItemInserted(repositorio.tamanho()-1)
    }

    fun editarItem(index: Int, tarefaSelecionada: Tarefa){
        val inputEdicao = EditText(this).apply{
            setText(tarefaSelecionada.nome)
            setSelection(tarefaSelecionada.nome.length)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar Tarefa")
            .setMessage("Atualizar a tarefa")
            .setView(inputEdicao)
            .setPositiveButton("Salvar",{ dialog, which ->
                val novoTexto = inputEdicao.text.toString().trim()
                repositorio.editar(index, Tarefa(
                    tarefaSelecionada.id,
                    novoTexto,
                    tarefaSelecionada.status
                ))
                // notifica a adição do item para o View
                adaptador.notifyItemChanged(index)
            })
            .setNegativeButton("Cancelar", null)
            .show()
    }
    fun removerItem(index: Int){
        repositorio.remover(index)
        // Notifica a remoção do item para o View
        adaptador.notifyItemRemoved(index)
        // Atualiza as posições dos itens subsequentes para recalcular bindings
        adaptador.notifyItemRangeChanged(index, repositorio.tamanho() - index)
    }


}


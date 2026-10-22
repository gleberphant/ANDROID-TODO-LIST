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
import kotlin.text.clear
import kotlin.toString


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
        repositorio = TarefaRepositorio(this)
        repositorio.init()

        // configura adaptador
        adaptador= TarefaAdaptador(repositorio)

        //repositorio.adicionar(Tarefa("1", "tarefa 1", true))
        //repositorio.adicionar(Tarefa("2", "tarefa 2", true))
        //repositorio.adicionar(Tarefa("3", "tarefa 3", true))

        // configura o recycleView
        refs.recycleView.layoutManager = LinearLayoutManager(this)
        refs.recycleView.adapter = adaptador

        // configurar listener dos botões do menu principal
        refs.btnAdicionar.setOnClickListener({
            adaptador.btnEventAdicionarItem(refs.inputTarefa.text.toString().trim())
            refs.inputTarefa.text.clear()
        })

    }




}


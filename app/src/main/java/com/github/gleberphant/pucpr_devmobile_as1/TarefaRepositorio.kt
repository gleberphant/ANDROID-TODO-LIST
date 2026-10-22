package com.github.gleberphant.pucpr_devmobile_as1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TarefaRepositorio(context: Context) : SQLiteOpenHelper(
    context,
    "meu_banco.db",
    null,
    1
)  {

    val listaCache = mutableListOf<Tarefa>()
    var proximoID: Int = 0
    var quantidadeItens: Int = 0

    fun init() {

        carregaListaCache()

    }

    private fun carregaListaCache(){
        val db = readableDatabase

        val curso = db.rawQuery("""
            SELECT * FROM tarefas
            ORDER BY id
        """.trimIndent(), null  )

        try {
            while (curso.moveToNext()){
                quantidadeItens++
                val novaTarefa=Tarefa(
                    curso.getString(curso.getColumnIndexOrThrow("id")),
                    curso.getString(curso.getColumnIndexOrThrow("nome")),
                    true
                )

                listaCache.add(novaTarefa)
                proximoID=novaTarefa.id.toInt()+1
            }

        } finally {
            curso.close()
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = """
            CREATE TABLE tarefas(
                id TEXT NOT NULL,
                nome TEXT NOT NULL,
                status TEXT NOT NULL DEFAULT "true"
            );
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun adicionar(novaTarefa : Tarefa){


        novaTarefa.id = proximoID.toString()

        val db = writableDatabase

        db.execSQL(
            """
            INSERT INTO tarefas (id, nome, status)
            VALUES(?,?,?)
            """.trimIndent(),
            arrayOf(novaTarefa.id, novaTarefa.nome, novaTarefa.status.toString())
        )
        quantidadeItens++
        proximoID++

        listaCache.add(novaTarefa)

    }

    fun buscarPorIndice(index: Int) : Tarefa{

        val db = readableDatabase

        val curso = db.rawQuery("""
            SELECT count() FROM tarefas
            ORDER BY id

        """.trimIndent(), null  )

        try {
            curso.moveToFirst()
            val count = curso.getInt(0)
            if (count != listaCache.size)
                  carregaListaCache()
        } finally {
            curso.close()
        }

        return listaCache[index]
    }

    fun tamanho(): Int{
    return listaCache.size
    //return quantidadeItens
    }

    fun buscarPorId(id: String): Tarefa? {

        val db = readableDatabase

        val sql = """
            SELECT * FROM tarefas WHERE id LIKE ?
            """.trimIndent()

        val curso = db.rawQuery(sql, arrayOf(id))

        try {
            if (curso.moveToFirst()){
                return Tarefa(
                    id=curso.getString(curso.getColumnIndexOrThrow("id")),
                    nome=curso.getString(curso.getColumnIndexOrThrow("nome")),
                    status= curso.getString(curso.getColumnIndexOrThrow("status")).toBoolean()
                )
            }
        } finally {
            curso.close()
        }


        return null
    }


    fun removerPorId(ID: String){

        val db= writableDatabase

        db.execSQL("""
               DELETE FROM tarefas
               WHERE id = ?
        """.trimIndent(),
            arrayOf(ID))

        val cursor = db.rawQuery("""SELECT changes()""", null)

        try {
            if (cursor.moveToFirst() && cursor.getInt(0) > 0) quantidadeItens--
        }finally {
            cursor.close()
        }

        listaCache.removeIf { tarefa -> tarefa.id == ID }

    }

    fun editarPorId(id: String, novaTarefa: Tarefa){
        val db = writableDatabase

        db.execSQL("""
            UPDATE tarefas 
            SET nome= ?, status=?
            WHERE id = ?
        """.trimIndent(),
            arrayOf(novaTarefa.nome, novaTarefa.status.toString(), novaTarefa.id) )

        for((i, v) in listaCache.withIndex() ){
            if (v.id == id)
                listaCache[i]=novaTarefa
          }
    }

}
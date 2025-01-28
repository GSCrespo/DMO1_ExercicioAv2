package br.edu.ifsp.dmo1.pesquisaopiniao.data.database

import android.util.Log
import androidx.core.content.contentValuesOf
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Aluno
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto

class MeuDAO (private val dbhelper : DatabaseHelper) {

    fun insertAluno(dadosAluno: Aluno){
        val values = contentValuesOf().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO,dadosAluno.prontuario)
        }

        val db = dbhelper.writableDatabase

        try {
            val result = db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_ALUNO,null,values)

            // se der erro, o  metodo insert retorna -1l
            if(result  == -1L){
                Log.e("DatabaseError", "Erro ao tentar inserir  aluno")
            }else{
                Log.d("DatabaseSucess","Aluno inserido com sucesso")
            }

        }catch (e: Exception){
            Log.e("DatabaseException","Erro ao inserir aluno")
        }

    }


    fun insertVoto(dadosVoto: Voto){
        val values = contentValuesOf().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO,dadosVoto.codigo)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_OPCAO, dadosVoto.opcao)
        }

        val db = dbhelper.writableDatabase

        try {
            val result = db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_VOTO,null,values)

            // se der erro, o  metodo insert retorna -1l
            if(result  == -1L){
                Log.e("DatabaseError", "Erro ao tentar inserir um voto")
            }else{
                Log.d("DatabaseSucess","voto inserido com sucesso")
            }

        }catch (e: Exception){
            Log.e("DatabaseException","Erro ao inserir o voto")
        }

    }


    fun getByProntuario(prontuario: String): Aluno?{
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO,
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO} = ?"
        val whereArgs = arrayOf(prontuario)

        val db = dbhelper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_ALUNO,columns,where,whereArgs,null,null,null
        )

        val dado: Aluno?

        cursor.use {
            dado = if(cursor.moveToNext()){
                Aluno(
                    cursor.getString(0), // prontuario
                )
            }
            else{
                null
            }
        }
        return dado
    }


    fun getContagemVotos() : Int{
        // declarando a coluna como COUNT para apenas retornar o valor total de votos
        val columns = arrayOf(
            "COUNT(*) AS total"
        )
        val db = dbhelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO, columns, null, null, null, null, null
        )

        var contagem = 0
        cursor.use {
            if (cursor.moveToFirst()) {
                contagem = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
            }
        }
        return contagem
    }

    // para verificar se o aluno já tem voto registrado
    fun getVotoByAluno(prontuario: String): Voto? {
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_OPCAO
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO} IN (SELECT ${DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO} FROM ${DatabaseHelper.DATABASE_KEYS.TABLE_ALUNO} WHERE ${DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO} = ?)"
        val whereArgs = arrayOf(prontuario)

        val db = dbhelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO, columns, where, whereArgs, null, null, null
        )

        var voto: Voto? = null

        cursor.use {
            if (cursor.moveToNext()) {
                voto = Voto(
                    cursor.getString(0),
                    cursor.getString(1)
                )
            }
        }
        return voto
    }

    //contando votos por opção
    fun getTotalVotesByOption(opcao: String): Int {
        val columns = arrayOf("COUNT(*) AS total")
        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_OPCAO} = ?"
        val whereArgs = arrayOf(opcao)

        val db = dbhelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO, columns, where, whereArgs, null, null, null
        )

        var contagem = 0
        cursor.use {
            if (cursor.moveToFirst()) {
                contagem = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
            }
        }
        return contagem
    }

    fun getVotoByCodigo(codigo: String): Voto? {
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_OPCAO
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO} = ?"
        val whereArgs = arrayOf(codigo)

        val db = dbhelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_VOTO, columns, where, whereArgs, null, null, null
        )

        var voto: Voto? = null

        cursor.use {
            if (cursor.moveToNext()) {
                voto = Voto(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_OPCAO))
                )
            }
        }

        return voto
    }
}
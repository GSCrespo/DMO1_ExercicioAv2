package br.edu.ifsp.dmo1.pesquisaopiniao.data.database

import android.util.Log
import androidx.core.content.contentValuesOf
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Aluno
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto

class MeuDAO (private val dbhelper : DatabaseHelper) {

    fun insertAluno(dadosAluno: Aluno){
        val values = contentValuesOf().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO,dadosAluno.prontuario)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_NAME,dadosAluno.nome)
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
        }finally {
            db.close()
        }


    }


    fun insertVoto(dadosVoto: Voto){
        val values = contentValuesOf().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_ID,dadosVoto.id)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_VOTO_CODIGO,dadosVoto.codigo)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO,dadosVoto.voto_prontuario)
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
        }finally {
            db.close()
        }



    }
}
package br.edu.ifsp.dmo1.pesquisaopiniao.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_KEYS.DATABASE_NAME,null,DATABASE_KEYS.DATABASE_VERSION) {

    object DATABASE_KEYS{
        const val DATABASE_NAME = "PesquisaVotos_databse.db"
        const val DATABASE_VERSION = 1
        const val TABLE_ALUNO = "tb_aluno"
        const val TABLE_VOTO = "tb_voto"

        const val COLUMN_ALUNO_PRONTUARIO = "prontuario"
        const val COLUMN_ALUNO_NAME = "nome"

        const val COLUMN_VOTO_CODIGO = "codigo"
        const val COLUMN_VOTO_OPCAO = "opcao"

    }

    private companion object {

        const val CREATE_TABLE_ALUNO = "CREATE TABLE ${DATABASE_KEYS.TABLE_ALUNO} " +
                "(" + "${DATABASE_KEYS.COLUMN_ALUNO_PRONTUARIO} TEXT PRIMARY KEY NOT NULL," +
                "${DATABASE_KEYS.COLUMN_ALUNO_NAME})"

        const val CREATE_TABLE_VOTO = "CREATE TABLE ${DATABASE_KEYS.TABLE_VOTO} " +
                "(" + "${DATABASE_KEYS.COLUMN_VOTO_CODIGO} TEXT PRIMARY KEY AUTOINCREMENT," +
                "${DATABASE_KEYS.COLUMN_VOTO_OPCAO} TEXT)"



    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_ALUNO)
        db.execSQL(CREATE_TABLE_VOTO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // por enquanto nao ha necessidade de outra versao no banco, deixarei nao implementado
    }


}
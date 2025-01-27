package br.edu.ifsp.dmo1.pesquisaopiniao.data.repository

import android.content.Context
import br.edu.ifsp.dmo1.pesquisaopiniao.data.database.DatabaseHelper
import br.edu.ifsp.dmo1.pesquisaopiniao.data.database.MeuDAO
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Aluno
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto

class PesquisaRepository (context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val dao = MeuDAO(dbHelper)

    //usar para verificar se o aluno ja participou da pesquisa
    fun getAlunoByProntuario(prontuario : String) : Aluno?{
        return dao.getByProntuario(prontuario)
    }

    fun addAluno(dadoAluno: Aluno) = dao.insertAluno(dadoAluno)
    fun addVoto(dadoVoto: Voto) = dao.insertVoto(dadoVoto)

}
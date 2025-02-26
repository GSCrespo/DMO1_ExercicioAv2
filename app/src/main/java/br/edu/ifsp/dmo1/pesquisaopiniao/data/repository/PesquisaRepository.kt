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

    // Novo método para buscar o total de votos
    fun getTotalVotes(): Int {
        return dao.getContagemVotos()
    }

    fun hasAlunoVoted(prontuario: String): Boolean {
        val voto = dao.getVotoByAluno(prontuario)
        return voto != null
    }

    fun getTotalVotesByOption(opcao: String): Int {
        return dao.getTotalVotesByOption(opcao)
    }

    fun getVotoByCodigo(codigo: String): Voto? {
        return dao.getVotoByCodigo(codigo)
    }
}
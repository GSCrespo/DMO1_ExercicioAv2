package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository

class MainViewModel(private val repository: PesquisaRepository) : ViewModel() {

    private val _canParticipate = MutableLiveData<Boolean>()
    val canParticipate: LiveData<Boolean> get() = _canParticipate

    private val _canCheckVote = MutableLiveData<Boolean>()
    val canCheckVote: LiveData<Boolean> get() = _canCheckVote

    private val _voteCount = MutableLiveData<Int>()
    val voteCount: LiveData<Int> get() = _voteCount

    private val _totalVotes = MutableLiveData<Map<String, Int>>()
    val totalVotes: LiveData<Map<String, Int>> get() = _totalVotes

    // verificar se o aluno já votou
    fun checkIfAlunoCanParticipate(prontuario: String) {
        val hasVoted = repository.hasAlunoVoted(prontuario)
        _canParticipate.value = !hasVoted
        _canCheckVote.value = hasVoted
    }

    // finalizar a pesquisa (mostrar total de votos)
    fun finishSurvey() {
        val totalVotes = repository.getTotalVotes()
        _voteCount.value = totalVotes

        // Contabilizar votos por opção
        val votosPorOpcao = mapOf(
            "Ruim" to repository.getTotalVotesByOption("Ruim"),
            "Bom" to repository.getTotalVotesByOption("Bom"),
            "Regular" to repository.getTotalVotesByOption("Regular"),
            "Ótimo" to repository.getTotalVotesByOption("Ótimo")
        )
        _totalVotes.value = votosPorOpcao
    }
}

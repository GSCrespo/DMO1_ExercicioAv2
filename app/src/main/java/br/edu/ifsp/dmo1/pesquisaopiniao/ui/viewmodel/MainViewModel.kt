package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository

class MainViewModel(private val repository: PesquisaRepository) : ViewModel() {

    private val _voteCount = MutableLiveData<Int>()
    val voteCount: LiveData<Int> get() = _voteCount

    private val _totalVotes = MutableLiveData<Map<String, Int>>()
    val totalVotes: LiveData<Map<String, Int>> get() = _totalVotes

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

package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Aluno
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository

class ParticipateSearchViewModel(private val repository: PesquisaRepository) : ViewModel() {

    private val _votoRegistrado = MutableLiveData<Voto?>()
    val votoRegistrado: LiveData<Voto?> get() = _votoRegistrado

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    // registrar o voto
    fun registerVoto(prontuario: String, opcao: String) {
        var aluno = repository.getAlunoByProntuario(prontuario)

        //aluno existe e ainda não votou
        if (aluno == null) {
            aluno = Aluno(prontuario = prontuario)
            repository.addAluno(aluno)
        }

        if (repository.hasAlunoVoted(prontuario)) {
            _errorMessage.value = "Você já votou na pesquisa."
        } else {
            val voto = Voto(opcao = opcao)
            repository.addVoto(voto)
            _votoRegistrado.value = voto
            _successMessage.value = "Voto registrado com sucesso!"
        }
    }

    fun resetMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}

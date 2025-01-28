package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Aluno
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository
import kotlin.random.Random

class ParticipateSearchViewModel(private val repository: PesquisaRepository) : ViewModel() {

    private val _votoRegistrado = MutableLiveData<Voto?>()
    val votoRegistrado: LiveData<Voto?> get() = _votoRegistrado

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> get() = _successMessage

    //para gerar codigos
    fun generateRandomCode(length: Int = 10): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    // registrar o voto
    fun registerVoto(prontuario: String, opcao: String) {
        val aluno = repository.getAlunoByProntuario(prontuario)

        //aluno existe e ainda não votou
        if (aluno == null) {
            val novoAluno = Aluno(prontuario = prontuario)
            repository.addAluno(novoAluno)
        }

        if (repository.hasAlunoVoted(prontuario)) {
            _errorMessage.value = "Você já votou na pesquisa."
        } else {
            val codigoVoto = generateRandomCode()
            val voto = Voto(opcao = opcao, codigo = codigoVoto)
            repository.addVoto(voto)
            _votoRegistrado.value = voto
            _successMessage.value = "Voto registrado com sucesso! Código: $codigoVoto"
        }
    }

    fun resetMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}

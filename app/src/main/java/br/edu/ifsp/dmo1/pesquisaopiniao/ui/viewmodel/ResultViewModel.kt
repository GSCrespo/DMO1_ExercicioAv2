package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.model.Voto
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository

class ResultViewModel(private val repository: PesquisaRepository) : ViewModel() {

    private val _votoEncontrado = MutableLiveData<Voto?>()
    val votoEncontrado: LiveData<Voto?> get() = _votoEncontrado

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun buscarVotoPorCodigo(codigo: String) {
        val voto = repository.getVotoByCodigo(codigo)
        if (voto != null) {
            _votoEncontrado.value = voto
        } else {
            _errorMessage.value = "Voto não encontrado."
        }
    }
}

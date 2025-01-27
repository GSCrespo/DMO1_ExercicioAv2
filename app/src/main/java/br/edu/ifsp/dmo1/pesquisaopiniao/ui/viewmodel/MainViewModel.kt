// MainViewModel.kt
package br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _sampleData = MutableLiveData<String>()
    val sampleData: LiveData<String> get() = _sampleData

    fun updateSampleData(data: String) {
        _sampleData.value = data
    }
}

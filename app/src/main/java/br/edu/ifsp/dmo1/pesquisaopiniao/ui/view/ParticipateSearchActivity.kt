package br.edu.ifsp.dmo1.pesquisaopiniao.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.pesquisaopiniao.R
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository
import br.edu.ifsp.dmo1.pesquisaopiniao.databinding.ActivityParticipateSearchBinding
import br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel.ParticipateSearchViewModel

class ParticipateSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticipateSearchBinding
    private val repository by lazy { PesquisaRepository(this) }
    private val viewModel: ParticipateSearchViewModel by viewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ParticipateSearchViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParticipateSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // spinner com as opções de voto
        val options = arrayOf("Ruim", "Regular", "Bom", "Ótimo")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        binding.spinnerOptions.adapter = adapter

        //setando usuario para nulo sempre que essa activity é aberta
        //viewModel.setAlunoNull()

        setupObservers()

        // botão para registrar voto
        binding.buttonSubmitVote.setOnClickListener {
            val prontuario = binding.inputProntuario.text.toString()
            val selectedOption = binding.spinnerOptions.selectedItem.toString()

            if (prontuario.isBlank()) {
                Toast.makeText(this, "Por favor, insira seu prontuário.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.registerVoto(prontuario, selectedOption)
            }
        }
    }

    private fun setupObservers() {
        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrBlank()) {
                binding.tvErrorMessage.text = errorMessage
                binding.tvErrorMessage.visibility = View.VISIBLE
                binding.tvSuccessMessage.visibility = View.GONE
            }
        }

        viewModel.successMessage.observe(this) { successMessage ->
            if (!successMessage.isNullOrBlank()) {
                binding.tvSuccessMessage.text = successMessage
                binding.tvSuccessMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
            }
        }

        viewModel.votoRegistrado.observe(this) { voto ->
            if (voto != null) {
                Toast.makeText(this, "Voto registrado: ${voto.opcao}. Código: ${voto.codigo}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

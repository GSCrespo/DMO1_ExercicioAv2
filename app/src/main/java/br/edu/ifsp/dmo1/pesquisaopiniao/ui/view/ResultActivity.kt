package br.edu.ifsp.dmo1.pesquisaopiniao.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo1.pesquisaopiniao.R
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository
import br.edu.ifsp.dmo1.pesquisaopiniao.databinding.ActivityResultBinding
import br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel.ResultViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val repository by lazy { PesquisaRepository(this) }
    private val viewModel: ResultViewModel by viewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return ResultViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

        // Botão para buscar o voto
        binding.buttonBuscarVoto.setOnClickListener {
            val codigo = binding.inputCodigo.text.toString()

            if (codigo.isBlank()) {
                Toast.makeText(this, "Por favor, insira o código do voto.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.buscarVotoPorCodigo(codigo)
            }
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            // retornar para a Main Activity
            finish()
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

        viewModel.votoEncontrado.observe(this) { voto ->
            if (voto != null) {
                binding.tvSuccessMessage.text = "Voto encontrado! Opção: ${voto.opcao}"
                binding.tvSuccessMessage.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
            }
        }
    }
}

package br.edu.ifsp.dmo1.pesquisaopiniao.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel.MainViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository
import br.edu.ifsp.dmo1.pesquisaopiniao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val repository by lazy { PesquisaRepository(this) }
    private val mainViewModel: MainViewModel by lazy {
        MainViewModel(repository)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnParticipar.setOnClickListener {
            startActivity(Intent(this, ParticipateSearchActivity::class.java))
        }

        binding.btnChecarVoto.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }

        binding.btnFinalizar.setOnClickListener {
            mainViewModel.finishSurvey()

            // alterei a dinamica aqui para ficar de acordo com o doc de requisitos, para exibir
            // as contagens apenas apos clicar no botao finalizar
            binding.txtVoteCount.visibility = View.VISIBLE
            binding.txtResults.visibility = View.VISIBLE
        }

        // usando o observer para o livedata para atualizar os dados
        mainViewModel.voteCount.observe(this, Observer { voteCount ->
            binding.txtVoteCount.text = "Total de Votos: $voteCount"
        })

        mainViewModel.totalVotes.observe(this, Observer { totalVotes ->
            totalVotes?.let {
                binding.txtResults.text = it.toString()
            }
        })
    }
}

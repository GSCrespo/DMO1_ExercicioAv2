package br.edu.ifsp.dmo1.pesquisaopiniao.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.dmo1.pesquisaopiniao.R
import br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel.MainViewModel
import br.edu.ifsp.dmo1.pesquisaopiniao.data.repository.PesquisaRepository

class MainActivity : AppCompatActivity() {

    private val repository by lazy { PesquisaRepository(this) }
    private val mainViewModel: MainViewModel by lazy {
        MainViewModel(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botões de navegação
        findViewById<Button>(R.id.btnParticipar).setOnClickListener {
            startActivity(Intent(this, ParticipateSearchActivity::class.java))
        }

        findViewById<Button>(R.id.btnChecarVoto).setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }

        findViewById<Button>(R.id.btnFinalizar).setOnClickListener {
            mainViewModel.finishSurvey()
        }

        mainViewModel.voteCount.observe(this, Observer { voteCount ->
            // Exibir contagem de votos
            findViewById<TextView>(R.id.txtVoteCount).text = "Total de Votos: $voteCount"
        })

        mainViewModel.totalVotes.observe(this, Observer { totalVotes ->
            // Exibir total de votos por opção
            totalVotes?.let {
                findViewById<TextView>(R.id.txtResults).text = it.toString()
            }
        })
    }
}

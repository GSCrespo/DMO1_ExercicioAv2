// MainActivity.kt
package br.edu.ifsp.dmo1.pesquisaopiniao.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.dmo1.pesquisaopiniao.R
import br.edu.ifsp.dmo1.pesquisaopiniao.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.sampleData.observe(this, Observer { data ->
            println("Dado recebido: $data")
        })

        mainViewModel.updateSampleData("Hello, ViewModel!")
    }
}

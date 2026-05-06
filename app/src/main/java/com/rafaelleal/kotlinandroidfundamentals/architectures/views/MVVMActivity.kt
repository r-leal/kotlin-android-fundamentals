package com.rafaelleal.kotlinandroidfundamentals.architectures.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rafaelleal.kotlinandroidfundamentals.R
import com.rafaelleal.kotlinandroidfundamentals.databinding.ActivityMvvmactivityBinding

class MVVMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmactivityBinding

    private val viewModel: MVVMViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura o View Binding
        binding = ActivityMvvmactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observa as mudanças de dados na ViewModel
        viewModel.text.observe(this) { novoTexto ->
            binding.textViewStatus.text = novoTexto
        }

        // Exemplo de interação: Atualizando a ViewModel ao clicar em um botão
        binding.buttonUpdate.setOnClickListener {
            viewModel.updateText("Texto atualizado via ViewModel!")
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }
}
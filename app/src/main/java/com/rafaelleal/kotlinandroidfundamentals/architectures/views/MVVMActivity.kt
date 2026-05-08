package com.rafaelleal.kotlinandroidfundamentals.architectures.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

        binding = ActivityMvvmactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.text.observe(this) { newText ->
            binding.textViewStatus.text = newText
        }

        viewModel.showLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.linearLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.showErrorMessage.observe(this) { showError ->
            binding.linearLayoutError.visibility = if (showError) View.VISIBLE else View.GONE
            binding.buttonShowError.visibility = if (showError) View.GONE else View.VISIBLE
        }

        binding.buttonUpdate.setOnClickListener {
            viewModel.updateText("Text was updated by the ViewModel!")
        }

        binding.buttonShowLoading.setOnClickListener {
            viewModel.showLoading()
        }

        binding.buttonShowError.setOnClickListener {
            viewModel.showErrorMessage()
        }

        binding.buttonTryAgain.setOnClickListener {
            viewModel.tryAgain()
            binding.textViewStatus.text = "Waiting..."
        }
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }
}
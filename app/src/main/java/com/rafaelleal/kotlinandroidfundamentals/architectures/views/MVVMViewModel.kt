package com.rafaelleal.kotlinandroidfundamentals.architectures.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MVVMViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Bem-vindo ao MVVM!"
    }
    val text: LiveData<String> = _text
    fun updateText(newText: String) {
        _text.value = newText
    }


}
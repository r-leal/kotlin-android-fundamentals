package com.rafaelleal.kotlinandroidfundamentals.architectures.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MVVMViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome to MVVM!"
    }
    val text: LiveData<String> = _text
    private val _showLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val showLoading: LiveData<Boolean> = _showLoading
    private val _showErrorMessage = MutableLiveData<Boolean>().apply {
        value = false
    }
    val showErrorMessage: LiveData<Boolean> = _showErrorMessage




    fun updateText(newText: String) {
        _text.value = newText
    }

    fun showLoading(){
        viewModelScope.launch {
            _showLoading.value = true
            delay(2000)
            _showLoading.value = false
        }
    }

    fun showErrorMessage(){
        viewModelScope.launch {
            _showErrorMessage.value = true
        }
    }

    fun tryAgain(){
        viewModelScope.launch {
            _showErrorMessage.value = false
        }
    }

}
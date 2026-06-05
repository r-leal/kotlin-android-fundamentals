package com.rafaelleal.kotlinandroidfundamentals.fundamentals.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.FlowCollectIntent
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.FlowCollectState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FlowCollectViewModel : ViewModel() {

    private val _state = MutableStateFlow(FlowCollectState())
    val state = _state.asStateFlow()

    fun onIntent(intent: FlowCollectIntent) {
        when (intent) {
            is FlowCollectIntent.MoveDown -> moveDown(intent.step)
            is FlowCollectIntent.MoveLeft -> moveLeft(intent.step)
            is FlowCollectIntent.MoveRight -> moveRight(intent.step)
            is FlowCollectIntent.MoveUp -> moveUp(intent.step)
        }
    }

    private fun moveUp(step: Float) {
        viewModelScope.launch {
            val microStep = step / 10
            for (i in 1..10) {
                delay(100)
                _state.value = _state.value
                    .copy(
                        position = _state.value.position.copy(coordY = _state.value.position.coordY - microStep)
                    )
            }
        }
    }

    private fun moveRight(step: Float) {
        viewModelScope.launch {
            val microStep = step / 10
            for (i in 1..10) {
                delay(100)
                _state.value = _state.value
                    .copy(
                        position = _state.value.position.copy(coordX = _state.value.position.coordX + microStep)
                    )
            }
        }
    }

    private fun moveLeft(step: Float) {
        viewModelScope.launch {
            val microStep = step / 10
            for (i in 1..10) {
                delay(100)
                _state.value = _state.value
                    .copy(
                        position = _state.value.position.copy(coordX = _state.value.position.coordX - microStep)
                    )
            }
        }
    }

    private fun moveDown(step: Float) {
        viewModelScope.launch {
            val microStep = step / 10
            for (i in 1..10) {
                delay(100)
                _state.value = _state.value
                    .copy(
                        position = _state.value.position.copy(coordY = _state.value.position.coordY + microStep)
                    )
            }
        }
    }

}
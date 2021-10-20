package com.missclickads.cleaner.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _viewStates = MutableLiveData<BaseUiState>(BaseUiState.NotOptimize)
    val viewStates : LiveData<BaseUiState> = _viewStates

    fun startOptimization(){
        _viewStates.value = BaseUiState.Optimization
    }

    fun endOptimization(){
        _viewStates.value = BaseUiState.Optimized
    }
}
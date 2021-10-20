package com.missclickads.cleaner.core

sealed class BaseUiState {
    object NotOptimize : BaseUiState()
    object Optimization : BaseUiState()
    object Optimized : BaseUiState()
    class Error(val err : String) : BaseUiState()
}
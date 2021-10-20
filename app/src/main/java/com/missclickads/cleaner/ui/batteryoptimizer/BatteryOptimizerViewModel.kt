package com.missclickads.cleaner.ui.batteryoptimizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.missclickads.cleaner.core.BaseViewModel

class BatteryOptimizerViewModel : BaseViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

}
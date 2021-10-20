package com.missclickads.cleaner.ui.cpucooler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.missclickads.cleaner.core.BaseViewModel

class CpuCoolerViewModel :  BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
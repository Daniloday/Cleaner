package com.missclickads.cleaner.di


import com.missclickads.cleaner.ui.batteryoptimizer.BatteryOptimizerViewModel
import com.missclickads.cleaner.ui.cpucooler.CpuCoolerViewModel
import com.missclickads.cleaner.ui.filemanager.FileManagerViewModel
import com.missclickads.cleaner.ui.junkcleaner.JunkCleanerViewModel
import com.missclickads.cleaner.ui.phonebooster.PhoneBoosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        BatteryOptimizerViewModel()
    }
    viewModel {
        CpuCoolerViewModel()
    }
    viewModel {
        FileManagerViewModel()
    }
    viewModel {
        JunkCleanerViewModel()
    }
    viewModel {
        PhoneBoosterViewModel()
    }

}
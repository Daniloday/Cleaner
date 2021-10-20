package com.missclickads.cleaner.di

import android.content.Context
import com.missclickads.cleaner.utils.PhoneData

import org.koin.dsl.module

val dataModule = module {
    single {
        PhoneData(get())
    }
}



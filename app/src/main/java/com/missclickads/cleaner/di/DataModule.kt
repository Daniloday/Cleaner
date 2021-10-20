package com.missclickads.cleaner.di

import android.content.Context
import com.missclickads.cleaner.utils.PhoneData

import org.koin.dsl.module

val dataModule = module {
    single {
        providePhoneData(get())
    }
}

fun providePhoneData(context: Context) : PhoneData {
    return PhoneData(context)
}


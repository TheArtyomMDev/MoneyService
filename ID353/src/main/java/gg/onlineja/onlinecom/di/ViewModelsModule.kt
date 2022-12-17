package gg.onlineja.onlinecom.di

import gg.onlineja.onlinecom.ui.splash.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelsModule = module {

    viewModel {
        SplashScreenViewModel(get(), get(), get(), get())
    }
}
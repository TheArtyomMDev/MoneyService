package gg.onlineja.onlinecom.ui.splash

import gg.onlineja.onlinecom.data.ResponseMainData

sealed class SplashScreenState {
    object Loading : SplashScreenState()
    data class Successful(val mainData: ResponseMainData) : SplashScreenState()
    object Error : SplashScreenState()
    object Empty : SplashScreenState()
}

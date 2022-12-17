package gg.onlineja.onlinecom.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.airbnb.lottie.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.destinations.CardsTabsScreenDestination
import gg.onlineja.onlinecom.ui.destinations.CategoriesScreenDestination
import gg.onlineja.onlinecom.ui.destinations.FakeTabsScreenDestination
import org.koin.androidx.compose.koinViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    val vm: SplashScreenViewModel = koinViewModel()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_load_animation))
        val logoAnimationState =
            animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress },
            Modifier.fillMaxSize()
        )

        when(val screenState = vm.splashScreenState.collectAsState().value) {
            is SplashScreenState.Error, SplashScreenState.Empty -> {
                navigator.navigate(FakeTabsScreenDestination)
            }
            is SplashScreenState.Successful -> {
                navigator.navigate(CategoriesScreenDestination(screenState.mainData))
            }
            is SplashScreenState.Loading -> {}
        }
    }
}
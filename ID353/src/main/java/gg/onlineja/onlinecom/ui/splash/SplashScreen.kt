package gg.onlineja.onlinecom.ui.splash

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.airbnb.lottie.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.destinations.CategoriesScreenDestination
import gg.onlineja.onlinecom.ui.destinations.DetailsScreenDestination
import gg.onlineja.onlinecom.ui.destinations.FakeTabsScreenDestination
import gg.onlineja.onlinecom.utils.network.NetworkChangeReceiver
import gg.onlineja.onlinecom.utils.network.NetworkUtils
import org.koin.androidx.compose.koinViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    deepLinkArgs: DeepLinkArgs
) {
    val vm: SplashScreenViewModel = koinViewModel()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val receiver = NetworkChangeReceiver(
            navigator = navigator,
            appContext = context
        )
        context.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

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

        val screenState = vm.splashScreenState.collectAsState().value
        val context = LocalContext.current

        LaunchedEffect(key1 = screenState) {
            when (screenState) {
                is SplashScreenState.Error, SplashScreenState.Empty -> {
                    if (NetworkUtils.isConnected(context))
                        navigator.navigate(FakeTabsScreenDestination)
                }
                is SplashScreenState.Successful -> {
                    if (NetworkUtils.isConnected(context)) {
                        navigator.navigate(CategoriesScreenDestination)
                        if (deepLinkArgs.category != null && deepLinkArgs.id != null) {
                            navigator.navigate(DetailsScreenDestination(deepLinkArgs.id,
                                deepLinkArgs.category)) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
                is SplashScreenState.Loading -> {}
            }
        }
    }
}

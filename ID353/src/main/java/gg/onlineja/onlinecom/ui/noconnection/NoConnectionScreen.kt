package gg.onlineja.onlinecom.ui.noconnection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.destinations.SplashScreenDestination
import gg.onlineja.onlinecom.ui.splash.SplashScreen
import gg.onlineja.onlinecom.ui.theme.MediumDimen
import gg.onlineja.onlinecom.ui.theme.RootDimen
import gg.onlineja.onlinecom.ui.theme.Typography
import gg.onlineja.onlinecom.utils.Constants
import gg.onlineja.onlinecom.utils.network.NetworkUtils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.get

@RootNavGraph
@Destination
@Composable
fun NoConnectionScreen(
    navigator: DestinationsNavigator
) {
    val dataStore: DataStore<Preferences> = get()

    Box(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(2 * MediumDimen)
        ) {

            Spacer(Modifier.height(150.dp))

            Text(
                "Проблемы подключения!",
                style = Typography.titleSmall
            )
            Image(
                painter = painterResource(R.drawable.wifi_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                "Проверьте настройки соединения и повторите попытку, также можете попробовать перезайти в приложение.",
                style = Typography.bodySmall
            )
        }

        Image(
            painter = painterResource(id = R.drawable.proceed_button_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.FillBounds
        )

        val context = LocalContext.current
        Image(
            painter = painterResource(R.drawable.retry_button),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .width(100.dp)
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    if (NetworkUtils.isConnected(context)) {
                        runBlocking {
                            val prefs = dataStore.data.first()
                            if (prefs[Constants.HAS_VISITED_CATEGORY] == true)
                                navigator.popBackStack()
                            else
                                navigator.navigate(SplashScreenDestination)
                        }
                    }
                }
        )
    }


    BackHandler(enabled = true) {}
}
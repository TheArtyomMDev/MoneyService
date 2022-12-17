package gg.onlineja.onlinecom.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.ui.destinations.NoConnectionScreenDestination
import gg.onlineja.onlinecom.utils.DeviceID

class NetworkChangeReceiver(
    private val appContext: Context,
    val navigator: DestinationsNavigator
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        println("NetworkChange received")

        if(!NetworkUtils.isConnected(appContext)) {
            navigator.navigate(NoConnectionScreenDestination) {
                launchSingleTop = true
            }
        }
    }
}
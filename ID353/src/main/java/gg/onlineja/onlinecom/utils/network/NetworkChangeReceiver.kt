package gg.onlineja.onlinecom.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.utils.DeviceID

class NetworkChangeReceiver(private val appContext: Context, navigator: DestinationsNavigator) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        println("NetworkChange received")

        if(!NetworkUtils.isConnected(appContext)) {
            //val mIntent = Intent(appContext, NoConnectionActivity::class.java)
            //mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //appContext.startActivity(mIntent)
        }
    }
}
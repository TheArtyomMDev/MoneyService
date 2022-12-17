package gg.onlineja.onlinecom.utils.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    @Suppress("Deprecation")
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo != null)
            if (cm.activeNetworkInfo!!.isConnected)
                return true

        return false
    }
}
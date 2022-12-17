package gg.onlineja.onlinecom.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.my.tracker.MyTracker
import com.scottyab.rootbeer.RootBeer
import gg.onlineja.onlinecom.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*


object DeviceID {

    fun getSimCountryIso(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        var simCountryIso = tm!!.simCountryIso

        if (simCountryIso == null || simCountryIso == "")
            simCountryIso = "null"

        return simCountryIso
    }

    suspend fun getColorFromConfig(): String {
        return try {
            val remoteConfig = Firebase.remoteConfig
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getString("color")
        } catch (e: Exception) {
            "no_color"
        }
    }

    fun hasRoot(context: Context): String? {
        val rootBeer = RootBeer(context)
        return if (rootBeer.isRooted) "granted"
        else "null"
    }

    fun getLocale(context: Context): Locale? {
        return context.resources.configuration.locale
    }

    @SuppressLint("HardwareIds")
    fun getAndroidID(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    suspend fun getGAID(context: Context): String? = withContext(Dispatchers.IO) {
        try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            adInfo.id
        } catch (e: Exception) {
            e.printStackTrace()
            "no-gaid"
        }
    }

    fun getMyTrackerId(context: Context): String? {
        return MyTracker.getInstanceId(context)
    }

    fun getVersion(): String {
        val versionName: String = BuildConfig.VERSION_NAME
        return versionName.replace(".", "")
    }

    fun getMCC(context: Context): Int {
        val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        val networkOperator = tel!!.networkOperator
        var mcc = 0

        if (!TextUtils.isEmpty(networkOperator)) {
            mcc = networkOperator.substring(0, 3).toInt()
        }
        return mcc
    }

    fun getMNC(context: Context): Int {
        val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
        val networkOperator = tel!!.networkOperator
        var mnc = 0

        if (!TextUtils.isEmpty(networkOperator)) {
            mnc = networkOperator.substring(3).toInt()
        }
        return mnc
    }

    fun getBatteryLevel(context: Context): Int {
        val bm = context.getSystemService(BATTERY_SERVICE) as BatteryManager
        return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    fun getWifiLevel(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities =cm.activeNetworkInfo ?: return ""
        val result = when(networkCapabilities.type) {
            ConnectivityManager.TYPE_WIFI -> {
                val wifiManager =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

                val rssi = wifiManager.connectionInfo.rssi
                WifiManager.calculateSignalLevel(rssi, 5).toString()
            }
            ConnectivityManager.TYPE_MOBILE -> {
                "phone_internet"
            }
            else -> "null"
        }

        return result
    }

    suspend fun getFirebaseToken(): String? {
        return try {
            val task = FirebaseMessaging.getInstance().token
            task.await()
        } catch (e: Exception) {
            "no_firebase_token"
        }
    }

}
package gg.onlineja.onlinecom.ui.splash

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import com.my.tracker.MyTracker
import gg.onlineja.onlinecom.data.APIService
import gg.onlineja.onlinecom.data.ResponseMainData
import gg.onlineja.onlinecom.data.subs.GetSubs
import gg.onlineja.onlinecom.utils.Constants
import com.yandex.metrica.AppMetricaDeviceIDListener
import com.yandex.metrica.YandexMetrica
import gg.onlineja.onlinecom.utils.DeviceID
import gg.onlineja.onlinecom.utils.network.NetworkUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first


class SplashScreenViewModel(
    private val apiService: APIService,
    private val context: Context,
    private val getSubs: GetSubs,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _splashScreenState: MutableStateFlow<SplashScreenState> = MutableStateFlow(SplashScreenState.Loading)
    val splashScreenState = _splashScreenState.asStateFlow()

    private val appMetrikaIdFlow = callbackFlow {
        YandexMetrica.requestAppMetricaDeviceID(object: AppMetricaDeviceIDListener {
            override fun onLoaded(p0: String?) { trySend(p0 ?: "empty-appmetrika") }
            override fun onError(p0: AppMetricaDeviceIDListener.Reason) { trySend("no-appmetrika-id") }
        })

        awaitClose {}
    }

    init {

        CoroutineScope(Dispatchers.IO).launch {
            val appMetrikaID = appMetrikaIdFlow.first()
            updateSubs(appMetrikaID)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val folder = getFolderPath()
            println("Folder is $folder")
            when(folder) {
                null -> {
                    _splashScreenState.emit(SplashScreenState.Error)
                }
                "null" -> {
                    _splashScreenState.emit(SplashScreenState.Empty)

                    val eventParams: MutableMap<String, String> = mutableMapOf()
                    eventParams["GAID"] = DeviceID.getGAID(context).toString()
                    MyTracker.trackEvent("actualbackend_null", eventParams)
                    YandexMetrica.reportEvent("actualbackend_null", eventParams.toMap())
                }
                else -> {
                    val mainData = getMainData(folder)!!
                    _splashScreenState.emit(SplashScreenState.Successful(mainData))
                }
            }
        }
    }

    private suspend fun getFolderPath(): String? {
        try {
            val response = apiService.uploadData(
                p1 = DeviceID.getSimCountryIso(context),
                p2 = DeviceID.getColorFromConfig(),
                p3 = DeviceID.hasRoot(context),
                p4 = DeviceID.getLocale(context).toString(),
                p5 = Constants.APPMETRIKA_API_KEY,
                p6 = DeviceID.getAndroidID(context),
                p7 = DeviceID.getFirebaseToken(),
                p8 = DeviceID.getGAID(context),
                p9 = DeviceID.getMyTrackerId(context),
                p10 = DeviceID.getVersion(),
                p11 = DeviceID.getMCC(context).toString(),
                p12 = DeviceID.getMNC(context).toString(),
                p13 = DeviceID.getBatteryLevel(context).toString(),
                p14 = DeviceID.getWifiLevel(context)
            )

            return response.body()?.actualbackend!!
        } catch (e: Exception) {
            MyTracker.trackEvent("backend_unavailable")
            YandexMetrica.reportEvent("backend_unavailable")
            return null
        }
    }

    private suspend fun updateSubs(AppMetricaDeviceID: String) {
        var isEmptySent = false
        var isAFSent = false
        var isMyTrackerSent = false

        dataStore.data.collect { prefs ->
            if (prefs[Constants.AFF_SUB1].isNullOrEmpty()) {
                val sub1 = getSubs.getSub1(AppMetricaDeviceID)
                dataStore.edit { it[Constants.AFF_SUB1] = sub1 }
            }

            if (!isEmptySent && prefs[Constants.AF_DATA].isNullOrEmpty() && prefs[Constants.MYTRACKER_DATA].isNullOrEmpty()) {
                isEmptySent = true
                val sub2 = getSubs.getSub2(null, null)
                dataStore.edit { it[Constants.AFF_SUB2] = sub2 }
            }
            if (!isAFSent && !prefs[Constants.AF_DATA].isNullOrEmpty()) {
                isAFSent = true
                val sub2 = getSubs.getSub2(prefs[Constants.AF_DATA], null)
                dataStore.edit { it[Constants.AFF_SUB2] = sub2 }
            }
            if (!isMyTrackerSent && !prefs[Constants.MYTRACKER_DATA].isNullOrEmpty()) {
                isMyTrackerSent = true
                val sub2 = getSubs.getSub2(null, prefs[Constants.MYTRACKER_DATA])
                dataStore.edit { it[Constants.AFF_SUB2] = sub2 }
            }

            if (prefs[Constants.AFF_SUB3].isNullOrEmpty()) {
                val sub3 = getSubs.getSub3()
                dataStore.edit { it[Constants.AFF_SUB3] = sub3 }
            }

            if (prefs[Constants.AFF_SUB4].isNullOrEmpty()) {
                val sub4 = getSubs.getSub4()
                dataStore.edit { it[Constants.AFF_SUB4] = sub4 }
            }

            if (prefs[Constants.AFF_SUB5].isNullOrEmpty()) {
                val sub5 = getSubs.getSub5()
                dataStore.edit { it[Constants.AFF_SUB5] = sub5 }
            }
        }
    }

    private suspend fun getMainData(folder: String): ResponseMainData? {
        return try {
            val body = apiService.getDbJson(folder).body()

            MyTracker.trackEvent("requestdb")
            YandexMetrica.reportEvent("requestdb")

            val appConfig = body?.app_config
            dataStore.edit { it[Constants.USER_TERM_HTML] = appConfig?.user_term_html.toString() }

            body
        } catch (e: Exception) {
            null
        }
    }

}
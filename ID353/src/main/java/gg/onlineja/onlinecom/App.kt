package gg.onlineja.onlinecom

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.gson.Gson
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import gg.onlineja.onlinecom.di.databaseModule
import gg.onlineja.onlinecom.di.networkModule
import gg.onlineja.onlinecom.di.viewModelsModule
import gg.onlineja.onlinecom.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import pro.userx.UserX

class App: Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelsModule, networkModule, databaseModule))
        }

        val dataStore: DataStore<Preferences> by inject()

        // AppMetrika
        val config = YandexMetricaConfig.newConfigBuilder(Constants.APPMETRIKA_API_KEY)
            .withLogs()
            .build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)

        runBlocking {
            val prefs = dataStore.data.first()
            if (prefs[Constants.IS_MYTRACKER_RETRIEVED] != true) MyTracker.setAttributionListener { attribution ->
                val deeplink = attribution.deeplink
                println("GOT DEEPLINK : $deeplink")

                runBlocking {
                    dataStore.edit {
                        it[Constants.MYTRACKER_DATA] = deeplink
                        it[Constants.IS_MYTRACKER_RETRIEVED] = true
                    }
                }
            }
            MyTracker.initTracker(Constants.MY_TRACKER_API_KEY, this@App)

            // AppsFlyer
            val conversionDataListener  = object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
                    println("data is ")
                    if (data != null) {
                        for(elem in data) println(elem)
                        runBlocking { dataStore.edit { it[Constants.AF_DATA] = Gson().toJson(data.toMap()) } }
                    }
                    runBlocking { dataStore.edit { it[Constants.IS_AF_RETRIEVED] = true } }
                }
                override fun onConversionDataFail(error: String?) {}
                override fun onAppOpenAttribution(data: MutableMap<String, String>?) {}
                override fun onAttributionFailure(error: String?) {}
            }

            AppsFlyerLib.getInstance().setDebugLog(true)
            AppsFlyerLib.getInstance().init(Constants.APPSFLYER_API_KEY,
                    if(prefs[Constants.IS_AF_RETRIEVED] != true) conversionDataListener else null, this@App)
            AppsFlyerLib.getInstance().start(this@App)
        }

        // FireBase
        FirebaseApp.initializeApp(this)
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        remoteConfig.reset()
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
            fetchTimeoutInSeconds = 60
        }
        remoteConfig.setDefaultsAsync(R.xml.config)
        remoteConfig.setConfigSettingsAsync(configSettings)

        // UserX
        UserX.init(Constants.USERX_API_KEY)
    }
}
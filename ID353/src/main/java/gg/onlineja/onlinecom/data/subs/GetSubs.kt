package gg.onlineja.onlinecom.data.subs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.appsflyer.AppsFlyerLib
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.my.tracker.MyTracker
import gg.onlineja.onlinecom.data.APIService
import gg.onlineja.onlinecom.data.subs.bodies.Sub1Body
import gg.onlineja.onlinecom.data.subs.bodies.Sub2Body
import gg.onlineja.onlinecom.data.subs.bodies.Sub3Body
import gg.onlineja.onlinecom.data.subs.bodies.Sub5Body
import gg.onlineja.onlinecom.utils.Constants
import gg.onlineja.onlinecom.utils.DeviceID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import org.json.JSONObject

class GetSubs(
    val context: Context,
    private val apiService: APIService,
    private val dataStore: DataStore<Preferences>
    ) {

    suspend fun getSub1(AppMetricaDeviceID: String): String {
        val payload1 = mutableMapOf<String, String>()
        payload1["MyTracker"] = MyTracker.getInstanceId(context)
        payload1["AppMetricaDeviceID"] = AppMetricaDeviceID
        payload1["Appsflyer"] = AppsFlyerLib.getInstance().getAppsFlyerUID(context)
        payload1["FireBase"] = DeviceID.getFirebaseToken().toString().take(30)

        val jsonedPayload = JSONObject(payload1.toMap())
        val serializedJson = jsonedPayload.toString()

        return try {
            val body = apiService.getAffSub1(
                body= Sub1Body(
                    user_id = DeviceID.getGAID(context),
                    payload_affsub1 = serializedJson
                )
            ).body()
            body!!.affsub1
        } catch (e: Exception) {
            e.printStackTrace()
            "empty-affsub1"
        }

    }

    suspend fun getSub2(appsFlyerData: String?, myTrackerData: String?): String {
        try {
            if (appsFlyerData == null && myTrackerData == null) {
                return apiService.getAffSub2(
                    body = Sub2Body(
                        user_id = DeviceID.getGAID(context),
                    )
                ).body()!!.affsub2
            }

            if(appsFlyerData != null) {
                println("AF : $appsFlyerData")

                val type = object : TypeToken<Map<String, String>>() {}.type
                val map: Map<String, String> = Gson().fromJson(appsFlyerData, type)

                return apiService.getAffSub2(
                    body = Sub2Body(
                        user_id = DeviceID.getGAID(context),
                        payload_appsflyer = JSONObject(map).toString()
                    )
                ).body()!!.affsub2
            }

            if(myTrackerData != null) {
                return apiService.getAffSub2(
                    body = Sub2Body(
                        user_id = DeviceID.getGAID(context),
                        payload_mytracker = myTrackerData
                    )
                ).body()!!.affsub2
            }
            return "empty-affsub2"
        } catch (e: Exception) {
            e.printStackTrace()
            return "empty-affsub2"
        }
    }

    suspend fun getSub3(): String {
        val payload3 = mutableMapOf<String, String>()
        payload3["FireBaseToken"] = DeviceID.getFirebaseToken().toString()

        val jsonedPayload = JSONObject(payload3.toMap())
        val serializedJson = jsonedPayload.toString()

        return try {
            apiService.getAffSub3(
                body = Sub3Body(
                    user_id = DeviceID.getGAID(context),
                    payload_affsub3 = serializedJson
                )
            ).body()!!.affsub3
        } catch (e: Exception) {
            "empty-affsub3"
        }
    }

    suspend fun getSub4(): String {
        return ""
    }

    suspend fun getSub5(): String {
        val payload5 = mutableMapOf<String, String>()
        payload5["GAID"] = DeviceID.getGAID(context).toString()

        val jsonedPayload = JSONObject(payload5.toMap())
        val serializedJson = jsonedPayload.toString()

        return try {
            apiService.getAffSub5(
                body = Sub5Body(
                    user_id = DeviceID.getGAID(context),
                    payload_affsub5 = serializedJson
                )
            ).body()!!.affsub5
        } catch (e: Exception) {
            "empty-affsub5"
        }
    }
}
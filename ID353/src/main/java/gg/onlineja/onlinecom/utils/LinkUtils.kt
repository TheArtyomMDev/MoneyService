package gg.onlineja.onlinecom.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import gg.onlineja.onlinecom.data.FinProduct
import kotlinx.coroutines.flow.first

object LinkUtils {
    suspend fun generateUrlAndTrack(
        finProduct: FinProduct, dataStore: DataStore<Preferences>, screen: String
    ): String {
        val prefs = dataStore.data.first()

        var url = finProduct.order
        url += "&aff_sub1=" + prefs[Constants.AFF_SUB1]
        url += "&aff_sub2=" + prefs[Constants.AFF_SUB2]
        url += "&aff_sub3=" + prefs[Constants.AFF_SUB3]
        url += "&aff_sub4=" + prefs[Constants.AFF_SUB4]
        url += "&aff_sub5=" + prefs[Constants.AFF_SUB5]

        val eventParams: MutableMap<String, String> = HashMap()
        eventParams["external_link"] = finProduct.itemId
        eventParams["screen"] = screen
        eventParams["url"] = url
        MyTracker.trackEvent("external_link")
        YandexMetrica.reportEvent("external_link", eventParams.toMap())

        return url
    }
}
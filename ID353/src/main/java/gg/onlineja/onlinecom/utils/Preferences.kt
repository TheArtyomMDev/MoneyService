package gg.onlineja.onlinecom.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
class Preferences(context: Context) {
    val preferences: SharedPreferences
    private val gson = Gson()

    private val editor: SharedPreferences.Editor
        get() = preferences.edit()

    /*
    var pin: String?
        get() = preferences.getString(PREF_PIN, "")
        set(data) {
            editor.putString(PREF_PIN, data).commit()
        }

     */

    // _________________________
    var affsub1: String
        get() = preferences.getString(PREF_AFF_SUB1, "")!!
        set(data) {
            editor.putString(PREF_AFF_SUB1, data).commit()
        }

    var affsub2: String
        get() = preferences.getString(PREF_AFF_SUB2, "")!!
        set(data) {
            editor.putString(PREF_AFF_SUB2, data).commit()
        }

    var affsub3: String
        get() = preferences.getString(PREF_AFF_SUB3, "")!!
        set(data) {
            editor.putString(PREF_AFF_SUB3, data).commit()
        }

    var affsub4: String
        get() = preferences.getString(PREF_AFF_SUB4, "")!!
        set(data) {
            editor.putString(PREF_AFF_SUB4, data).commit()
        }

    var affsub5: String
        get() = preferences.getString(PREF_AFF_SUB5, "")!!
        set(data) {
            editor.putString(PREF_AFF_SUB5, data).commit()
        }

    // _________________________

    var isEmptySent: Boolean
        get() = preferences.getBoolean(PREF_IS_EMPTY_SENT, false)
        set(data) {
            editor.putBoolean(PREF_IS_EMPTY_SENT, data).commit()
        }

    var isAfSent: Boolean
        get() = preferences.getBoolean(PREF_IS_AF_SENT, false)
        set(data) {
            editor.putBoolean(PREF_IS_AF_SENT, data).commit()
        }

    var isMyTrackerSent: Boolean
        get() = preferences.getBoolean(PREF_IS_MYTRACKER_SENT, false)
        set(data) {
            editor.putBoolean(PREF_IS_MYTRACKER_SENT, data).commit()
        }

    var isMyTrackerRetrieved: Boolean
        get() = preferences.getBoolean(PREF_IS_MYTRACKER_RETRIEVED, false)
        set(data) {
            editor.putBoolean(PREF_IS_MYTRACKER_RETRIEVED, data).commit()
        }

    var isAFRetrieved: Boolean
        get() = preferences.getBoolean(PREF_IS_AF_RETRIEVED, false)
        set(data) {
            editor.putBoolean(PREF_IS_AF_RETRIEVED, data).commit()
        }

    var wasOnMainScreen: Boolean
        get() = preferences.getBoolean(PREF_WAS_ON_MAIN_SCREEN, false)
        set(data) {
            editor.putBoolean(PREF_WAS_ON_MAIN_SCREEN, data).commit()
        }

    var navigated: Boolean
        get() = preferences.getBoolean(PREF_NAVIGATED, false)
        set(data) {
            editor.putBoolean(PREF_NAVIGATED, data).commit()
        }

    var afData: Map<String, Any>?
        get() {
            val dataString = preferences.getString(PREF_AF_DATA, "")
            //println("DataString is $dataString")
            val type = object : TypeToken<Map<String, Any>>() {}.type

            return if(dataString == "") null
            else gson.fromJson(dataString, type)
        }
        set(data) {
            val dataString = gson.toJson(data)
            editor.putString(PREF_AF_DATA, dataString).commit()
        }

    var myTrackerData: String?
        get() = preferences.getString(PREF_MYTRACKER_DATA, null)
        set(data) {
            editor.putString(PREF_MYTRACKER_DATA, data).commit()
        }

    var userTermHtml: String?
        get() = preferences.getString(PREF_USER_TERM_HTML, null)
        set(data) {
            editor.putString(PREF_USER_TERM_HTML, data).commit()
        }

    var isAgreementChecked: Boolean
        get() {
            return preferences.getBoolean(PREF_IS_AGREEMENT_CHECKED, false)
        }
        set(data) {
            editor.putBoolean(PREF_IS_AGREEMENT_CHECKED, data).commit()
        }

    companion object {
        const val APP_PREFS = "app_prefs"

        const val PREF_IS_MYTRACKER_RETRIEVED = "IS_MYTRACKER_RETRIEVED"
        const val PREF_IS_AF_RETRIEVED = "IS_AF_RETRIEVED"
        const val PREF_AF_DATA = "AF_DATA"
        const val PREF_MYTRACKER_DATA = "MYTRACKER_DATA"

        const val PREF_IS_EMPTY_SENT = "IS_EMPTY_SENT"
        const val PREF_IS_AF_SENT = "IS_AF_SENT"
        const val PREF_IS_MYTRACKER_SENT = "IS_MYTRACKER_SENT"

        const val PREF_USER_TERM_HTML = "USER_TERM_HTML"

        const val PREF_AFF_SUB1 = "AFF_SUB1"
        const val PREF_AFF_SUB2 = "AFF_SUB2"
        const val PREF_AFF_SUB3 = "AFF_SUB3"
        const val PREF_AFF_SUB4 = "AFF_SUB4"
        const val PREF_AFF_SUB5 = "AFF_SUB5"

        const val PREF_WAS_ON_MAIN_SCREEN = "PREF_WAS_ON_MAIN_SCREEN"
        const val PREF_NAVIGATED = "NAVIGATED"

        // ------
        const val PREF_IS_AGREEMENT_CHECKED = "IS_AGREEMENT_CHECKED "
    }

    init {
        preferences = context.getSharedPreferences(APP_PREFS, 0)
    }
}

 */
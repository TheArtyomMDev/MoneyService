package gg.onlineja.onlinecom.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val TAG = "RecommendFinance"

    const val BASE_URL = "https://bullshitcar.xyz"
    const val MAIN_SERVICE_API_KEY = "zyIyMLFotORbNpdtNpM0kYp7SBz/qEpKPcDlYI8/UBEVe0bum0a1vH2QLhfQMqv8MyroAvoCguNlAqntzBNIb5QI+ULEZWFOk20I6R+TcYqkAbo3JG6iMB9d44weXe6HpJ9BSmfOED6peEsMP8wP8WVQzKIcUft7f6gJvnPhRLZ496L5DR8YInILBNVnEAU="

    const val APPMETRIKA_API_KEY = "66be042e-de02-4293-839b-acefc006b9fb"
    const val MY_TRACKER_API_KEY = "28661488501755295699"
    const val USERX_API_KEY = "de9d95d9-449a-448a-be4d-39982fe84c7e"
    const val APPSFLYER_API_KEY = "jLNYzxQAqxAFeturo6YEAD"

    // DataStore
    val IS_MYTRACKER_RETRIEVED = booleanPreferencesKey("IS_MYTRACKER_RETRIEVED")
    val IS_AF_RETRIEVED = booleanPreferencesKey("IS_AF_RETRIEVED")
    val AF_DATA = stringPreferencesKey("AF_DATA")
    val MYTRACKER_DATA = stringPreferencesKey("MYTRACKER_DATA")
    val RESPONSE_MAIN_DATA = stringPreferencesKey("RESPONSE_MAIN_DATA")
    val HAS_VISITED_CATEGORY = booleanPreferencesKey("HAS_VISITED_CATEGORY")

    val USER_TERM_HTML = stringPreferencesKey("USER_TERM_HTML")

    val AFF_SUB1 = stringPreferencesKey("AFF_SUB1")
    val AFF_SUB2 = stringPreferencesKey("AFF_SUB2")
    val AFF_SUB3 = stringPreferencesKey("AFF_SUB3")
    val AFF_SUB4 = stringPreferencesKey("AFF_SUB4")
    val AFF_SUB5 = stringPreferencesKey("AFF_SUB5")
}
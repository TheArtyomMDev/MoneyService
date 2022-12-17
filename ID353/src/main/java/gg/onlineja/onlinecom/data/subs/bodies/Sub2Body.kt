package gg.onlineja.onlinecom.data.subs.bodies

import gg.onlineja.onlinecom.utils.Constants

data class Sub2Body(
    val application_token: String = Constants.MAIN_SERVICE_API_KEY,
    val user_id: String?,
    val payload_mytracker: String = "",
    val payload_appsflyer: String = ""
)

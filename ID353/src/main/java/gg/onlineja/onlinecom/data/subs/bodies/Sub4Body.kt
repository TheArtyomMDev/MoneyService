package gg.onlineja.onlinecom.data.subs.bodies

import gg.onlineja.onlinecom.utils.Constants

data class Sub4Body(
    val application_token: String = Constants.MAIN_SERVICE_API_KEY,
    val user_id: String?,
    val payload_affsub1: String
)

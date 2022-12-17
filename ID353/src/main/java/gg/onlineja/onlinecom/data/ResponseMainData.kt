package gg.onlineja.onlinecom.data

import AppConfigData
import gg.onlineja.onlinecom.data.cards.AllCardsTypes
import java.io.Serializable


data class ResponseMainData(
    val app_config: AppConfigData,
    val loans: Array<FinProduct>?,
    val credits: Array<FinProduct>?,
    val cards: Array<AllCardsTypes>?
): Serializable

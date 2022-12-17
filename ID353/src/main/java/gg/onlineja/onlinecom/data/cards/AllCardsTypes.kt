package gg.onlineja.onlinecom.data.cards

import gg.onlineja.onlinecom.data.FinProduct
import java.io.Serializable

data class AllCardsTypes(
    val cards_credit: Array<FinProduct>?,
    val cards_debit: Array<FinProduct>?,
    val cards_installment: Array<FinProduct>?,
): Serializable

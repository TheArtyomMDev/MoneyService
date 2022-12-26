package gg.onlineja.onlinecom.ui.splash

import gg.onlineja.onlinecom.ui.categories.Category

data class DeepLinkArgs(
    val category: Category? = null,
    val id: Int? = null
)

fun String.toCategory(): Category? {
    return when (this) {
        "loans" -> Category.Loans
        "cards_credit" -> Category.Cards.Credit
        "cards_debit" -> Category.Cards.Debit
        "cards_installment" -> Category.Cards.Installment
        "credits" -> Category.Credits
        else -> null
    }
}

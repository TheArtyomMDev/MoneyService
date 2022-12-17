package gg.onlineja.onlinecom.ui.categories

import java.io.Serializable

sealed class Category : Serializable {
    object Loans : Category()
    sealed class Cards : Category() {
        object Credit : Cards()
        object Debit : Cards()
        object Installment : Cards()
    }
    object Credits : Category()
    object All : Category()
}

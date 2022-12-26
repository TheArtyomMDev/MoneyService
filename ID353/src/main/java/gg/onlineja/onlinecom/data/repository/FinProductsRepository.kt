package gg.onlineja.onlinecom.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gg.onlineja.onlinecom.data.FinProduct
import gg.onlineja.onlinecom.data.ResponseMainData
import gg.onlineja.onlinecom.ui.categories.Category
import gg.onlineja.onlinecom.utils.Constants
import kotlinx.coroutines.flow.first

class FinProductsRepository(
    val dataStore: DataStore<Preferences>
) {
    suspend fun getFinProducts(category: Category): Array<FinProduct> {
        val data = dataStore.data.first()

        val type = object : TypeToken<ResponseMainData>() {}.type
        val responseMainData: ResponseMainData = Gson().fromJson(data[Constants.RESPONSE_MAIN_DATA], type)

        try {
            return when (category) {
                is Category.Credits -> {
                    responseMainData.credits ?: emptyArray()
                }
                is Category.Loans -> {
                    responseMainData.loans ?: emptyArray()
                }
                is Category.Cards -> {
                    for (i in  responseMainData.cards!!.indices) {
                        try {
                            return when (category) {
                                is Category.Cards.Credit -> {
                                    responseMainData.cards[i].cards_credit!!
                                }
                                is Category.Cards.Debit -> {
                                    responseMainData.cards[i].cards_debit!!
                                }
                                is Category.Cards.Installment -> {
                                    responseMainData.cards[i].cards_installment!!
                                }
                            }
                        } catch (_: Exception) { }
                    }
                    emptyArray()
                }
                is Category.All -> {
                    getFinProducts(Category.Credits) +
                            getFinProducts(Category.Loans) +
                            getFinProducts(Category.Cards.Credit) +
                            getFinProducts(Category.Cards.Debit) +
                            getFinProducts(Category.Cards.Installment)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyArray()
        }
    }

    suspend fun writeMainData(responseMainData: ResponseMainData) {
        dataStore.edit {
            it[Constants.RESPONSE_MAIN_DATA] = Gson().toJson(responseMainData)
        }
    }
}
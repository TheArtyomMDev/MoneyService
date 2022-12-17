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

        return when(category) {
            is Category.Credits -> {
                responseMainData.credits?: emptyArray()
            }
            is Category.Loans -> {
                responseMainData.loans?: emptyArray()
            }
            is Category.Cards -> {
                return try {
                    when(category) {

                        is Category.Cards.Credit -> {
                            responseMainData.cards!![0].cards_credit ?: emptyArray()
                        }
                        is Category.Cards.Debit -> {
                            responseMainData.cards!![1].cards_debit ?: emptyArray()
                        }
                        is Category.Cards.Installment -> {
                            responseMainData.cards!![2].cards_installment ?: emptyArray()
                        }
                    }
                } catch (e: Exception) {
                    emptyArray()
                }
            }
            is Category.All -> {
                getFinProducts(Category.Credits) +
                        getFinProducts(Category.Loans) +
                        getFinProducts(Category.Cards.Credit) +
                        getFinProducts(Category.Cards.Debit) +
                        getFinProducts(Category.Cards.Installment)
            }
        }
    }

    suspend fun writeMainData(responseMainData: ResponseMainData) {
        dataStore.edit {
            it[Constants.RESPONSE_MAIN_DATA] = Gson().toJson(responseMainData)
        }
    }
}
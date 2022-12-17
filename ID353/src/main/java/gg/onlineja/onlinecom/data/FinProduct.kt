package gg.onlineja.onlinecom.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


data class FinProduct(
    val name: String,
    val score: String,
    val description: String,
    val itemId: String,
    //val orderButtonText: String?,

    val percentPrefix: String,
    val percent: String,
    val percentPostfix: String,

    val summPrefix: String,
    val summMin: String,
    val summMid: String,
    val summMax: String,
    val summPostfix: String,

    val show_mastercard: Int,
    val show_mir: Int,
    val show_visa: Int,
    val show_qiwi: Int,
    val show_yandex: Int,
    val show_cash: Int,

    val screen: String,
    val order: String,
): Serializable
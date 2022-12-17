package gg.onlineja.onlinecom.utils

/*
import android.app.ActionBar
import android.text.PrecomputedText
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toolbar
import com.squareup.picasso.Picasso
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.data.FinProduct
import kotlin.reflect.KProperty1

object CommonUtils {

    fun addNecessaryCards(layout: LinearLayout, finproduct: FinProduct) {
        fun addCards() {
            with(layout) {
                val masterCard = ImageView(context)
                val mirCard = ImageView(context)
                val visaCard = ImageView(context)
                val qiwiCard = ImageView(context)
                val yandexMoney = ImageView(context)
                val cashCard = ImageView(context)

                masterCard.setImageResource(R.drawable.ic_master_card)
                mirCard.setImageResource(R.drawable.ic_mir_card)
                visaCard.setImageResource(R.drawable.ic_visa_card)
                qiwiCard.setImageResource(R.drawable.ic_qiwi_card)
                yandexMoney.setImageResource(R.drawable.ic_yandex_money)
                cashCard.setImageResource(R.drawable.ic_cash_card)

                if (finproduct.show_mastercard == 1) {
                    addView(masterCard)
                    masterCard.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
                if (finproduct.show_mir == 1) {
                    addView(mirCard)
                    mirCard.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
                if (finproduct.show_visa == 1) {
                    addView(visaCard)
                    visaCard.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
                if (finproduct.show_qiwi == 1) {
                    addView(qiwiCard)
                    qiwiCard.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
                if (finproduct.show_yandex == 1) {
                    addView(yandexMoney)
                    yandexMoney.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
                if (finproduct.show_cash == 1) {
                    addView(cashCard)
                    cashCard.layoutParams =
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1f)
                }
            }
        }

        println("FINPRODUCT: $finproduct")
        if(layout.childCount == 0) addCards()
        else {
            layout.removeAllViews()
            addCards()
        }

    }

    fun loadImageIntoView(url: String, imageView: ImageView) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.empty_image)
            .error(R.drawable.ic_no_connection)
            .into(imageView)
    }

}

 */
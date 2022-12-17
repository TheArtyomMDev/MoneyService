package gg.onlineja.onlinecom.ui.cardtabs

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.data.FinProduct
import gg.onlineja.onlinecom.data.repository.FinProductsRepository
import gg.onlineja.onlinecom.ui.cardtabs.components.Chip
import gg.onlineja.onlinecom.ui.categories.Category
import gg.onlineja.onlinecom.ui.destinations.DetailsScreenDestination
import gg.onlineja.onlinecom.ui.theme.*
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun CardsTabsScreen(
    navigator: DestinationsNavigator,
) {
    val finProductsRepository: FinProductsRepository = get()

    val creditCards = remember {
        mutableStateOf(arrayOf<FinProduct>())
    }

    val debitCards = remember {
        mutableStateOf(arrayOf<FinProduct>())
    }

    val installmentCards = remember {
        mutableStateOf(arrayOf<FinProduct>())
    }


    LaunchedEffect(Unit) {
        creditCards.value = finProductsRepository.getFinProducts(Category.Cards.Credit)
        debitCards.value = finProductsRepository.getFinProducts(Category.Cards.Debit)
        installmentCards.value = finProductsRepository.getFinProducts(Category.Cards.Installment)
    }

    Scaffold(
        containerColor = Color.Transparent
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.background_2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(Alignment.Bottom)
                    .align(Alignment.BottomCenter)
            )

            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = RootDimen, top = RootDimen, end = RootDimen),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Какая карта нужна?",
                        style = Typography.titleLarge,
                        modifier = Modifier.widthIn(max = 280.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.close_button),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navigator.popBackStack()
                            }
                    )
                }

                Spacer(Modifier.height(LargeDimen))

                Box(
                    Modifier
                        //.height(IntrinsicSize.Min)
                        .fillMaxWidth()
                        .padding(horizontal = RootDimen)
                        .height(220.dp)
                ) {

                        if (debitCards.value.isNotEmpty())
                            Chip(
                                "Дебетовая"
                            ) {
                                navigator.navigate(DetailsScreenDestination(0, Category.Cards.Debit))
                            }

                        if (creditCards.value.isNotEmpty())
                            Chip(
                                "Кредитная",
                                Modifier
                                    .align(Alignment.TopCenter)
                                    .offset(y = (70.dp))
                            ) {
                                navigator.navigate(DetailsScreenDestination(0,
                                    Category.Cards.Credit))
                            }

                        if (installmentCards.value.isNotEmpty())
                            Chip(
                                "Рассрочка",
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(y = (140.dp))
                            ) {
                                navigator.navigate(DetailsScreenDestination(0,
                                    Category.Cards.Installment))
                            }

                }

                Box(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(R.drawable.cards_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )

                    Column(
                        Modifier.padding(RootDimen)
                    ) {
                        val offers = mapOf(
                            "Дебетовые карты" to debitCards.value,
                            "Кредитные карты" to creditCards.value,
                            "Рассрочка" to installmentCards.value
                        )
                        for (name in offers.keys) {
                            val count = offers[name]?.size ?: 0
                            Text(
                                name,
                                fontWeight = FontWeight.Light,
                                style = Typography.bodySmall
                            )
                            Text(
                                "$count ${getWord(count)}",
                                style = Typography.bodySmall
                            )

                            Spacer(Modifier.height(MediumDimen))
                        }
                    }
                }
            }
        }
    }
}

fun getWord(count: Int): String {
    val lastDigits = count % 100
    return when(count) {
        0 -> "предложений"
        in 11..20 -> "предложений"
        else -> when(lastDigits % 10) {
            1 -> "предложение"
            in 2..4 -> "предложения"
            else -> "предложений"
        }
    }
}
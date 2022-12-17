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

            Column(
                Modifier.padding(RootDimen)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Какая карта нужна?",
                        fontSize = Typography.titleLarge.fontSize,
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

                if (debitCards.value.isNotEmpty())
                    Chip("Дебетовая") {
                        navigator.navigate(DetailsScreenDestination(0, Category.Cards.Debit))
                    }

                Spacer(Modifier.height(SmallDimen))

                if (creditCards.value.isNotEmpty())
                    Chip("Кредитная", Modifier.align(Alignment.CenterHorizontally)) {
                        navigator.navigate(DetailsScreenDestination(0, Category.Cards.Credit))
                    }

                Spacer(Modifier.height(SmallDimen))

                if (installmentCards.value.isNotEmpty())
                    Chip("Рассрочка", Modifier.align(Alignment.End)) {
                        navigator.navigate(DetailsScreenDestination(0, Category.Cards.Installment))
                    }

                Spacer(Modifier.height(LargeDimen))

                //with(parentScope) {
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
                                .height(400.dp)
                                //.wrapContentHeight(Alignment.Bottom)
                                .align(Alignment.Center)
                        )


                        /*
                    finProducts.value?.apply {
                        Column(
                            Modifier.padding(RootDimen)
                        ) {
                            Text(
                                "Сумма",
                                fontWeight = FontWeight.Light,
                                style = Typography.bodySmall
                            )
                            Text(
                                "$summPrefix $summMin $summMid $summMax $summPostfix",
                                style = Typography.bodySmall
                            )

                            Spacer(Modifier.height(MediumDimen))

                            Text(
                                "Срок",
                                fontWeight = FontWeight.Light,
                                style = Typography.bodySmall
                            )
                            Text(
                                "$percentPrefix $percent $percentPostfix",
                                style = Typography.bodySmall
                            )

                            Spacer(Modifier.height(MediumDimen))

                            Text(
                                "Рейтинг пользователей",
                                fontWeight = FontWeight.Light,
                                style = Typography.bodySmall
                            )

                            RatingBar(score.toDouble().toInt())

                            Spacer(Modifier.height(MediumDimen))

                            Text(
                                "Получение",
                                fontWeight = FontWeight.Light,
                                style = Typography.bodySmall
                            )

                            val isAvailable = listOf(show_visa, show_mastercard, show_mir, show_qiwi,  show_cash)
                                .map { it == 1 }
                            val pictures = listOf(
                                R.drawable.ic_visa,
                                R.drawable.ic_mastercard,
                                R.drawable.ic_mir,
                                R.drawable.ic_qiwi,
                                R.drawable.ic_wallet
                            ).map { painterResource(it) }
                            AvailableCardsRow(isAvailable, pictures)
                        }
                    }


                     */
                    }
                }




        }
    }
}
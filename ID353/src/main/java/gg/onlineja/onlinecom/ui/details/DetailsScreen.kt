package gg.onlineja.onlinecom.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.data.FinProduct
import gg.onlineja.onlinecom.data.repository.FinProductsRepository
import gg.onlineja.onlinecom.ui.destinations.DetailsScreenDestination
import gg.onlineja.onlinecom.ui.destinations.WebViewDestination
import gg.onlineja.onlinecom.ui.details.components.AvailableCardsRow
import gg.onlineja.onlinecom.ui.details.components.RatingBar
import gg.onlineja.onlinecom.ui.theme.*
import gg.onlineja.onlinecom.utils.LinkUtils
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@RootNavGraph
@Destination(
    navArgsDelegate = DetailsScreenNavArgs::class
)
@Composable
fun DetailsScreen(
    navigator: DestinationsNavigator,
    navArgs: DetailsScreenNavArgs,
) {
    val finProductsRepository: FinProductsRepository = get()
    val dataStore: DataStore<Preferences> = get()

    val finProducts = remember {
        mutableStateOf(arrayOf<FinProduct>())
    }
    val finProduct = remember {
        mutableStateOf<FinProduct?>(null)
    }

    LaunchedEffect(Unit) {
        finProducts.value = finProductsRepository.getFinProducts(navArgs.Category)
        finProduct.value = finProducts.value[navArgs.id]
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
                            finProduct.value?.name ?: "",
                            style = Typography.titleLarge,
                            maxLines = 2,
                            modifier = Modifier
                                .widthIn(max = 250.dp),
                            overflow = TextOverflow.Ellipsis,
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

                    val columnScope = this@Column
                    Box(Modifier.padding(horizontal = LargeDimen)) {
                        Image(
                            painter = painterResource(R.drawable.card_background),
                            contentDescription = null,
                            modifier = Modifier
                                .matchParentSize()
                                .offset(x = 20.dp, y = 30.dp)
                        )

                        with(columnScope) {

                            GlideImage(
                                model = finProduct.value?.screen,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .clip(roundLarge)
                                    .background(Color.White, shape = roundLarge)
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .border(4.dp, BrightBlue, shape = roundLarge)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        runBlocking {
                                            navigator.navigate(
                                                WebViewDestination(
                                                    url = LinkUtils.generateUrlAndTrack(
                                                        finProduct.value!!,
                                                        dataStore,
                                                        "more_details"
                                                    ),
                                                    title = finProduct.value?.name ?: ""
                                                )
                                            )
                                        }
                                    }
                            )
                        }
                    }
                }

                val parentScope = this@Box
                Box(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    with(parentScope) {
                        Image(
                            painter = painterResource(R.drawable.background_info),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                //.wrapContentHeight(Alignment.Bottom)
                                .align(Alignment.BottomCenter)
                        )

                        Image(
                            painter = painterResource(R.drawable.get_money_button),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .width(100.dp)
                                .height(100.dp)
                                .align(Alignment.BottomCenter)
                                .clickable {
                                    runBlocking {
                                        navigator.navigate(
                                            WebViewDestination(
                                                url = LinkUtils.generateUrlAndTrack(
                                                    finProduct.value!!,
                                                    dataStore,
                                                    "more_details"
                                                ),
                                                title = finProduct.value?.name ?: ""
                                            )
                                        )
                                    }
                                }
                        )
                    }

                    if (navArgs.id - 1 >= 0)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.BottomStart)
                                .padding(RootDimen)
                                .clickable {
                                    navigator.popBackStack()
                                    navigator.navigate(DetailsScreenDestination(navArgs.id - 1,
                                        navArgs.Category))
                                }
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                "Назад",
                                style = Typography.bodyMedium,
                                color = Color.White
                            )
                        }

                    if (navArgs.id + 1 < finProducts.value.size)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(RootDimen)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    navigator.popBackStack()
                                    navigator.navigate(DetailsScreenDestination(navArgs.id + 1,
                                        navArgs.Category))
                                }
                        ) {
                            Text(
                                "Далее",
                                style = Typography.bodyMedium,
                                color = Color.White
                            )
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                    finProduct.value?.apply {
                        Column(
                            Modifier.padding(RootDimen)
                        ) {
                            if((summPrefix + summMin + summMid + summMax + summPostfix).isNotEmpty()) {
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
                            }


                            if((percentPrefix + percent + percentPostfix).isNotEmpty() && hide_PercentFields != 1) {
                                Text(
                                    "Ставка",
                                    fontWeight = FontWeight.Light,
                                    style = Typography.bodySmall
                                )
                                Text(
                                    "$percentPrefix $percent $percentPostfix",
                                    style = Typography.bodySmall
                                )
                                Spacer(Modifier.height(MediumDimen))
                            }


                            if(score.isDigitsOnly() && score.isNotEmpty()) {
                                Text(
                                    "Рейтинг пользователей",
                                    fontWeight = FontWeight.Light,
                                    style = Typography.bodySmall
                                )
                                RatingBar(score.toDouble().toInt())
                                Spacer(Modifier.height(MediumDimen))
                            }

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

                }
            }
        }
}
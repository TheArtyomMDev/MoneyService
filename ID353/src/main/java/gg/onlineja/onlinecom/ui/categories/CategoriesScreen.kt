package gg.onlineja.onlinecom.ui.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.data.ResponseMainData
import gg.onlineja.onlinecom.data.repository.FinProductsRepository
import gg.onlineja.onlinecom.ui.destinations.CardsTabsScreenDestination
import gg.onlineja.onlinecom.ui.destinations.DetailsScreenDestination
import gg.onlineja.onlinecom.ui.theme.MediumDimen
import gg.onlineja.onlinecom.ui.theme.RootDimen
import gg.onlineja.onlinecom.ui.theme.Typography
import org.koin.androidx.compose.get

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun CategoriesScreen(
    navigator: DestinationsNavigator,
    responseMainData: ResponseMainData
) {
    val finProductsRepository: FinProductsRepository = get()

    LaunchedEffect(key1 = Unit) {
        finProductsRepository.writeMainData(responseMainData)
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
                        "Категории",
                        style = Typography.titleLarge,
                    )
                    Image(
                        painter = painterResource(R.drawable.stack_menu_button),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(Modifier.height(2 * RootDimen))

                val categories = mapOf(
                    Category.Loans to painterResource(R.drawable.loans_category),
                    Category.Cards.Credit to painterResource(R.drawable.cards_category),
                    Category.Credits to painterResource(R.drawable.credits_category),
                    Category.All to painterResource(R.drawable.all_category)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    userScrollEnabled = false
                ) {
                    items(categories.keys.toList()) { category ->
                        Image(
                            painter = categories[category]!!,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MediumDimen)
                                .clickable {
                                    when (category) {
                                        is Category.Loans, Category.All, Category.Credits -> {
                                            navigator.navigate(
                                                DetailsScreenDestination(0, category)
                                            )
                                        }
                                        is Category.Cards -> {
                                            navigator.navigate(CardsTabsScreenDestination)
                                        }
                                    }
                                }
                        )
                    }
                }

                Spacer(Modifier.height(MediumDimen))

                Text(
                    "Рекомендуем подавать сразу 3-4 заявки, чтобы вам гарантированно одобрили займ, кредит или карту!",
                    style = Typography.bodySmall,
                    modifier = Modifier.padding(horizontal = MediumDimen)
                )

            }
        }
    }
}
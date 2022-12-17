package gg.onlineja.onlinecom.ui.laststep

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.ui.destinations.HelloScreenDestination
import gg.onlineja.onlinecom.ui.destinations.ThankScreenDestination
import gg.onlineja.onlinecom.ui.faketabs.FakeTabsNavGraph
import gg.onlineja.onlinecom.ui.theme.*
import gg.onlineja.onlinecom.ui.enterphone.components.Chip
import gg.onlineja.onlinecom.ui.hello.components.ProceedButton

@OptIn(ExperimentalMaterial3Api::class)
@FakeTabsNavGraph
@Destination
@Composable
fun LastStepScreen(
    navigator: DestinationsNavigator,
    phone: String,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(RootDimen)
        ) {

            Text(
                "Последний шаг",
                style = Typography.titleLarge,
                color = White
            )

            Spacer(Modifier.height(RootDimen))

            Row {
                Chip("Не судим", false)
                Spacer(Modifier.width(SmallDimen))
                Chip("Нет ипотеки", false)
            }
            Chip("Есть официальная работа", false)
            Chip("Нет активных долгов", false)

            Spacer(Modifier.height(RootDimen))

            TextField(
                value = phone,
                onValueChange = {},
                label = { Text("Номер телефона") },
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = White,
                    disabledLabelColor = White,
                    containerColor = Transparent,
                )
            )

            Spacer(Modifier.height(RootDimen))
        }

        ProceedButton(this) {
            navigator.navigate(ThankScreenDestination)
        }
    }

}
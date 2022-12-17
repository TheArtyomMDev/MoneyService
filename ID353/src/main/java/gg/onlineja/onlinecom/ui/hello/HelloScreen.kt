package gg.onlineja.onlinecom.ui.hello

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.destinations.EnterPhoneScreenDestination
import gg.onlineja.onlinecom.ui.enterphone.EnterPhoneScreen
import gg.onlineja.onlinecom.ui.faketabs.FakeTabsNavGraph
import gg.onlineja.onlinecom.ui.hello.components.ProceedButton
import gg.onlineja.onlinecom.ui.hello.components.RadioGroup
import gg.onlineja.onlinecom.ui.theme.GrayBlue
import gg.onlineja.onlinecom.ui.theme.LightBlue
import gg.onlineja.onlinecom.ui.theme.RootDimen
import gg.onlineja.onlinecom.ui.theme.Typography

@FakeTabsNavGraph(start = true)
@Destination
@Composable
fun HelloScreen(
    navigator: DestinationsNavigator
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(RootDimen)
        ) {
            Text(
                "Привет!",
                style = Typography.titleLarge,
            )
            Spacer(Modifier.height(RootDimen))
            Text(
                "Не будем ходить вокруг да около,\n" + "какая сумма нужна?",
                style = Typography.bodyMedium
            )

            val progress = remember { mutableStateOf(0F) }
            Slider(
                value = progress.value,
                onValueChange = {
                    progress.value = it
                },
                colors = SliderDefaults.colors(
                    thumbColor = LightBlue,
                    activeTrackColor = LightBlue,
                    inactiveTrackColor = GrayBlue
                )
            )

            Text(
                "${(progress.value * 100000).toInt()} руб.",
                style = Typography.bodyMedium,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(Modifier.height(RootDimen))

            Text(
                "Случались ли просрочки по кредитам?",
                style = Typography.bodyMedium
            )

            RadioGroup(listOf("Да", "Нет"))

            Spacer(Modifier.height(RootDimen))

            Text(
                "Вам больше всего интересен:",
                style = Typography.bodyMedium
            )

            RadioGroup(listOf("Займ", "Кредитная карта", "Потребительский кредит"))
        }
        ProceedButton(this) {
            navigator.navigate(EnterPhoneScreenDestination)
        }
    }

}
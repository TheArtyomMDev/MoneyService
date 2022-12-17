package gg.onlineja.onlinecom.ui.thank

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.text.isDigitsOnly
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.destinations.HelloScreenDestination
import gg.onlineja.onlinecom.ui.faketabs.FakeTabsNavGraph
import gg.onlineja.onlinecom.ui.theme.*
import gg.onlineja.onlinecom.ui.enterphone.components.Chip
import gg.onlineja.onlinecom.ui.hello.components.ProceedButton

@OptIn(ExperimentalMaterial3Api::class)
@FakeTabsNavGraph
@Destination
@Composable
fun ThankScreen(
    navigator: DestinationsNavigator,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(RootDimen)
        ) {

            Text(
                "Отлично!",
                style = Typography.titleLarge,
                color = White
            )

            Spacer(Modifier.height(RootDimen))

            Text(
                "Запрос отправлен, менеджер скоро свяжется с вами для уточнения деталей, спасибо за использование нашего приложения!",
                style = Typography.bodyMedium,
                color = White
            )

            Image(
                painter = painterResource(R.drawable.hourglass),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.End)
            )
        }

        Button(
            onClick = {
                navigator.navigate(HelloScreenDestination)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue
            ),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(bottom = 25.dp, start = RootDimen, end = RootDimen)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                "В меню",
                style = Typography.bodyLarge,
                color = White,
            )
        }
    }

}
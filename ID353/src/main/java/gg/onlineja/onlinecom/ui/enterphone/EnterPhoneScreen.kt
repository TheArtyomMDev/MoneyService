package gg.onlineja.onlinecom.ui.enterphone

import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.ui.destinations.HelloScreenDestination
import gg.onlineja.onlinecom.ui.destinations.LastStepScreenDestination
import gg.onlineja.onlinecom.ui.faketabs.FakeTabsNavGraph
import gg.onlineja.onlinecom.ui.hello.components.ProceedButton
import gg.onlineja.onlinecom.ui.hello.components.RadioGroup
import gg.onlineja.onlinecom.ui.theme.*
import gg.onlineja.onlinecom.ui.enterphone.components.Chip

@OptIn(ExperimentalMaterial3Api::class)
@FakeTabsNavGraph
@Destination
@Composable
fun EnterPhoneScreen(
    navigator: DestinationsNavigator
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier.padding(RootDimen)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable {
                        navigator.navigate(HelloScreenDestination)
                    }
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    "Назад",
                    style = Typography.titleLarge,
                    color = White
                )
            }

            Spacer(Modifier.height(RootDimen))

            Row {
                Chip("Не судим")
                Spacer(Modifier.width(SmallDimen))
                Chip("Нет ипотеки")
            }
            Chip("Есть официальная работа")
            Chip("Нет активных долгов")

            Spacer(Modifier.height(RootDimen))

            val formattedPhone = remember { mutableStateOf("") }
            val phone = remember { mutableStateOf("") }
            TextField(
                value = formattedPhone.value,
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

            val numButtons = listOf(
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                ",", "0", "."
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = RootDimen),
            ) {
                items(numButtons) { item ->
                    Button(
                        onClick = {
                            if(item.isDigitsOnly() && phone.value.length < 10) phone.value += item
                            formattedPhone.value = formatPhone(phone.value)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = White
                        ),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(horizontal =  3.dp)
                    ) {
                        Text(
                            item,
                            style = Typography.bodyMedium,
                            color = Black
                        )
                    }
                }
            }

            Button(
                onClick = {
                    phone.value = phone.value.dropLast(1)
                    formattedPhone.value = formatPhone(phone.value)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = White
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(horizontal = RootDimen + 3.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Backspace",
                    tint = Black
                )
            }

            Button(
                onClick = {
                    navigator.navigate(LastStepScreenDestination(formattedPhone.value))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkBlue
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(horizontal = RootDimen + 3.dp)
                    .fillMaxWidth(),
                enabled = phone.value.length == 10
            ) {
                Text(
                    "Подтвердить",
                    style = Typography.bodyLarge,
                    color = White
                )
            }
        }
    }


}

fun formatPhone(phone: String): String {
    var mPhone = phone

    if (mPhone.isEmpty()) return ""

    if (mPhone.length < 10) {
        mPhone += "*".repeat(10 - mPhone.length)
    }

    return "+7 (${mPhone.substring(0, 3)}) ${mPhone.substring(3, 6)}-${mPhone.substring(6, 8)}-${mPhone.substring(8)}"
}
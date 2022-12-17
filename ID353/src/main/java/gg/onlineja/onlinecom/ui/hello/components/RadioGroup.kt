package gg.onlineja.onlinecom.ui.hello.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import gg.onlineja.onlinecom.ui.theme.LightBlue
import gg.onlineja.onlinecom.ui.theme.Typography

@Composable
fun RadioGroup(textFields: List<String>) {
    Column {
        val selectedId = remember { mutableStateOf(0) }
        for (id in textFields.indices) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedId.value = id
                    }
            ) {
                RadioButton(
                    selected = selectedId.value == id,
                    onClick = { selectedId.value = id },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = LightBlue,
                        unselectedColor = Color.White
                    )
                )
                Text(
                    textFields[id],
                    style = Typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
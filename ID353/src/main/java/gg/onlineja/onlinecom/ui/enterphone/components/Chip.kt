package gg.onlineja.onlinecom.ui.enterphone.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import gg.onlineja.onlinecom.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(text: String, hideOnClick: Boolean = true) {
    val isShown = remember {
        mutableStateOf(true)
    }

    if (isShown.value || !hideOnClick) {
        AssistChip(
            onClick = { isShown.value = !isShown.value },
            label = {
                Text(
                    text,
                    style = Typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(vertical = SmallDimen)
                )
            },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = BrightBlue
            ),
            trailingIcon = {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize + SmallDimen),
                    tint = LightBlue2
                )
            }
        )
    }
}
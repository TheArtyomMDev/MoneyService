package gg.onlineja.onlinecom.ui.cardtabs.components

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
fun Chip(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    AssistChip(
        onClick = { onClick() },
        label = {
            Text(
                text,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(vertical = SmallDimen)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = BrightBlue
        ),
        modifier = modifier
    )
}
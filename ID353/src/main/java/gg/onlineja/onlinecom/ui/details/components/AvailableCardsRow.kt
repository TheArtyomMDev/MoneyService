package gg.onlineja.onlinecom.ui.details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gg.onlineja.onlinecom.R

@Composable
fun AvailableCardsRow(
    isAvailable: List<Boolean>,
    pictures: List<Painter>
) {
    Row {
        for (i in isAvailable.indices) {
            if (isAvailable[i]) {
                Image(
                    painter = pictures[i],
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
                Spacer(Modifier.width(8.dp))
            }
        }
    }

}
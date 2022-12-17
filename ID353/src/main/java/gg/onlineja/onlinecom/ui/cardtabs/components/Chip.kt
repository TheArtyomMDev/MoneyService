package gg.onlineja.onlinecom.ui.cardtabs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(R.drawable.chip),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.TopCenter)
        )
        Text(
            text = text,
            style = Typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = SmallDimen, horizontal = MediumDimen)
        )
    }
}
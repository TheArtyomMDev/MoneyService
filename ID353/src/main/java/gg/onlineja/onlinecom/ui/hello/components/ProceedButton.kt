package gg.onlineja.onlinecom.ui.hello.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gg.onlineja.onlinecom.R

@Composable
fun ProceedButton(boxScope: BoxScope, onClick : () -> Unit) {
    boxScope.apply {
        Image(
            painter = painterResource(id = R.drawable.proceed_button_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(R.drawable.proceed_button),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .width(100.dp)
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .clickable {
                    onClick()
                }
        )
    }
}
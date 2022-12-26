package gg.onlineja.onlinecom.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.splash.DeepLinkArgs
import gg.onlineja.onlinecom.ui.splash.toCategory
import gg.onlineja.onlinecom.ui.theme.DarkBlue
import gg.onlineja.onlinecom.ui.theme.MoneyServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val link = intent.extras?.getString("link")
        val screen = link?.split("/")?.get(0)
        val id = link?.split("/")?.get(1)?.toInt()


        setContent {
            MoneyServiceTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    rememberSystemUiController().setStatusBarColor(
                        color = DarkBlue,
                    )

                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(DeepLinkArgs(screen?.toCategory(), id))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyServiceTheme {}
}
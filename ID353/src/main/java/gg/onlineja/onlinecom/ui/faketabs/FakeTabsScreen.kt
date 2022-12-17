package gg.onlineja.onlinecom.ui.faketabs

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import gg.onlineja.onlinecom.ui.NavGraphs

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph
@Destination
@Composable
fun FakeTabsScreen() {
    DestinationsNavHost(navGraph = NavGraphs.fakeTabs)
}

@NavGraph
annotation class FakeTabsNavGraph(
    val start: Boolean = false
)
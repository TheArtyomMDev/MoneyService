package gg.onlineja.onlinecom.ui.webview

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.FileProvider
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.BuildConfig
import gg.onlineja.onlinecom.R
import gg.onlineja.onlinecom.ui.theme.DarkBlue
import gg.onlineja.onlinecom.ui.theme.RootDimen
import gg.onlineja.onlinecom.ui.theme.SmallDimen
import gg.onlineja.onlinecom.ui.theme.Typography
import java.io.File


private fun getTmpFileUri(context: Context): Uri {
    val tmpFile = File.createTempFile("tmp_image_file", ".png", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }

    return FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
}

@RootNavGraph
@Destination
@Composable
fun WebView(
    navigator: DestinationsNavigator,
    url: String? = null,
    content: String? = null,
    title: String
) {

    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    var curRequest: PermissionRequest? = null
    var myFilePathCallback by remember {
        mutableStateOf<ValueCallback<Array<Uri>>?>(null)
    }
    var uri: Uri? = null
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val showDialog = remember { mutableStateOf(false) }
    val showProgress = remember { mutableStateOf(false) }

    val getPictureFromCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it) {
            if(uri != null)
                myFilePathCallback?.onReceiveValue(arrayOf(uri!!))
        }
    }

    val getImageFromGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { curUri: Uri? ->
        if(curUri != null)
            myFilePathCallback?.onReceiveValue(arrayOf(curUri))
    }

    val permissionForCameraApp =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    granted -> {
                        uri = getTmpFileUri(context)
                        getPictureFromCamera.launch(uri)
                    }
                    !shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                        Toast.makeText(context, "Access to camera denied", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(context, "Access to camera denied", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    if(showDialog.value)
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("Get picture from")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        getImageFromGallery.launch("image/*")
                    },
                ) {
                    Text("From gallery")
                }
                Button(
                    onClick = {
                        showDialog.value = false
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(activity,
                                    Manifest.permission.CAMERA)
                            ) {
                                uri = getTmpFileUri(context)
                                println("MY URI IS : $uri")
                                getPictureFromCamera.launch(uri)
                            } else {
                                permissionForCameraApp.launch(Manifest.permission.CAMERA)
                            }
                        } else {
                            uri = getTmpFileUri(context)
                            println("MY URI IS : $uri")
                            getPictureFromCamera.launch(uri)
                        }
                    },
                ) {
                    Text("From camera")
                }
                Button(
                    onClick = {
                        showDialog.value = false
                        getImageFromGallery.launch("*/*")
                    },
                ) {
                    Text("File from explorer")
                }
            }
        )

    val cameraPermissionForDirect =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    granted -> {
                        curRequest?.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                    }
                    !shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA) -> {
                        Toast.makeText(context, "Access to camera denied", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(context, "Access to camera denied", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    val mWebChromeClient = object: WebChromeClient() {
        override fun onPermissionRequest(request: PermissionRequest?) {
            curRequest = request

            request?.resources?.forEach { r ->
                if(r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cameraPermissionForDirect.launch(Manifest.permission.CAMERA)
                    }
                    else {
                        request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                    }
                }
            }

        }
        override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
            myFilePathCallback = filePathCallback
            showDialog.value = true
            return true
        }
    }

    val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(
            view: WebView,
            url: String?,
            favicon: Bitmap?
        ) {
            backEnabled = view.canGoBack()
            showProgress.value = true
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            showProgress.value = false
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
            showProgress.value = false
            view?.loadUrl("file:///android_asset/error.html")
        }
    }

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = RootDimen, top = RootDimen, end = RootDimen),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                style = Typography.titleSmall,
                modifier = Modifier.widthIn(max = 280.dp)
            )
            Image(
                painter = painterResource(R.drawable.close_button),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navigator.popBackStack()
                    }
            )
        }

        Spacer(Modifier.height(SmallDimen))

        Box(Modifier.fillMaxSize()) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setDownloadListener { url, s2, s3, s4, l ->
                            println("Started downloading")
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            context.startActivity(i)
                        }
                        webViewClient = mWebViewClient
                        webChromeClient = mWebChromeClient
                        settings.apply {
                            javaScriptEnabled = true
                            javaScriptCanOpenWindowsAutomatically = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            builtInZoomControls = true
                            displayZoomControls = false
                            setSupportZoom(true)
                            javaScriptCanOpenWindowsAutomatically = true
                            defaultTextEncodingName = "utf-8"
                        }
                        if(url != null) {
                            loadUrl(url)
                        }
                        else if(content != null) {
                            loadData(content, "text/html", "UTF-8")
                        }
                    }
                },
                update = {
                    webView = it
                },
                modifier = Modifier.fillMaxSize()
            )
            if(showProgress.value)
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp),
                    color = DarkBlue
                )
        }
    }


    BackHandler() {
        if(backEnabled) webView?.goBack()
    }
}
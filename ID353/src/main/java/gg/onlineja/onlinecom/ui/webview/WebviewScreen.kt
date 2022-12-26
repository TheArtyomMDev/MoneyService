package gg.onlineja.onlinecom.ui.webview

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import gg.onlineja.onlinecom.BuildConfig
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
    val curRequest = remember {
        mutableStateOf<PermissionRequest?>(null)
    }
    var myFilePathCallback by remember {
        mutableStateOf<ValueCallback<Array<Uri>>?>(null)
    }
    val uri = remember {
        mutableStateOf<Uri?>(null)
    }
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val showDialog = remember { mutableStateOf(false) }
    val showProgress = remember { mutableStateOf(false) }

    val getPictureFromCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it) {
            if(uri.value != null)
                myFilePathCallback?.onReceiveValue(arrayOf(uri.value!!))
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
                        uri.value = getTmpFileUri(context)
                        getPictureFromCamera.launch(uri.value)
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
                Text("Получить картинку")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        getImageFromGallery.launch("image/*")
                    },
                ) {
                    Text("С галереи")
                }
                Button(
                    onClick = {
                        showDialog.value = false
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(activity,
                                    Manifest.permission.CAMERA)
                            ) {
                                uri.value = getTmpFileUri(context)
                                getPictureFromCamera.launch(uri.value)
                            } else {
                                permissionForCameraApp.launch(Manifest.permission.CAMERA)
                            }
                        } else {
                            uri.value = getTmpFileUri(context)
                            println("MY URI IS : $uri")
                            getPictureFromCamera.launch(uri.value)
                        }
                    },
                ) {
                    Text("С камеры")
                }
            }
        )

    val cameraPermissionForDirect =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when {
                    granted -> {
                        curRequest.value?.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                        println(curRequest)
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
            curRequest.value = request

            request?.resources?.forEach { r ->
                if(r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    ) {
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
                .clickable {
                    if(backEnabled) webView?.goBack()
                    else navigator.popBackStack()
                }
                .padding(start = RootDimen, top = RootDimen, end = RootDimen),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                "Назад",
                style = Typography.titleSmall,
                modifier = Modifier.widthIn(max = 280.dp)
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
                        setDownloadListener { url, _, _, _, _ ->
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


    BackHandler {
        if(backEnabled) webView?.goBack()
        else navigator.popBackStack()
    }
}
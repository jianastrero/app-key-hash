package dev.jianastrero.appkeyhash.model

import android.content.pm.ApplicationInfo
import androidx.compose.ui.graphics.ImageBitmap

data class App(
    val applicationInfo: ApplicationInfo,
    val iconImageBitmap: ImageBitmap,
    val appName: String,
)

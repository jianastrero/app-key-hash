package dev.jianastrero.appkeyhash.use_case

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.jianastrero.appkeyhash.model.App
import javax.inject.Inject

class GetAllAppsUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke() =
        context.packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
            .map { packageInfo ->
                val appInfo = packageInfo.applicationInfo
                val appName = appInfo.loadLabel(context.packageManager).toString()
                val iconImageBitmap = appInfo.loadIcon(context.packageManager).toBitmap().asImageBitmap()
                App(
                    applicationInfo = appInfo,
                    iconImageBitmap = iconImageBitmap,
                    appName = appName,
                )
            }
            .sortedBy { it.appName }
}

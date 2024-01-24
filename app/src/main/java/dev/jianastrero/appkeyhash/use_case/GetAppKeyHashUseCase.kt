package dev.jianastrero.appkeyhash.use_case

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.MessageDigest
import javax.inject.Inject


class GetAppKeyHashUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(packageName: String): String? {
        try {
            val info: PackageInfo = context.packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())

                val keyHash: String = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                return keyHash
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return null
    }
}

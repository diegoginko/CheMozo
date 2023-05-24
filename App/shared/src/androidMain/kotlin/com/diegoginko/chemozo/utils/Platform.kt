package com.diegoginko.chemozo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

actual class Platform(context: Context) {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    @SuppressLint("HardwareIds")
    actual val deviceId: String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}
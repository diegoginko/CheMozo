package com.diegoginko.chemozo.utils

import platform.UIKit.UIDevice

actual class Platform {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    actual val deviceId: String
        get() = UIDevice.currentDevice.identifierForVendor.toString()// .systemName() + " " + UIDevice.currentDevice.systemVersion
}
package com.vishalrandive.wishlib

import android.graphics.drawable.Drawable

data class AppInfoModel(
    val appName: String,
    val packageName: String,
    val appIcon: Drawable,
    val launcherActivity: String,
    val versionCode: Int,
    val versionName: String
)  {
//    override fun toString(): String {
//        return "appName= $appName, packageName= $packageName, launcherActivity= $launcherActivity, versionCode=$versionCode, versionName= $versionName";
//    }
}

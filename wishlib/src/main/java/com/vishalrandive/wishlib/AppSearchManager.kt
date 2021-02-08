package com.vishalrandive.wishlib

import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

object AppSearchManager {

    init {
        println("vishal's singleton class .")
    }

    fun getListOfInstalledApp(context: ContextWrapper) : ArrayList<AppInfoModel>{

        val packageManager = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val appList: ArrayList<AppInfoModel> = ArrayList();
        for (i in 0 until packageManager.size) {
            val applicationInfo: ApplicationInfo = packageManager[i]

            if (isSystemPackage(applicationInfo)) {
                continue;
            }
            val packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0)
            val mainActivityName: String? = context.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName)?.resolveActivity(context.getPackageManager())?.className

            val appInfoDataModel = AppInfoModel(
                appName = applicationInfo.loadLabel(context.getPackageManager()).toString(),
                packageName = applicationInfo.packageName,
                appIcon = applicationInfo.loadIcon(context.getPackageManager()),
                launcherActivity = mainActivityName!!,
                versionCode = packageInfo.versionCode,
                versionName = packageInfo.versionName
            )
            appList.add(appInfoDataModel)
        }
        //sort the installed app list
        appList.sortWith { o1, o2 -> o1.packageName!!.compareTo(o2.packageName!!) }
        return  appList

    }

    fun registerUninstallReceiver(context: Context, notifyUninstall : (Any) -> Unit){
        val uninstallApplication: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val packageName: String = Objects.requireNonNull(intent.data)!!.getEncodedSchemeSpecificPart()

                    Toast.makeText(context, "Wish uninstalled : $packageName", Toast.LENGTH_SHORT).show()
                    notifyUninstall(packageName)
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addDataScheme("package")
        context.registerReceiver(uninstallApplication, intentFilter)
    }

    fun registerInstallReceiver(context: Context, notifyInstall : (Any) -> Unit){
        val uninstallApplication: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val packageName: String = Objects.requireNonNull(intent.data)!!.getEncodedSchemeSpecificPart()
                    Toast.makeText(context, "Wish installed : $packageName", Toast.LENGTH_SHORT).show()
                    notifyInstall(packageName)
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addDataScheme("package")
        context.registerReceiver(uninstallApplication, intentFilter)
    }

    private fun isSystemPackage(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

}
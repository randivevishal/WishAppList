package com.vishalrandive.wishlauncher

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val TAG: String? = MainActivityViewModel::class.simpleName

    var isRefresh = MutableLiveData<Boolean>()

    fun notifyInstall(item: Any) {
        //do things related to activity
        System.out.println("notifyInstall $item");
        isRefresh.value = true
    }

    fun notifyUninstall(item: Any) {
        System.out.println("notifyUninstall $item");
        isRefresh.value = true
    }

}
package com.vishalrandive.wishlauncher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishalrandive.wishlauncher.databinding.ActivityMainBinding
import com.vishalrandive.wishlib.AppInfoModel
import com.vishalrandive.wishlib.AppSearchManager


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private val mBinding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.lifecycleOwner = this
        mBinding.viewModel = viewModel

        var appList: ArrayList<AppInfoModel> = AppSearchManager.getListOfInstalledApp(this)

        /**
         * init install and uninstall broadcast receivers
         */
        AppSearchManager.registerInstallReceiver(this) { item -> viewModel.notifyInstall(item) }
        AppSearchManager.registerUninstallReceiver(this) { item -> viewModel.notifyUninstall(item) }

        mBinding.rvAppList.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                LinearLayoutManager.HORIZONTAL
            )
        )
        val linearLayoutManager = LinearLayoutManager(baseContext)
        mBinding.rvAppList.layoutManager = linearLayoutManager

        val appListAdapter: AppListAdapter by lazy {
            AppListAdapter(appList).apply {
                onItemClicked {
                    val launchIntent = getPackageManager().getLaunchIntentForPackage(it.packageName)
                    startActivity(launchIntent)
                }
            }
        }
        mBinding.rvAppList.adapter = appListAdapter
        viewModel.isRefresh.observe(this, Observer {
            it?.let {
                appListAdapter.clear()
                appListAdapter.changeData(AppSearchManager.getListOfInstalledApp(this))
                appListAdapter.notifyDataSetChanged()
            }
        })
    }

}

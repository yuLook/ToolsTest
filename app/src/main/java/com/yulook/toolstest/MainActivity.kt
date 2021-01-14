package com.yulook.toolstest

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yulook.tools.base.BaseActivity
import com.yulook.tools.base.BaseViewModel
import com.yulook.tools.utils.ToastUtil.toast
import com.yulook.toolstest.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun initTitleBar(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {
        binding.tvToast.setOnClickListener {

            "Toast".toast()

        }
    }

}
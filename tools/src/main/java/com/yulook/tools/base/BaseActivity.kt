package com.yulook.tools.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.gyf.immersionbar.ktx.immersionBar
import com.yulook.tools.R

/**
 *  时间：2021/1/7-17:45
 *  com.yulook.tools.base BaseActivity
 *  描述：
 */
abstract class BaseActivity<E:ViewDataBinding> : AppCompatActivity() {

    lateinit var binding : E

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        val viewModel = getViewModel()
        binding.setVariable(BR.viewModel,viewModel)
        //感知生命周期
        lifecycle.addObserver(viewModel)
        binding.lifecycleOwner = this

        //设置沉浸式状态栏
        immersionBar{
            fitsSystemWindows(true)
            statusBarColor(R.color.yl_tools_white)
            navigationBarColor(R.color.yl_tools_white)
            statusBarDarkFont(true)
        }

        initTitleBar(savedInstanceState)
        initData(savedInstanceState)
    }

    abstract fun getLayoutId():Int

    abstract fun getViewModel():BaseViewModel

    abstract fun initTitleBar(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for(fragment in supportFragmentManager.fragments){
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

}
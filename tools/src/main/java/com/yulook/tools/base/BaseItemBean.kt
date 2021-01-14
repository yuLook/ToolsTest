package com.yulook.tools.base

import android.content.Context
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel

/**
 *  时间：2021/1/11-10:50
 *  公司:北京爱豆文化传媒有限公司
 *  com.yulook.tools.base BaseItemBean
 *  描述：
 */
abstract class BaseItemBean : ViewModel(),LifecycleObserver {

    abstract fun getViewType():Int

    open fun onBindViewModel(context: Context, binding: ViewDataBinding?, position:Int){
        this.position=position
        this.context = context
    }

    var context:Context? = null
    var position = -1
    var height = ObservableInt()
    /**是否占据默认的空间大小*/
    var spanStates = true
    /**排序用的标识*/
    var sortLetters = ""

}
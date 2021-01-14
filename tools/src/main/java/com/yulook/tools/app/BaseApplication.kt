package com.yulook.tools.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.yulook.tools.utils.ScreenAdaptationUtil

/**
 *  时间：2021/1/13-11:20
 *  com.yulook.tools.app BaseApplication
 *  描述：
 */
class BaseApplication : Application() {

   companion object{
       lateinit var instance:Application
   }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ScreenAdaptationUtil.createDesign(375,667,0)
    }

    /**Dex分包*/
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}
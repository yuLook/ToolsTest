package com.yulook.tools.utils

import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yulook.tools.app.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  时间：2021/1/13-10:39
 *  com.yulook.tools.utils ToastUtil
 *  描述：
 */
object ToastUtil : ViewModel(){

    var toast:Toast? = null

    fun String.toast(): Toast? {
        viewModelScope.launch(Dispatchers.Main) {
            //主线程里进行toast
            if (toast == null){
                toast=Toast.makeText(BaseApplication.instance , this@toast, Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.CENTER,0,0)
                    show()
                }
            }else{
                toast?.setText(this@toast)
                toast?.show()
            }
        }
        return toast
    }

    fun showToast(content:String){
        showToast(content,Toast.LENGTH_SHORT)
    }

    fun showToast(content:String,duration:Int){
        viewModelScope.launch(Dispatchers.Main) {
            //主线程里进行toast
            if (toast == null){
                toast=Toast.makeText(BaseApplication.instance , content, duration).apply {
                    setGravity(Gravity.CENTER,0,0)
                    show()
                }
            }else{
                toast?.setText(content)
                toast?.show()
            }
        }
    }

}
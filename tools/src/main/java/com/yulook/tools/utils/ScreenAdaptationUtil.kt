package com.yulook.tools.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.databinding.BindingAdapter

/**
 *  时间：2021/1/14-11:41
 *  com.yulook.tools.utils ScreenAdaptationUtil
 *  描述：屏幕适配工具
 */
object ScreenAdaptationUtil {

    private var mDesignWidth  = 0
    private var mDesignHeight  = 0
    private var mDirection = 0
    private val size : Float by lazy {
        return@lazy if (mDirection == 0){
            Dp2PxUtils.getScreenWidth()/Dp2PxUtils.dip2px(mDesignWidth)
        }else{
            Dp2PxUtils.getScreenHeight()/ Dp2PxUtils.dip2px(mDesignHeight)
        }
    }
    /**
     * 屏幕适配
     * @param width 设计稿的宽度 以dp单位为准
     * @param height 设计稿的高度 以dp单位为准
     * @param direction 0以宽为基准 1以高为基准  一般以宽为基准
     */
    fun createDesign(width:Int,height:Int,direction:Int){
        mDesignWidth = width
        mDesignHeight = height
        mDirection = direction
    }

    /**
     * 设置宽高尺寸
     */
    @JvmStatic
    @BindingAdapter("y_height")
    fun setViewHeight(view: View, height:Int){
        if (size<=0)return
        view.layoutParams.height = (Dp2PxUtils.dip2px((height)) * size).toInt()
    }

    /**
     * 设置宽高尺寸
     */
    @JvmStatic
    @BindingAdapter("y_width")
    fun setViewWidth(view: View, width:Int){
        if (size<=0)return
        view.layoutParams.width = (Dp2PxUtils.dip2px((width)) * size).toInt()
    }

    @JvmStatic
    @BindingAdapter("y_padding")
    fun setViewPadding(view: View, padding:Int){
        if (size<=0)return
        view.setPadding((Dp2PxUtils.dip2px((padding)) * size).toInt())
    }

    @JvmStatic
    @BindingAdapter("y_padding_left")
    fun setViewPaddingLeft(view: View, padding:Int){
        if (size<=0)return
        view.setPadding((Dp2PxUtils.dip2px((padding)) * size).toInt(),view.paddingTop,view.paddingRight,view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter("y_padding_top")
    fun setViewPaddingTop(view: View, padding:Int){
        if (size<=0)return
        view.setPadding(view.paddingLeft,(Dp2PxUtils.dip2px((padding)) * size).toInt(),view.paddingRight,view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter("y_padding_right")
    fun setViewPaddingRight(view: View, padding:Int){
        if (size<=0)return
        view.setPadding(view.paddingLeft,view.paddingTop,(Dp2PxUtils.dip2px((padding)) * size).toInt(),view.paddingBottom)
    }

    @JvmStatic
    @BindingAdapter("y_padding_bottom")
    fun setViewPaddingBottom(view: View, padding:Int){
        if (size<=0)return
        view.setPadding(view.paddingLeft,view.paddingTop,view.paddingRight,(Dp2PxUtils.dip2px((padding)) * size).toInt())
    }

    @JvmStatic
    @BindingAdapter("y_margin")
    fun setViewMargin(view: View, margin:Int){
        if (size<=0)return
        val m = (Dp2PxUtils.dip2px((margin)) * size).toInt()
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams){
            lp.setMargins(m)
            view.layoutParams = lp
        }
    }

    @JvmStatic
    @BindingAdapter("y_margin_left")
    fun setViewMarginLeft(view: View, margin:Int){
        if (size<=0)return
        val m = (Dp2PxUtils.dip2px((margin)) * size).toInt()
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams){
            lp.leftMargin = m
            view.layoutParams = lp
        }
    }

    @JvmStatic
    @BindingAdapter("y_margin_top")
    fun setViewMarginTop(view: View, margin:Int){
        if (size<=0)return
        val m = (Dp2PxUtils.dip2px((margin)) * size).toInt()
        val lp = view.layoutParams
        if(lp is ViewGroup.MarginLayoutParams){
            lp.topMargin = m
            view.layoutParams = lp
        }

    }

    @JvmStatic
    @BindingAdapter("y_margin_right")
    fun setViewMarginRight(view: View, margin:Int){
        if (size<=0)return
        val m = (Dp2PxUtils.dip2px((margin)) * size).toInt()
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams){
            lp.rightMargin = m
            view.layoutParams = lp
        }
    }

    @JvmStatic
    @BindingAdapter("y_margin_bottom")
    fun setViewMarginBottom(view: View, margin:Int){
        if (size<=0)return
        val m = (Dp2PxUtils.dip2px((margin)) * size).toInt()
        val lp = view.layoutParams
        if (lp is ViewGroup.MarginLayoutParams){
            lp.bottomMargin = m
            view.layoutParams = lp
        }
    }

}
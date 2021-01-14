package com.yulook.tools.utils.glide

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yulook.tools.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*

/**
 *  时间：2021/1/13-11:52
 *  公司:北京爱豆文化传媒有限公司
 *  com.yulook.tools.utils.glide GlideUtil
 *  描述：
 */
object GlideUtil {

    enum class ImageSize{
        LONG,WIDTH,SQUARE
    }

    fun ImageView.glide(url:Any){
        glide(this.context,url,this, ImageSize.SQUARE)
    }

    /**加载图片*/
    fun glide(context: Context, url:Any, view: ImageView){
        glide(context,url,view, ImageSize.SQUARE)
    }

    fun glide(context: Context, url:Any, view: ImageView,size:ImageSize){
        if (context is Activity && (context.isDestroyed || context.isFinishing)){
            return
        }
        if (context is Fragment && (context.activity == null || context.activity!!.isFinishing || context.activity!!.isDestroyed)){
            return
        }
        Glide
            .with(context)
            .load(url)
            .placeholder(getPlaceholder(size)) //站位图片
            .error(getPlaceholder(size)) //错误图片
            .skipMemoryCache(true) //不使用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) //只在磁盘缓存 加载的尺寸
            .into(view)
    }

    /**加载图片-圆图*/
    fun glideCircle(context: Context, url:Any, view: ImageView){
        if (context is Activity && (context.isDestroyed || context.isFinishing)){
            return
        }
        if (context is Fragment && (context.activity == null || context.activity!!.isFinishing || context.activity!!.isDestroyed)){
            return
        }
        Glide
            .with(context)
            .load(url)
            .placeholder(getPlaceholder(ImageSize.SQUARE))
            .error(getPlaceholder(ImageSize.SQUARE))
            .apply(RequestOptions().circleCrop())
            .into(view)
    }

    /**
     * 加载图片
     * 角图
     * 默认5f  正方形
     */
    fun glideCorners(context: Context, url:Any, view: ImageView){
        val transform = CenterCropTransform(5f,CenterCropTransform.CornerType.ALL)
        glideCorners(context, url, view, transform,ImageSize.SQUARE)
    }

    fun glideCorners(context: Context, url:Any, view: ImageView,size:ImageSize){
        val transform = CenterCropTransform(5f,CenterCropTransform.CornerType.ALL)
        glideCorners(context, url, view, transform,size)
    }

    fun glideCorners(context: Context, url:Any, view: ImageView,radius:Float){
        val transform = CenterCropTransform(radius,CenterCropTransform.CornerType.ALL)
        glideCorners(context, url, view, transform,ImageSize.SQUARE)
    }

    fun glideCorners(context: Context, url:Any, view: ImageView,radius:Float,size:ImageSize){
        val transform = CenterCropTransform(radius,CenterCropTransform.CornerType.ALL)
        glideCorners(context, url, view, transform,size)
    }

    private fun glideCorners(context: Context, url: Any, view: ImageView,transform: Transformation<Bitmap>,size:ImageSize){
        if (context is Activity && (context.isDestroyed || context.isFinishing)){
            return
        }
        if (context is Fragment && (context.activity == null || context.activity!!.isFinishing || context.activity!!.isDestroyed)){
            return
        }
        Glide
            .with(context)
            .asDrawable()
            .load(url)
            .placeholder(getPlaceholder(size))
            .error(getPlaceholder(size))
            .transform(transform)
            .skipMemoryCache(true) //不使用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) //只在磁盘缓存 加载的尺寸
            .into(view)
    }

    /**模糊图片*/
    fun glideBlur(context: Context, url:Any, view: ImageView){
        if (context is Activity && (context.isDestroyed || context.isFinishing)){
            return
        }
        if (context is Fragment && (context.activity == null || context.activity!!.isFinishing || context.activity!!.isDestroyed)){
            return
        }
        Glide
            .with(view)
            .asDrawable()
            .load(url)
            .transform(BlurTransformation())
            .skipMemoryCache(true) //不使用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) //只在磁盘缓存 加载的尺寸
            .into(view)
    }

    var placeholderLong = R.mipmap.ic_launcher
    var placeholderWidth = R.mipmap.ic_launcher
    var placeholderSquare = R.mipmap.ic_launcher

    private fun getPlaceholder(size:ImageSize):Int{
        return when (size){
            ImageSize.LONG -> placeholderLong
            ImageSize.WIDTH -> placeholderWidth
            ImageSize.SQUARE -> placeholderSquare
        }
    }
}
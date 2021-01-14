package com.yulook.tools.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.databinding.library.baseAdapters.BR
import com.yulook.tools.base.BaseItemBean

/**
 *  时间：2021/1/11-10:48
 *  com.yulook.tools.adapter BaseMvvmAdapter
 *  描述：
 */
open class BaseBindingAdapter(context: Context) : RecyclerView.Adapter<BaseBindingAdapter.BaseViewHolder>() {

    private var mContext = context

    val onItemClickListener = ObservableField<OnItemClickListener>()
    val mDataList = arrayListOf<BaseItemBean>()
    val mHeadList = arrayListOf<BaseItemBean>()
    val emptyBean = ObservableField<BaseItemBean>()
    private val mRefreshList = arrayListOf<BaseItemBean>()

    constructor(context : Context, dataList:ArrayList<BaseItemBean>):this(context){
        mDataList.addAll(dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext),viewType,parent,false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val list = mutableListOf<BaseItemBean>()
        list.addAll(mHeadList)
        list.addAll(mDataList)
        if (list.size==0){
            emptyBean.get()?.let { empty ->
                holder.binding?.setVariable(BR.item,empty)
            }
        }else{
            val itemBean = list[position]
            holder.binding?.setVariable(BR.item,itemBean)
            onItemClickListener.get()?.let {
                holder.itemView.setOnClickListener { view ->
                    it.itemClick(position,itemBean,view)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        val size = mHeadList.size + mDataList.size
        if (size==0){
            //无数据时可显示设置的空页面
            return if (emptyBean.get() == null) 0 else 1
        }
        return size
    }

    override fun getItemViewType(position: Int): Int {
        val size = mHeadList.size + mDataList.size
        if (size==0){
            emptyBean.get()?.also {
                return it.getViewType()
            }
        }else{
            val list = mutableListOf<BaseItemBean>()
            list.addAll(mHeadList)
            list.addAll(mDataList)
            return list[position].getViewType()
        }
        return super.getItemViewType(position)
    }

    /**设置head占用的空间*/
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        //为GridLayoutManager类型的adapter设置header
        if(recyclerView.layoutManager is GridLayoutManager){
            (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position : Int): Int {
                    //每个item在布局上占据的单元格 item占据1个 header占据spanCount个
                    return if (position>=mHeadList.size) {
                        if (position-mHeadList.size>=mDataList.size){
                            return (recyclerView.layoutManager as GridLayoutManager).spanCount
                        }else{
                            val bean = mDataList[position-mHeadList.size]
                            if (bean.spanStates){
                                1
                            }else{
                                (recyclerView.layoutManager as GridLayoutManager).spanCount
                            }
                        }

                    }else {
                        (recyclerView.layoutManager as GridLayoutManager).spanCount
                    }
                }
            }
        }
    }
    /**设置head占用的空间*/
    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val lp = holder.itemView.layoutParams
        lp?.let {
            if (lp is StaggeredGridLayoutManager.LayoutParams ){
                //为StaggeredGridLayoutManager类型的adapter设置header
                if (holder.adapterPosition<mHeadList.size){
                    lp.isFullSpan = true
                }else if (holder.adapterPosition - mHeadList.size <mDataList.size) {
                    val bean = mDataList[holder.adapterPosition - mHeadList.size]
                    if (!bean.spanStates){
                        lp.isFullSpan = true
                    }
                }
            }
        }
    }

    fun addHead(item : BaseItemBean){
        mHeadList.add(item)
    }

    fun addHead(list:ArrayList<BaseItemBean>){
        mHeadList.addAll(list)
    }

    fun addHead(item : BaseItemBean,index:Int){
        mHeadList.add(index,item)
    }

    fun addBody(item : BaseItemBean){
        mDataList.add(item)
    }

    fun addBody(list : ArrayList<BaseItemBean>){
        mDataList.addAll(list)
    }

    fun addBody(item : BaseItemBean,index:Int){
        addBody(item,index,false)
    }

    fun addBody(item : BaseItemBean,index:Int,isAll:Boolean){
        if (isAll){
            mDataList.add(index-mHeadList.size,item)
        }else{
            mDataList.add(index,item)
        }
    }

    fun getItem(position: Int):BaseItemBean?{
        if (position<0) return null
        val list = mutableListOf<BaseItemBean>()
        list.addAll(mHeadList)
        list.addAll(mDataList)
        if (list.size>0 && position<list.size){
            return list[position]
        }
        return null
    }

    /**添加一个并刷新*/
    fun addAndRefresh(item : BaseItemBean){
        val current = mHeadList.size + mDataList.size
        addBody(item)
        notifyItemInserted(current)
    }

    /**
     * 添加一个等待一起刷新
     * 建议for循环时使用
     */
    fun addWaitRefresh(item : BaseItemBean){
        mRefreshList.add(item)
    }
    /**等待中的数据刷新*/
    fun notifyWaitDataToRefresh(){
        val current = mHeadList.size + mDataList.size
        mDataList.addAll(mRefreshList)
        notifyInserted(current)
        mRefreshList.clear()
    }

    /**从下标position的位置开始刷新后面的数据*/
    fun notifyInserted(position: Int){
        if (position>=0&&position<=mDataList.size+mHeadList.size){
            notifyItemInserted(position)
            notifyItemRangeChanged(position,100)
        }else{
            notifyDataSetChanged()
        }
    }

    fun notifyDelete(position: Int){
        if (position<mHeadList.size){
            mHeadList.removeAt(position)
        }
        else if(position-mHeadList.size<mDataList.size){
            mDataList.removeAt(position-mHeadList.size)
        }
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,20)
    }

    fun removeAllDateFromIndex(index: Int){
        val list = ArrayList<BaseItemBean>()
        for(position in index until mDataList.size){
            list.add(mDataList[position])
        }
        mDataList.removeAll(list)
        notifyInserted(mHeadList.size+mDataList.size)
    }

    fun clear(){
        mHeadList.clear()
        mDataList.clear()
        notifyDataSetChanged()
    }

    class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){

        var binding: ViewDataBinding?= null

        constructor(binding: ViewDataBinding):this(binding.root){
            this.binding = binding
        }

    }

    interface OnItemClickListener{
        fun itemClick(position: Int,item: BaseItemBean,parent:View)
    }

}
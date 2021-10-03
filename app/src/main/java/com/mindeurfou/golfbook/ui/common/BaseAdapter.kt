package com.mindeurfou.golfbook.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class BaseViewHolder<Item>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: Item)
}

abstract class BaseAdapter<Item> (
    private val list: List<Item>,
    private val layoutItems: List<Int>,
) : RecyclerView.Adapter<BaseViewHolder<Item>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
        val layoutId = when(viewType) {
            1 -> layoutItems[1]
            2 -> layoutItems[2]
            3 -> layoutItems[3]
            0 -> layoutItems[4]
            else -> layoutItems[0]
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        if (layoutId == layoutItems[0]) {
            val layoutParams : StaggeredGridLayoutManager.LayoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
            view.layoutParams = layoutParams
        }
        view.setOnClickListener { onItemClick(it) }
        additionalLayout(view)
        return getViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            5
        else
            (position % 4)
    }

    override fun getItemCount(): Int = list.size

    protected abstract fun onItemClick(itemView: View)
    protected abstract fun additionalLayout(view: View)
    protected abstract fun getViewHolder(itemView: View) : BaseViewHolder<Item>

}
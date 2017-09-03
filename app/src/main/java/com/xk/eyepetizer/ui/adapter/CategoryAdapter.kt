package com.xk.eyepetizer.ui.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.xk.eyepetizer.mvp.model.bean.Category
import com.xk.eyepetizer.ui.view.category.CategoryItem
import com.xk.eyepetizer.ui.view.detail.ListEndView
import kotlinx.android.synthetic.main.layout_list_end.view.*
import java.util.*

/**
 * Created by xuekai on 2017/9/3.
 */
class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    val data by lazy {
        ArrayList<Category>()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_STANDARD -> {
                (holder?.itemView as CategoryItem).setData(data[position])
                holder.itemView.setOnClickListener { onClick?.invoke(data[position]) }
            }
        }

    }

    fun setData(data: ArrayList<Category>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (data.size == 0) 0 else data.size + 1


    private val TYPE_STANDARD = 1
    private val TYPE_END = 2

    override fun getItemViewType(position: Int): Int {
        if (data.size == position)
            return TYPE_END
        return TYPE_STANDARD
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var itemView: View? = null
        when (viewType) {
            TYPE_END -> {
                itemView = ListEndView(parent?.context)
                itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
                itemView.tv_text_end.setTextColor(Color.BLACK)
            }

            TYPE_STANDARD -> {
                itemView = CategoryItem(parent?.context)
            }
        }
        return CategoryAdapter.ViewHolder(itemView)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    var onClick: ((Category) -> Unit)? = {}
}
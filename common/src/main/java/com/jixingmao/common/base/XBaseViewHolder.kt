package com.jixingmao.common.base

import android.view.View
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**

BaseRecyclerViewAdapterHelper

解决部分方法找不到的问题
 */
class XBaseViewHolder(view: View): BaseViewHolder(view) {
    fun bindingAdapterPosition():Int{
        return bindingAdapterPosition
    }

    fun itemViewType():Int{
        return itemViewType
    }
}
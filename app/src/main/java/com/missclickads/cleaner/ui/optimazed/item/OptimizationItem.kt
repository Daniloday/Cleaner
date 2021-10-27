package com.missclickads.cleaner.ui.optimazed.item

import android.view.View
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemFoldersTypesBinding
import com.missclickads.cleaner.databinding.ItemOptimizationEndBinding
import com.xwray.groupie.viewbinding.BindableItem

class OptimizationItem(
private val image : Int,
private val title : String,
) : BindableItem<ItemOptimizationEndBinding>(){

    override fun bind(viewBinding: ItemOptimizationEndBinding, position: Int) {
        viewBinding.icon.setImageResource(image)
        viewBinding.typeText.text = title
    }

    override fun getLayout(): Int = R.layout.item_optimization_end

    override fun initializeViewBinding(view: View): ItemOptimizationEndBinding = ItemOptimizationEndBinding.bind(view)

}
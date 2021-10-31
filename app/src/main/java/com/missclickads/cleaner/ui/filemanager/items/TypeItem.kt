package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemFoldersTypesBinding
import com.xwray.groupie.viewbinding.BindableItem


class TypeItem(
    private val image : Int,
    private val type : String,
    private val memory : String,
    private val callback : (String) -> (Unit)
) : BindableItem<ItemFoldersTypesBinding>(){
    override fun bind(viewBinding: ItemFoldersTypesBinding, position: Int) {
        viewBinding.icon.setImageResource(image)
        viewBinding.typeText.text = type
        viewBinding.memoryText.text = memory
        viewBinding.root.setOnClickListener { callback.invoke(type) }
    }

    override fun getLayout(): Int = R.layout.item_folders_types

    override fun initializeViewBinding(view: View): ItemFoldersTypesBinding = ItemFoldersTypesBinding.bind(view)

}
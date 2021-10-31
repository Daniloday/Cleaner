package com.missclickads.cleaner.ui.filemanager.items

import android.view.View
import com.missclickads.cleaner.R
import com.missclickads.cleaner.databinding.ItemFileBinding
import com.missclickads.cleaner.databinding.ItemFoldersTypesBinding
import com.missclickads.cleaner.models.FileModel
import com.xwray.groupie.viewbinding.BindableItem

class FileItem (
    private val file : FileModel
) : BindableItem<ItemFileBinding>(){
    override fun bind(viewBinding: ItemFileBinding, position: Int) {
        viewBinding.textNameTop.text = file.title
        viewBinding.textSizeBottom.text = file.size
    }

    override fun getLayout(): Int = R.layout.item_file

    override fun initializeViewBinding(view: View): ItemFileBinding = ItemFileBinding.bind(view)

}